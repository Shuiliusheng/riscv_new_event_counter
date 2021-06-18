// See LICENSE.SiFive for license details.
// See LICENSE.Berkeley for license details.
//zwq
package freechips.rocketchip.rocket

import Chisel._
import Chisel.ImplicitConversions._
import chisel3.{DontCare, WireInit, withClock}
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.devices.debug.DebugModuleKey
import freechips.rocketchip.tile._
import freechips.rocketchip.util._
import freechips.rocketchip.util.property._
import scala.collection.mutable.LinkedHashMap
import Instructions._

// 用于定义机器状态寄存器
class MStatus extends Bundle {
  /* 不是真正的部分，但是很方便 */
  val debug = Bool()
  val cease = Bool()
  val wfi = Bool()
  // misa寄存器
  val isa = UInt(width = 32)
  // 数据访问的有效特权级
  val dprv = UInt(width = PRV.SZ)
  // 特权级
  val prv = UInt(width = PRV.SZ)
 
  /* 真正的部分 */
  // FS和XS是否由脏状态
  val sd = Bool()
  // 保留位
  val zero2 = UInt(width = 27)
  // 特权态指令系统宽度
  val sxl = UInt(width = 2)
  // 用户态指令系统宽度
  val uxl = UInt(width = 2)
  // 32位指令系统的SD
  val sd_rv32 = Bool()
  // 保留位
  val zero1 = UInt(width = 8)
  // 截获特权态异常返回指令（SRET）
  val tsr = Bool()
  // 截获WFI指令，为1时不能在低特权级执行WFI指令
  val tw = Bool()
  // 截获特权态虚拟内存管理操作（satp CSR和SFENCE.VMA）
  val tvm = Bool()
  // 使可执行也可读，为1时Load只能访问可读页，为0时还可以访问可执行页
  val mxr = Bool()
  // 允许特权态访问用户态内存
  val sum = Bool()
  // 修改的特权级，为0时正常，为1时地址访问按mpp级别进行
  val mprv = Bool()
  // 自定义单元状态
  val xs = UInt(width = 2)
  // 浮点单元状态，分别是Off，Initial，Clean和Dirty
  // Off：任何试图读取或写入相应状态的指令都将导致非法指令异常
  // Initial：相应的状态应该有一个初始常量值，还原时需要设置默认值
  // Clean：相应的状态可能与初始值不同，但与上下文切换中存储的最后一个值匹配，还原时需要读内存
  // Dirty：自上次上下文保存以来，相应的状态可能已被修改，保存时需要重新写入
  val fs = UInt(width = 2)
  /* xpp用于嵌套trap，记录进入上级trap的特权级别，upp省略 */
  val mpp = UInt(width = 2)
  val vs = UInt(width = 2)
  val spp = UInt(width = 1)
  /* xpie用于嵌套trap，记录进入trap前的xie */
  val mpie = Bool()
  val hpie = Bool()
  val spie = Bool()
  val upie = Bool()
  /* xie表示中断启用状态 */
  val mie = Bool()
  val hie = Bool()
  val sie = Bool()
  val uie = Bool()
}

// 用于调试
class DCSR extends Bundle {
  val xdebugver = UInt(width = 2)
  val zero4 = UInt(width=2)
  val zero3 = UInt(width = 12)
  val ebreakm = Bool()
  val ebreakh = Bool()
  val ebreaks = Bool()
  val ebreaku = Bool()
  val zero2 = Bool()
  val stopcycle = Bool()
  val stoptime = Bool()
  val cause = UInt(width = 3)
  val zero1 = UInt(width=3)
  val step = Bool()
  val prv = UInt(width = PRV.SZ)
}

// 用于定义机器中断挂起寄存器
class MIP(implicit p: Parameters) extends CoreBundle()(p)
    with HasCoreParameters {
  val lip = Vec(coreParams.nLocalInterrupts, Bool())
  // 保留位
  val zero2 = Bool()
  // 调试中断，与CSR.debugIntCause同步
  val debug = Bool()
  // 保留位
  val zero1 = Bool()
  // 协处理器中断
  val rocc = Bool()
  /* 外部中断挂起 */
  val meip = Bool()
  val heip = Bool()
  val seip = Bool()
  val ueip = Bool()
  /* 定时器中断挂起 */
  val mtip = Bool()
  val htip = Bool()
  val stip = Bool()
  val utip = Bool()
  /* 软件中断挂起 */
  val msip = Bool()
  val hsip = Bool()
  val ssip = Bool()
  val usip = Bool()
}

// 用于定义页表基址寄存器，现在改为atp
class PTBR(implicit p: Parameters) extends CoreBundle()(p) {
  def additionalPgLevels = mode.extract(log2Ceil(pgLevels-minPgLevels+1)-1, 0)
  def pgLevelsToMode(i: Int) = (xLen, i) match {
    case (32, 2) => 1
    case (64, x) if x >= 3 && x <= 6 => x + 5
  }
  val (modeBits, maxASIdBits) = xLen match {
    case 32 => (1, 9)
    case 64 => (4, 16)
  }
  require(modeBits + maxASIdBits + maxPAddrBits - pgIdxBits == xLen)

  // 模式（层级）
  val mode = UInt(width = modeBits)
  // 进程号
  val asid = UInt(width = maxASIdBits)
  // 物理地址
  val ppn = UInt(width = maxPAddrBits - pgIdxBits)
}

// 特权级别
object PRV
{
  // 尺寸
  val SZ = 2
  // 用户态
  val U = 0
  // 监督态
  val S = 1
  // 超级监督态
  val H = 2
  // 机器态
  val M = 3
}

object CSR
{
  // CSR命令
  // 尺寸为3位
  val SZ = 3
  // 相当于BitPat("b???")
  def X = BitPat.dontCare(SZ)
  def N = UInt(0,SZ)  //空
  def R = UInt(2,SZ)  //读
  def I = UInt(4,SZ)  //立即数
  def W = UInt(5,SZ)  //写
  def S = UInt(6,SZ)  //设置
  def C = UInt(7,SZ)  //清除

  // 利用有效位生成CSR命令掩码
  def maskCmd(valid: Bool, cmd: UInt): UInt = {
    // 如果无效那么不能写
    cmd & ~Mux(valid, 0.U, CSR.I)
  }

  // 12位地址，4096个CSR
  val ADDRSZ = 12
  def busErrorIntCause = 128
  // 调试中断，与MIP.debug保持一致
  def debugIntCause = 14
  def debugTriggerCause = {
    val res = debugIntCause
    require(!(Causes.all contains res))
    res
  }
  def unmiIntCause = 15  // NMI: Higher numbers = higher priority, must not reuse debugIntCause
  def rnmiIntCause = 13
  def rnmiBEUCause = 12

  // mcycle的只读复制，32位系统为低32位
  val firstCtr = CSRs.cycle
  // mcycle高32位的只读复制
  val firstCtrH = CSRs.cycleh
  // mhpmcouter3的只读复制，32位系统为低32位
  val firstHPC = CSRs.hpmcounter3
  // mhpmcouter3高32位的只读复制
  val firstHPCH = CSRs.hpmcounter3h
  // 第一个硬件性能监视时间
  val firstHPE = CSRs.mhpmevent3
  // 第一个硬件性能监视计数器，32位系统中为低32位
  val firstMHPC = CSRs.mhpmcounter3
  // 第一个硬件性能监视计数器高32位，用于32位系统
  val firstMHPCH = CSRs.mhpmcounter3h
  // 第一个硬件性能监视编号
  val firstHPM = 3
  // 计数器数量
  val nCtr = 32
  // 硬件性能监视数量
  val nHPM = nCtr - firstHPM
  // 宽度为40位
  val hpmWidth = 40

  // 最多16个PMP表项
  val maxPMPs = 16
}

// 性能计数器接口
class PerfCounterIO(implicit p: Parameters) extends CoreBundle
    with HasCoreParameters {
  // 事件
  val eventSel = UInt(OUTPUT, xLen)
  // 增长量
  val inc = UInt(INPUT, log2Ceil(1+retireWidth))
}

// 用于追踪指令
class TracedInstruction(implicit p: Parameters) extends CoreBundle {
  val valid = Bool()
  // 指令地址
  val iaddr = UInt(width = coreMaxAddrBits)
  // 指令
  val insn = UInt(width = iLen)
  // 特权级别
  val priv = UInt(width = 3)
  // 产生异常
  val exception = Bool()
  // 产生中断
  val interrupt = Bool()
  // 引发原因
  val cause = UInt(width = xLen)
  // trap特殊值
  val tval = UInt(width = coreMaxAddrBits max iLen)
}

class TraceAux extends Bundle {
  val enable = Bool()
  val stall = Bool()
}

// CSR译码接口
class CSRDecodeIO extends Bundle {
  // CSR地址
  val csr = UInt(INPUT, CSR.ADDRSZ)
  // 浮点非法
  val fp_illegal = Bool(OUTPUT)
  // 向量非法
  val vector_illegal = Bool(OUTPUT)
  // 浮点CSR
  val fp_csr = Bool(OUTPUT)
  // ROCC非法
  val rocc_illegal = Bool(OUTPUT)
  // 读非法
  val read_illegal = Bool(OUTPUT)
  // 写非法
  val write_illegal = Bool(OUTPUT)
  // 写刷新
  val write_flush = Bool(OUTPUT)
  // 系统非法
  val system_illegal = Bool(OUTPUT)
}

class CSRFileIO(implicit p: Parameters) extends CoreBundle
    with HasCoreParameters {
  // 时钟
  val ungated_clock = Clock().asInput
  // 中断
  val interrupts = new CoreInterrupts().asInput
  // 硬件线程ID
  val hartid = UInt(INPUT, hartIdLen)
  // CSR读写
  val rw = new Bundle {
    // CSR地址
    val addr = UInt(INPUT, CSR.ADDRSZ)
    // CSR命令
    val cmd = Bits(INPUT, CSR.SZ)
    // 读数据
    val rdata = Bits(OUTPUT, xLen)
    // 写数据 
    val wdata = Bits(INPUT, xLen)
  }

  // CSR译码单元
  val decode = Vec(decodeWidth, new CSRDecodeIO)

  // CSR暂停
  val csr_stall = Bool(OUTPUT)
  // Call、Break或Return
  val eret = Bool(OUTPUT)
  // 单步控制
  val singleStep = Bool(OUTPUT)

  // 机器状态
  val status = new MStatus().asOutput
  // 页表基址寄存器
  val ptbr = new PTBR().asOutput
  // trap pc的地址
  val evec = UInt(OUTPUT, vaddrBitsExtended)
  // 异常
  val exception = Bool(INPUT)
  // 指令退休
  val retire = UInt(INPUT, log2Up(1+retireWidth))
  // 中断或异常原因
  val cause = UInt(INPUT, xLen)
  // PC
  val pc = UInt(INPUT, vaddrBitsExtended)
  // trap的特殊值
  val tval = UInt(INPUT, vaddrBitsExtended)
  // hart执行周期
  val time = UInt(OUTPUT, xLen)
  // 浮点读舍入模式
  val fcsr_rm = Bits(OUTPUT, FPConstants.RM_SZ)
  // 浮点读异常标志
  val fcsr_flags = Valid(Bits(width = FPConstants.FLAGS_SZ)).flip
  // 设置FS为脏
  val set_fs_dirty = coreParams.haveFSDirty.option(Bool(INPUT))
  // ROCC中断
  val rocc_interrupt = Bool(INPUT)
  // 中断
  val interrupt = Bool(OUTPUT)
  // 中断原因
  val interrupt_cause = UInt(OUTPUT, xLen)
  // 断点信息
  val bp = Vec(nBreakpoints, new BP).asOutput
  // 物理地址保护寄存器信息
  val pmp = Vec(nPMPs, new PMP).asOutput
  // 性能计数器信息
  val counters = Vec(nPerfCounters, new PerfCounterIO)
  // CSR命令写的计数器
  val csrw_counter = UInt(OUTPUT, CSR.nCtr)
  // mcycle计数器禁止递增
  val inhibit_cycle = Output(Bool())
  // 退休的指令
  val inst = Vec(retireWidth, UInt(width = iLen)).asInput
  // 追踪的指令
  val trace = Vec(retireWidth, new TracedInstruction).asOutput
  // 机器态上下文
  val mcontext = Output(UInt(coreParams.mcontextWidth.W))
  // 特权态上下文
  val scontext = Output(UInt(coreParams.scontextWidth.W))

  // 向量相关
  val vector = usingVector.option(new Bundle {
    val vconfig = new VConfig().asOutput
    val vstart = UInt(maxVLMax.log2.W).asOutput
    val vxrm = UInt(2.W).asOutput
    val set_vs_dirty = Input(Bool())
    val set_vconfig = Valid(new VConfig).flip
    val set_vstart = Valid(vstart).flip
    val set_vxsat = Bool().asInput
  })
}

/* Vx都为向量相关 */
class VConfig(implicit p: Parameters) extends CoreBundle {
  val vl = UInt((maxVLMax.log2 + 1).W)
  val vtype = new VType
}

object VType {
  def fromUInt(that: UInt, ignore_vill: Boolean = false)(implicit p: Parameters): VType = {
    val res = 0.U.asTypeOf(new VType)
    val in = that.asTypeOf(res)
    val vill = (in.max_vsew < in.vsew) || !in.lmul_ok || in.reserved =/= 0 || in.vill
    when (!vill || ignore_vill) {
      res := in
      res.vsew := in.vsew(log2Ceil(1 + in.max_vsew) - 1, 0)
    }
    res.reserved := 0.U
    res.vill := vill
    res
  }

  def computeVL(avl: UInt, vtype: UInt, currentVL: UInt, useCurrentVL: Bool, useMax: Bool, useZero: Bool)(implicit p: Parameters): UInt =
    VType.fromUInt(vtype, true).vl(avl, currentVL, useCurrentVL, useMax, useZero)
}

class VType(implicit p: Parameters) extends CoreBundle {
  val vill = Bool()
  val reserved = UInt((xLen - 9).W)
  val vma = Bool()
  val vta = Bool()
  val vsew = UInt(3.W)
  val vlmul_sign = Bool()
  val vlmul_mag = UInt(2.W)

  def vlmul_signed: SInt = Cat(vlmul_sign, vlmul_mag).asSInt

  @deprecated("use vlmul_sign, vlmul_mag, or vlmul_signed", "RVV 0.9")
  def vlmul: UInt = vlmul_mag

  def max_vsew = log2Ceil(eLen/8)
  def max_vlmul = (1 << vlmul_mag.getWidth) - 1

  def lmul_ok: Bool = Mux(this.vlmul_sign, this.vlmul_mag =/= 0 && ~this.vlmul_mag < max_vsew - this.vsew, true.B)

  def minVLMax: Int = ((maxVLMax / eLen) >> ((1 << vlmul_mag.getWidth) - 1)) max 1

  def vlMax: UInt = (maxVLMax >> (this.vsew +& Cat(this.vlmul_sign, ~this.vlmul_mag))).andNot(minVLMax-1)

  def vl(avl: UInt, currentVL: UInt, useCurrentVL: Bool, useMax: Bool, useZero: Bool): UInt = {
    val atLeastMaxVLMax = useMax || Mux(useCurrentVL, currentVL >= maxVLMax, avl >= maxVLMax)
    val avl_lsbs = Mux(useCurrentVL, currentVL, avl)(maxVLMax.log2 - 1, 0)

    val atLeastVLMax = atLeastMaxVLMax || (avl_lsbs & (-maxVLMax.S >> (this.vsew +& Cat(this.vlmul_sign, ~this.vlmul_mag))).asUInt.andNot(minVLMax-1)).orR
    val isZero = vill || useZero
    Mux(!isZero && atLeastVLMax, vlMax, 0.U) | Mux(!isZero && !atLeastVLMax, avl_lsbs, 0.U)
  }
}

// 用于CSR寄存器堆
class CSRFile(
  perfEventSets: EventSets = new EventSets(Seq()),
  customCSRs: Seq[CustomCSR] = Nil)(implicit p: Parameters)
    extends CoreModule()(p)
    with HasCoreParameters {
  // 接口
  val io = new CSRFileIO {
    val customCSRs = Vec(CSRFile.this.customCSRs.size, new CustomCSRIO).asOutput
  }

  /* 复位机器状态 */
  val reset_mstatus = Wire(init=new MStatus().fromBits(0))
  reset_mstatus.mpp := PRV.M
  reset_mstatus.prv := PRV.M
  reset_mstatus.xs := (if (usingRoCC) UInt(3) else UInt(0))
  val reg_mstatus = Reg(init=reset_mstatus)

  // 用于更新特权状态
  val new_prv = Wire(init = reg_mstatus.prv)
  // 更新特权状态
  reg_mstatus.prv := legalizePrivilege(new_prv)

  /* 调试相关 */
  val reset_dcsr = Wire(init=new DCSR().fromBits(0))
  reset_dcsr.xdebugver := 1
  reset_dcsr.prv := PRV.M
  val reg_dcsr = Reg(init=reset_dcsr)

  // 支持的中断和可以委托的中断
  val (supported_interrupts, delegable_interrupts) = {
    val sup = Wire(new MIP)
    sup.usip := false
    sup.ssip := Bool(usingSupervisor)
    sup.hsip := false
    sup.msip := true
    sup.utip := false
    sup.stip := Bool(usingSupervisor)
    sup.htip := false
    sup.mtip := true
    sup.ueip := false
    sup.seip := Bool(usingSupervisor)
    sup.heip := false
    sup.meip := true
    sup.rocc := usingRoCC
    sup.zero1 := false
    sup.debug := false
    sup.zero2 := false
    sup.lip foreach { _ := true }
    val supported_high_interrupts = if (io.interrupts.buserror.nonEmpty && !usingNMI) UInt(BigInt(1) << CSR.busErrorIntCause) else 0.U
    
    //机器三种中断不能委托
    val del = Wire(init=sup)
    del.msip := false
    del.mtip := false
    del.meip := false

    (sup.asUInt | supported_high_interrupts, del.asUInt)
  }
  // 可以委托的异常
  val delegable_exceptions = UInt(Seq(
    Causes.misaligned_fetch,
    Causes.fetch_page_fault,
    Causes.breakpoint,
    Causes.load_page_fault,
    Causes.store_page_fault,
    Causes.misaligned_load,
    Causes.misaligned_store,
    Causes.illegal_instruction,
    // 系统调用
    Causes.user_ecall).map(1 << _).sum)

  /* 调试相关寄存器 */
  val reg_debug = Reg(init=Bool(false))
  val reg_dpc = Reg(UInt(width = vaddrBitsExtended))
  val reg_dscratch = Reg(UInt(width = xLen))
  val reg_dscratch1 = (p(DebugModuleKey).map(_.nDscratch).getOrElse(1) > 1).option(Reg(UInt(width = xLen)))
  val reg_singleStepped = Reg(Bool())

  val reg_mcontext = (coreParams.mcontextWidth > 0).option(RegInit(0.U(coreParams.mcontextWidth.W)))
  val reg_scontext = (coreParams.scontextWidth > 0).option(RegInit(0.U(coreParams.scontextWidth.W)))

  val reg_tselect = Reg(UInt(width = log2Up(nBreakpoints)))
  val reg_bp = Reg(Vec(1 << log2Up(nBreakpoints), new BP))
  // 物理地址映射保护寄存器，config+addr
  val reg_pmp = Reg(Vec(nPMPs, new PMPReg))

  // 机器中断使能寄存器
  val reg_mie = Reg(UInt(width = xLen))
  // 机器中断委托寄存器，读取时需要结合可以委托的中断进行判断
  val (reg_mideleg, read_mideleg) = {
    val reg = Reg(UInt(xLen.W))
    (reg, Mux(usingSupervisor, reg & delegable_interrupts, 0.U))
  }
  // 机器异常委托寄存器，读取时需要结合可以委托的异常进行判断
  val (reg_medeleg, read_medeleg) = {
    val reg = Reg(UInt(xLen.W))
    (reg, Mux(usingSupervisor, reg & delegable_exceptions, 0.U))
  }
  // 机器中断挂起寄存器
  val reg_mip = Reg(new MIP)
  // 机器异常程序计数器
  val reg_mepc = Reg(UInt(width = vaddrBitsExtended))
  // 机器trap原因寄存器
  val reg_mcause = RegInit(0.U(xLen.W))
  // 机器trap特殊值寄存器
  val reg_mtval = Reg(UInt(width = vaddrBitsExtended))
  // 机器hart上下文空间指针寄存器
  val reg_mscratch = Reg(Bits(width = xLen))
  // trap向量寄存器，包括Base和Mode，Mode为0时将PC写入BASE，为1时将异步中断写入BASES+4*cause
  val mtvecWidth = paddrBits min xLen
  val reg_mtvec = mtvecInit match {
    case Some(addr) => Reg(init=UInt(addr, mtvecWidth))
    case None => Reg(UInt(width = mtvecWidth))
  }

  // 用于初始化的机器状态
  val reset_mnstatus = Wire(init=new MStatus().fromBits(0))
  reset_mnstatus.mpp := PRV.M
  /* 不可屏蔽的中断相关寄存器 */
  val reg_mnscratch = Reg(Bits(width = xLen))
  val reg_mnepc = Reg(UInt(width = vaddrBitsExtended))
  val reg_mncause = RegInit(0.U(xLen.W))
  val reg_mnstatus = Reg(init=reset_mnstatus)
  val reg_rnmie = RegInit(true.B)
  val reg_unmie = RegInit(true.B)
  val nmie = reg_rnmie && reg_unmie

  // 允许委托（向低特权级传递）的计数器
  val delegable_counters = ((BigInt(1) << (nPerfCounters + CSR.firstHPM)) - 1).U
  // 机器态向低特权级传递计数器使能，读取时结合处理器规定的允许的计数器
  val (reg_mcounteren, read_mcounteren) = {
    val reg = Reg(UInt(32.W))
    (reg, Mux(usingUser, reg & delegable_counters, 0.U))
  }
  // 特权态向低特权级传递计数器使能，读取时结合处理器规定的允许的计数器
  val (reg_scounteren, read_scounteren) = {
    val reg = Reg(UInt(32.W))
    (reg, Mux(usingSupervisor, reg & delegable_counters, 0.U))
  }

  // 特权态异常程序计数器
  val reg_sepc = Reg(UInt(width = vaddrBitsExtended))
  // 特权态trap原因寄存器
  val reg_scause = Reg(Bits(width = xLen))
  // 特权态trap特殊值寄存器
  val reg_stval = Reg(UInt(width = vaddrBitsExtended))
  // 特权态hart上下文空间指针寄存器
  val reg_sscratch = Reg(Bits(width = xLen))
  // 特权态trap向量寄存器
  val reg_stvec = Reg(UInt(width = vaddrBits))
  // 特权态地址翻译和保护寄存器
  val reg_satp = Reg(new PTBR)
  // 等待中断寄存器
  val reg_wfi = withClock(io.ungated_clock) { Reg(init=Bool(false)) }

  // 浮点异常标志寄存器
  val reg_fflags = Reg(UInt(width = 5))
  // 浮点舍入模式寄存器
  val reg_frm = Reg(UInt(width = 3))
  /* 向量相关寄存器 */
  val reg_vconfig = usingVector.option(Reg(new VConfig))
  val reg_vstart = usingVector.option(Reg(UInt(maxVLMax.log2.W)))
  val reg_vxsat = usingVector.option(Reg(Bool()))
  val reg_vxrm = usingVector.option(Reg(UInt(io.vector.get.vxrm.getWidth.W)))

  // 机器计数器禁止递增寄存器
  val reg_mcountinhibit = RegInit(0.U((CSR.firstHPM + nPerfCounters).W))
  // 禁止递增第0位表示mcycle禁止递增
  io.inhibit_cycle := reg_mcountinhibit(0)
  // 指令退休数计数器，禁止递增第2位表示禁止递增，递增量与退休宽度有关
  val reg_instret = WideCounter(64, io.retire, inhibit = reg_mcountinhibit(2))
  // 执行周期数计数器，enableCommitLog默认为False
  val reg_cycle = if (enableCommitLog) WideCounter(64, io.retire,     inhibit = reg_mcountinhibit(0))
    else withClock(io.ungated_clock) { WideCounter(64, !io.csr_stall, inhibit = reg_mcountinhibit(0)) }
  // 硬件性能监视事件寄存器
  val reg_hpmevent = io.counters.map(c => Reg(init = UInt(0, xLen)))
    (io.counters zip reg_hpmevent) foreach { case (c, e) => c.eventSel := e }
  // 硬件性能监视计数器
  val reg_hpmcounter = io.counters.zipWithIndex.map { case (c, i) =>
    WideCounter(CSR.hpmWidth, c.inc, reset = false, inhibit = reg_mcountinhibit(CSR.firstHPM+i)) }

  // 机器中断挂起
  val mip = Wire(init=reg_mip)
  /* 获取中断类型 */
  mip.lip := (io.interrupts.lip: Seq[Bool])
  mip.mtip := io.interrupts.mtip
  mip.msip := io.interrupts.msip
  mip.meip := io.interrupts.meip
  // seip is the OR of reg_mip.seip and the actual line from the PLIC
  io.interrupts.seip.foreach { mip.seip := reg_mip.seip || _ }
  mip.rocc := io.rocc_interrupt
  // 读取中断，结合支持的中断
  val read_mip = mip.asUInt & supported_interrupts
  val high_interrupts = (if (usingNMI) 0.U else io.interrupts.buserror.map(_ << CSR.busErrorIntCause).getOrElse(0.U))

  // 挂起的中断，结合中断使能
  val pending_interrupts = high_interrupts | (read_mip & reg_mie)
  // 调试中断
  val d_interrupts = io.interrupts.debug << CSR.debugIntCause
  // 不可屏蔽中断，不可屏蔽中断标志
  val (nmi_interrupts, nmiFlag) = io.interrupts.nmi.map(nmi =>
    (((nmi.unmi && reg_unmie) << CSR.unmiIntCause) |
    ((nmi.rnmi && reg_rnmie) << CSR.rnmiIntCause) |
    io.interrupts.buserror.map(_ << CSR.rnmiBEUCause).getOrElse(0.U),
    !io.interrupts.debug && (nmi.unmi && reg_unmie || nmi.rnmi && reg_rnmie))).getOrElse(0.U, false.B)
  // 需要机器态处理的中断
  val m_interrupts = Mux(nmie && (reg_mstatus.prv <= PRV.S || reg_mstatus.mie), ~(~pending_interrupts | read_mideleg), UInt(0))
  // 需要特权态处理的中断
  val s_interrupts = Mux(nmie && (reg_mstatus.prv < PRV.S || (reg_mstatus.prv === PRV.S && reg_mstatus.sie)), pending_interrupts & read_mideleg, UInt(0))
  // 按优先级选择中断
  val (anyInterrupt, whichInterrupt) = chooseInterrupt(Seq(s_interrupts, m_interrupts, nmi_interrupts, d_interrupts))
  // 中断最大位数
  val interruptMSB = BigInt(1) << (xLen-1)
  // 中断原因
  val interruptCause = UInt(interruptMSB) + (nmiFlag << (xLen-2)) + whichInterrupt
  /* 输出中断及相关信息 */
  io.interrupt := (anyInterrupt && !io.singleStep || reg_singleStepped) && !(reg_debug || io.status.cease)
  io.interrupt_cause := interruptCause
  io.bp := reg_bp take nBreakpoints
  io.mcontext := reg_mcontext.getOrElse(0.U)
  io.scontext := reg_scontext.getOrElse(0.U)
  io.pmp := reg_pmp.map(PMP(_))

  // 此掩码表示一部分支持的指令类型
  val isaMaskString =
    // 整型乘除
    (if (usingMulDiv) "M" else "") +
    // 原子
    (if (usingAtomics) "A" else "") +
    // 单精度浮点
    (if (fLen >= 32) "F" else "") +
    // 双精度浮点
    (if (fLen >= 64) "D" else "") +
    // 向量
    (if (usingVector) "V" else "") +
    // 位操作
    (if (usingBitManip) "B" else "") +
    // 压缩指令
    (if (usingCompressed) "C" else "")
  // 此掩码表示所有支持的指令类型
  val isaString = 
    // 嵌入式还是标准
    (if (coreParams.useRVE) "E" else "I") +
    // 一部分指令类型
    isaMaskString +
    // 自定义总是存在
    "X" +
    // 特权（监督）态
    (if (usingSupervisor) "S" else "") +
    // 用户态
    (if (usingUser) "U" else "")
  // misa寄存器，最左边两位是01表示32位，是10表示64位，最右边26位表示支持的指令类型
  val isaMax = (BigInt(log2Ceil(xLen) - 4) << (xLen-2)) | isaStringToMask(isaString)
  val reg_misa = Reg(init=UInt(isaMax))
  // 读mstatus寄存器
  val read_mstatus = io.status.asUInt()(xLen-1,0)
  // 读mtvec寄存器
  val read_mtvec = formTVec(reg_mtvec).padTo(xLen)
  // 读stvec寄存器
  val read_stvec = formTVec(reg_stvec).sextTo(xLen)

  // CSR地址和对应寄存器建立映射
  val read_mapping = LinkedHashMap[Int,Bits](
    /* 断点相关 */
    CSRs.tselect -> reg_tselect,
    CSRs.tdata1 -> reg_bp(reg_tselect).control.asUInt,
    CSRs.tdata2 -> reg_bp(reg_tselect).address.sextTo(xLen),
    CSRs.tdata3 -> reg_bp(reg_tselect).textra.asUInt,
    // 机器指令系统，指示支持的指令系统
    CSRs.misa -> reg_misa,
    // 机器状态
    CSRs.mstatus -> read_mstatus,
    // trap向量，指示trap的PC存入的地址
    CSRs.mtvec -> read_mtvec,
    // 机器中断挂起，指示中断挂起信息
    CSRs.mip -> read_mip,
    // 机器中断使能，指示中断使能信息
    CSRs.mie -> reg_mie,
    // 机器hart上下文空间指针，进入M模式陷阱处理程序时与用户寄存器交换
    CSRs.mscratch -> reg_mscratch,
    // 机器异常程序计数器，记录异常或中断而trap的虚拟地址
    CSRs.mepc -> readEPC(reg_mepc).sextTo(xLen),
    // 机器trap特殊值，帮助处理trap，比如错误的地址或指令
    CSRs.mtval -> reg_mtval.sextTo(xLen),
    // 机器trap原因，指示机器trap的事件
    CSRs.mcause -> reg_mcause,
    // 硬件线程ID
    CSRs.mhartid -> io.hartid)

  // 调试CSR映射建立
  val debug_csrs = if (!usingDebug) LinkedHashMap() else LinkedHashMap[Int,Bits](
    CSRs.dcsr -> reg_dcsr.asUInt,
    CSRs.dpc -> readEPC(reg_dpc).sextTo(xLen),
    CSRs.dscratch -> reg_dscratch.asUInt) ++
    reg_dscratch1.map(r => CSRs.dscratch1 -> r)

  // 不可屏蔽中断CSR建立映射
  val read_mnstatus = WireInit(0.U.asTypeOf(new MStatus()))
  read_mnstatus.mpp := io.status.mpp
  val nmi_csrs = if (!usingNMI) LinkedHashMap() else LinkedHashMap[Int,Bits](
    CSRs.mnscratch -> reg_mnscratch,
    CSRs.mnepc -> readEPC(reg_mnepc).sextTo(xLen),
    CSRs.mncause -> reg_mncause,
    CSRs.mnstatus -> read_mnstatus.asUInt)

  // 上下文CSR建立映射
  val context_csrs = LinkedHashMap[Int,Bits]() ++
    reg_mcontext.map(r => CSRs.mcontext -> r) ++
    reg_scontext.map(r => CSRs.scontext -> r)

  // 浮点相关CSR建立映射
  val read_fcsr = Cat(reg_frm, reg_fflags)
  val fp_csrs = LinkedHashMap[Int,Bits]() ++
    usingFPU.option(CSRs.fflags -> reg_fflags) ++
    usingFPU.option(CSRs.frm -> reg_frm) ++
    (usingFPU || usingVector).option(CSRs.fcsr -> read_fcsr)

  // 向量相关CSR建立映射
  val read_vcsr = Cat(reg_vxrm.getOrElse(0.U), reg_vxsat.getOrElse(0.U))
  val vector_csrs = if (!usingVector) LinkedHashMap() else LinkedHashMap[Int,Bits](
    CSRs.vxsat -> reg_vxsat.get,
    CSRs.vxrm -> reg_vxrm.get,
    CSRs.vcsr -> read_vcsr,
    CSRs.vstart -> reg_vstart.get,
    CSRs.vtype -> reg_vconfig.get.vtype.asUInt,
    CSRs.vl -> reg_vconfig.get.vl,
    CSRs.vlenb -> (vLen / 8).U)

  /* 全部合并到统一的CSR映射 */
  read_mapping ++= debug_csrs
  read_mapping ++= nmi_csrs
  read_mapping ++= context_csrs
  read_mapping ++= fp_csrs
  read_mapping ++= vector_csrs

  // 有基础计数器，建立计数器相关CSR映射
  if (coreParams.haveBasicCounters) {
    // 机器计数器禁止递增
    read_mapping += CSRs.mcountinhibit -> reg_mcountinhibit
    // 机器周期，记录hart运行的时钟周期数
    read_mapping += CSRs.mcycle -> reg_cycle
    // 机器指令退休，记录hart退休的指令数
    read_mapping += CSRs.minstret -> reg_instret

    for (((e, c), i) <- (reg_hpmevent.padTo(CSR.nHPM, UInt(0))
                         zip reg_hpmcounter.map(x => x: UInt).padTo(CSR.nHPM, UInt(0))) zipWithIndex) {
      // 机器硬件性能监视事件
      read_mapping += (i + CSR.firstHPE) -> e
      // 机器硬件性能监视计数器
      read_mapping += (i + CSR.firstMHPC) -> c
      // mhpmcounter只读复制
      if (usingUser) read_mapping += (i + CSR.firstHPC) -> c
      // 32位系统需要再记录高32位
      if (xLen == 32) {
        // mhpmcounter
        read_mapping += (i + CSR.firstMHPCH) -> (c >> 32) 
        // mhpmcounter只读复制
        if (usingUser) read_mapping += (i + CSR.firstHPCH) -> (c >> 32)
      }
    }

    if (usingUser) {
      // 机器态向下传递计数器使能
      read_mapping += CSRs.mcounteren -> read_mcounteren
      // mcycle只读复制，执行周期数计数器
      read_mapping += CSRs.cycle -> reg_cycle
      // minstret只读复制，指令退休数计数器
      read_mapping += CSRs.instret -> reg_instret
    }

    if (xLen == 32) {
      // 如果32位指令系统，将高32位存入计数器h
      read_mapping += CSRs.mcycleh -> (reg_cycle >> 32)
      read_mapping += CSRs.minstreth -> (reg_instret >> 32)
      if (usingUser) {
        // 如果32位指令系统，将高32位存入计数器h的只读复制
        read_mapping += CSRs.cycleh -> (reg_cycle >> 32)
        read_mapping += CSRs.instreth -> (reg_instret >> 32)
      }
    }
  }

  // 特权态CSR建立映射
  if (usingSupervisor) {
    // 特权态中断使能，机器中断使能与机器中断委托
    val read_sie = reg_mie & read_mideleg
    // 特权态中断挂起，奇迹中断挂起与机器中断委托
    val read_sip = read_mip & read_mideleg
    /* 特权态状态 */
    val read_sstatus = Wire(init = 0.U.asTypeOf(new MStatus))
    read_sstatus.sd := io.status.sd
    read_sstatus.uxl := io.status.uxl
    read_sstatus.sd_rv32 := io.status.sd_rv32
    read_sstatus.mxr := io.status.mxr
    read_sstatus.sum := io.status.sum
    read_sstatus.xs := io.status.xs
    read_sstatus.fs := io.status.fs
    read_sstatus.vs := io.status.vs
    read_sstatus.spp := io.status.spp
    read_sstatus.spie := io.status.spie
    read_sstatus.sie := io.status.sie

    // 特权态状态
    read_mapping += CSRs.sstatus -> (read_sstatus.asUInt())(xLen-1,0)
    // 特权态中断挂起
    read_mapping += CSRs.sip -> read_sip.asUInt
    // 特权态中断使能
    read_mapping += CSRs.sie -> read_sie.asUInt
    // 特权态hart上下文空间指针
    read_mapping += CSRs.sscratch -> reg_sscratch
    read_mapping += CSRs.scause -> reg_scause
    // 特权态trap特殊值
    read_mapping += CSRs.stval -> reg_stval.sextTo(xLen)
    // 特权态地址翻译和保护，记录根页表的物理页号
    read_mapping += CSRs.satp -> reg_satp.asUInt
    // 特权态异常程序计数器
    read_mapping += CSRs.sepc -> readEPC(reg_sepc).sextTo(xLen)
    // 特权态trap向量
    read_mapping += CSRs.stvec -> read_stvec
    read_mapping += CSRs.scounteren -> read_scounteren
    // 机器中断委托
    read_mapping += CSRs.mideleg -> read_mideleg
    // 机器异常委托
    read_mapping += CSRs.medeleg -> read_medeleg
  }

  /* 物理地址保护CSR建立映射 */
  // 每个CSR可以有几个PMP config
  val pmpCfgPerCSR = xLen / new PMPConfig().getWidth
  def pmpCfgIndex(i: Int) = (xLen / 32) * (i / pmpCfgPerCSR)
  if (reg_pmp.nonEmpty) {
    require(reg_pmp.size <= CSR.maxPMPs)
    // 读pmp
    val read_pmp = reg_pmp.padTo(CSR.maxPMPs, 0.U.asTypeOf(new PMP))
    // pmp config
    for (i <- 0 until read_pmp.size by pmpCfgPerCSR)
      read_mapping += (CSRs.pmpcfg0 + pmpCfgIndex(i)) -> read_pmp.map(_.cfg).slice(i, i + pmpCfgPerCSR).asUInt
    // pmp addr
    for ((pmp, i) <- read_pmp zipWithIndex)
      read_mapping += (CSRs.pmpaddr0 + i) -> pmp.readAddr
  }

  // 自定义CSRs建立映射
  val reg_custom = customCSRs.map { csr =>
    require(csr.mask >= 0 && csr.mask.bitLength <= xLen)
    require(!read_mapping.contains(csr.id))
    val reg = csr.init.map(init => RegInit(init.U(xLen.W))).getOrElse(Reg(UInt(xLen.W)))
    read_mapping += csr.id -> reg
    reg
  }

  // mimpid、marchid和mvendorid建立映射，除非被自定义CSR重写，否则都是0
  Seq(CSRs.mimpid, CSRs.marchid, CSRs.mvendorid).foreach(id => read_mapping.getOrElseUpdate(id, 0.U))

  // 译码的CSR地址
  val decoded_addr = read_mapping map { case (k, v) => k -> (io.rw.addr === k) }
  // 通过CSR命令得到wdata
  val wdata = readModifyWriteCSR(io.rw.cmd, io.rw.rdata, io.rw.wdata)

  // 有立即数为系统指令
  val system_insn = io.rw.cmd === CSR.I
  // 译码表
  val decode_table = Seq(        SCALL->       List(Y,N,N,N,N,N),     // 系统调用
                                 SBREAK->      List(N,Y,N,N,N,N),     // 控制权返回调试终端
                                 MRET->        List(N,N,Y,N,N,N),     // 从机器态trap返回
                                 CEASE->       List(N,N,N,Y,N,N),
                                 WFI->         List(N,N,N,N,Y,N)) ++  // 等待中断到来
    usingDebug.option(           DRET->        List(N,N,Y,N,N,N)) ++
    usingNMI.option(             MNRET->       List(N,N,Y,N,N,N)) ++
    coreParams.haveCFlush.option(CFLUSH_D_L1-> List(N,N,N,N,N,N)) ++
    usingSupervisor.option(      SRET->        List(N,N,Y,N,N,N)) ++  // 从特权态trap返回
    usingVM.option(              SFENCE_VMA->  List(N,N,N,N,N,Y))     // 用于将内存管理数据结构的更新与当前执行同步，更新数据前会排序

  // 译码
  val insn_call :: insn_break :: insn_ret :: insn_cease :: insn_wfi :: insn_sfence :: Nil =
    DecodeLogic(io.rw.addr << 20, decode_table(0)._2.map(x=>X), decode_table).map(system_insn && _.asBool)

  for (io_dec <- io.decode) {
    def decodeAny(m: LinkedHashMap[Int,Bits]): Bool = m.map { case(k: Int, _: Bits) => io_dec.csr === k }.reduce(_||_)
    def decodeFast(s: Seq[Int]): Bool = DecodeLogic(io_dec.csr, s.map(_.U), (read_mapping -- s).keys.toList.map(_.U))

    // 译码
    val _ :: is_break :: is_ret :: _ :: is_wfi :: is_sfence :: Nil =
      DecodeLogic(io_dec.csr << 20, decode_table(0)._2.map(x=>X), decode_table).map(_.asBool)

    // 允许WFI
    val allow_wfi = Bool(!usingSupervisor) || reg_mstatus.prv > PRV.S || !reg_mstatus.tw
    // 允许SFence_VMA
    val allow_sfence_vma = Bool(!usingVM) || reg_mstatus.prv > PRV.S || !reg_mstatus.tvm
    // 允许特权态异常返回
    val allow_sret = Bool(!usingSupervisor) || reg_mstatus.prv > PRV.S || !reg_mstatus.tsr
    // 允许计数器
    val counter_addr = io_dec.csr(log2Ceil(read_mcounteren.getWidth)-1, 0)
    val allow_counter = (reg_mstatus.prv > PRV.S || read_mcounteren(counter_addr)) &&
      (!usingSupervisor || reg_mstatus.prv >= PRV.S || read_scounteren(counter_addr))
    /* 通过机器状态、指令系统组成以及特权级别等判断一系列非法 */
    io_dec.fp_illegal := io.status.fs === 0 || !reg_misa('f'-'a')
    io_dec.vector_illegal := io.status.vs === 0 || !reg_misa('v'-'a')
    io_dec.fp_csr := decodeFast(fp_csrs.keys.toList)
    io_dec.rocc_illegal := io.status.xs === 0 || !reg_misa('x'-'a')
    io_dec.read_illegal := reg_mstatus.prv < io_dec.csr(9,8) ||
      !decodeAny(read_mapping) ||
      io_dec.csr === CSRs.satp && !allow_sfence_vma ||
      (io_dec.csr.inRange(CSR.firstCtr, CSR.firstCtr + CSR.nCtr) || io_dec.csr.inRange(CSR.firstCtrH, CSR.firstCtrH + CSR.nCtr)) && !allow_counter ||
      decodeFast(debug_csrs.keys.toList) && !reg_debug ||
      decodeFast(vector_csrs.keys.toList) && io_dec.vector_illegal ||
      io_dec.fp_csr && io_dec.fp_illegal
    io_dec.write_illegal := io_dec.csr(11,10).andR
    io_dec.write_flush := !(io_dec.csr >= CSRs.mscratch && io_dec.csr <= CSRs.mtval || io_dec.csr >= CSRs.sscratch && io_dec.csr <= CSRs.stval)
    io_dec.system_illegal := reg_mstatus.prv < io_dec.csr(9,8) ||
      is_wfi && !allow_wfi ||
      is_ret && !allow_sret ||
      is_ret && io_dec.csr(10) && io_dec.csr(7) && !reg_debug ||
      is_sfence && !allow_sfence_vma
  }

  // 原因
  val cause =
    Mux(insn_call, reg_mstatus.prv + Causes.user_ecall,
    Mux[UInt](insn_break, Causes.breakpoint, io.cause))
  // 原因低位
  val cause_lsbs = cause(log2Ceil(1 + CSR.busErrorIntCause)-1, 0)
  /* 判定是否为调试原因 */
  val causeIsDebugInt = cause(xLen-1) && cause_lsbs === CSR.debugIntCause
  val causeIsDebugTrigger = !cause(xLen-1) && cause_lsbs === CSR.debugTriggerCause
  val causeIsDebugBreak = !cause(xLen-1) && insn_break && Cat(reg_dcsr.ebreakm, reg_dcsr.ebreakh, reg_dcsr.ebreaks, reg_dcsr.ebreaku)(reg_mstatus.prv)
  /* 因为调试而trap */
  val trapToDebug = Bool(usingDebug) && (reg_singleStepped || causeIsDebugInt || causeIsDebugTrigger || causeIsDebugBreak || reg_debug)
  val debugEntry = p(DebugModuleKey).map(_.debugEntry).getOrElse(BigInt(0x800))
  val debugException = p(DebugModuleKey).map(_.debugException).getOrElse(BigInt(0x808))
  val debugTVec = Mux(reg_debug, Mux(insn_break, debugEntry.U, debugException.U), debugEntry.U)
  // 异常或中断是否可以委托
  val delegate = Bool(usingSupervisor) && reg_mstatus.prv <= PRV.S && Mux(cause(xLen-1), read_mideleg(cause_lsbs), read_medeleg(cause_lsbs))
  // mtvec的Base要4字节对齐
  def mtvecBaseAlign = 2
  // mtvec中断对齐位数
  def mtvecInterruptAlign = {
    require(reg_mip.getWidth <= xLen)
    log2Ceil(xLen)
  }
  val notDebugTVec = {
    // PC基地址
    val base = Mux(delegate, read_stvec, read_mtvec)
    // 6位trap原因+“00”
    val interruptOffset = cause(mtvecInterruptAlign-1, 0) << mtvecBaseAlign
    // PC进行8位对齐后接上6位trap原因加“00”
    val interruptVec = Cat(base >> (mtvecInterruptAlign + mtvecBaseAlign), interruptOffset)
    // 如果Mode为1且是中断，那么采用向量模式得到PC
    val doVector = base(0) && cause(cause.getWidth-1) && (cause_lsbs >> mtvecInterruptAlign) === 0
    // 按照Mode选择PC地址，向量模式需要去掉Mode
    Mux(doVector, interruptVec, base >> mtvecBaseAlign << mtvecBaseAlign)
  }

  // 原因是无法屏蔽中断
  val causeIsUnmiInt = cause(xLen-1) && cause(xLen-2) && cause_lsbs === CSR.unmiIntCause
  val causeIsRnmiInt = cause(xLen-1) && cause(xLen-2) && (cause_lsbs === CSR.rnmiIntCause || cause_lsbs === CSR.rnmiBEUCause)
  val causeIsRnmiBEU = cause(xLen-1) && cause(xLen-2) && cause_lsbs === CSR.rnmiBEUCause
  val causeIsNmi = causeIsUnmiInt || causeIsRnmiInt
  val nmiTVecInt = io.interrupts.nmi.map(nmi => Mux(causeIsRnmiInt, nmi.rnmi_interrupt_vector, nmi.unmi_interrupt_vector)).getOrElse(0.U)
  val nmiTVecXcpt = io.interrupts.nmi.map(nmi => Mux(reg_unmie, nmi.rnmi_exception_vector, nmi.unmi_exception_vector)).getOrElse(0.U)
  val trapToNmiInt = usingNMI.B && causeIsNmi
  val trapToNmiXcpt = usingNMI.B && !nmie
  val trapToNmi = trapToNmiInt || trapToNmiXcpt
  val nmiTVec = (Mux(causeIsNmi, nmiTVecInt, nmiTVecXcpt)>>1)<<1

  // 选择PC地址，输出trap信息
  val tvec = Mux(trapToDebug, debugTVec, Mux(trapToNmi, nmiTVec, notDebugTVec))
  io.evec := tvec
  io.ptbr := reg_satp
  io.eret := insn_call || insn_break || insn_ret
  io.singleStep := reg_dcsr.step && !reg_debug
  io.status := reg_mstatus
  io.status.sd := io.status.fs.andR || io.status.xs.andR || io.status.vs.andR
  io.status.debug := reg_debug
  io.status.isa := reg_misa
  // 用户态指令系统宽度，64位为10，32位为01
  io.status.uxl := (if (usingUser) log2Ceil(xLen) - 4 else 0)
  // 特权态指令系统宽度，64位为10，32位为01
  io.status.sxl := (if (usingSupervisor) log2Ceil(xLen) - 4 else 0)
  io.status.dprv := Reg(next = Mux(reg_mstatus.mprv && !reg_debug, reg_mstatus.mpp, reg_mstatus.prv))
  if (xLen == 32)
    io.status.sd_rv32 := io.status.sd

  // 有异常，包括call和break
  val exception = insn_call || insn_break || io.exception
  assert(PopCount(insn_ret :: insn_call :: insn_break :: io.exception :: Nil) <= 1, "these conditions must be mutually exclusive")

  /* 更新wfi寄存器信息 */
  when (insn_wfi && !io.singleStep && !reg_debug) { reg_wfi := true }
  when (pending_interrupts.orR || io.interrupts.debug || exception) { reg_wfi := false }
  io.interrupts.nmi.map(nmi => when (nmi.unmi || nmi.rnmi) { reg_wfi := false } )

  /* 更新singleStepped寄存器信息 */
  when (io.retire(0) || exception) { reg_singleStepped := true }
  when (!io.singleStep) { reg_singleStepped := false }
  assert(!io.singleStep || io.retire <= UInt(1))
  assert(!reg_singleStepped || io.retire === UInt(0))

  // 生成mepc
  val epc = formEPC(io.pc)
  val noCause :: mCause :: hCause :: sCause :: uCause :: Nil = Enum(5)
  val xcause_dest = Wire(init = noCause)

  when (exception) {
    // 因为调试trap
    when (trapToDebug) {
      when (!reg_debug) {
        reg_debug := true
        reg_dpc := epc
        reg_dcsr.cause := Mux(reg_singleStepped, 4, Mux(causeIsDebugInt, 3, Mux[UInt](causeIsDebugTrigger, 2, 1)))
        reg_dcsr.prv := trimPrivilege(reg_mstatus.prv)
        new_prv := PRV.M
      }
    // 因为无法屏蔽中断trap
    }.elsewhen (trapToNmiInt) {
      when (reg_rnmie || reg_unmie && causeIsUnmiInt) {
        reg_rnmie := false.B
        reg_unmie := !causeIsUnmiInt
        reg_mnepc := epc
        reg_mncause := (BigInt(1) << (xLen-1)).U | Mux(causeIsUnmiInt, 1.U, Mux(causeIsRnmiBEU, 3.U, 2.U))
        reg_mnstatus.mpp := trimPrivilege(reg_mstatus.prv)
        new_prv := PRV.M
      }
    // 可以委托给特权态
    }.elsewhen (delegate && nmie) {
      reg_sepc := epc
      reg_scause := cause
      xcause_dest := sCause
      reg_stval := io.tval
      reg_mstatus.spie := reg_mstatus.sie
      reg_mstatus.spp := reg_mstatus.prv
      reg_mstatus.sie := false
      new_prv := PRV.S
    // 只能由机器态处理
    }.otherwise {
      reg_mepc := epc
      reg_mcause := cause
      xcause_dest := mCause
      reg_mtval := io.tval
      reg_mstatus.mpie := reg_mstatus.mie
      reg_mstatus.mpp := trimPrivilege(reg_mstatus.prv)
      reg_mstatus.mie := false
      new_prv := PRV.M
    }
  }

  for (i <- 0 until supported_interrupts.getWidth) {
    val en = exception && (supported_interrupts & (BigInt(1) << i).U) =/= 0 && cause === (BigInt(1) << (xLen - 1)).U + i
    val delegable = (delegable_interrupts & (BigInt(1) << i).U) =/= 0
    cover(en && !delegate, s"INTERRUPT_M_$i")
    cover(en && delegable && delegate, s"INTERRUPT_S_$i")
  }
  for (i <- 0 until xLen) {
    val supported_exceptions: BigInt = 0x8fe |
      (if (usingCompressed && !coreParams.misaWritable) 0 else 1) |
      (if (usingUser) 0x100 else 0) |
      (if (usingSupervisor) 0x200 else 0) |
      (if (usingVM) 0xb000 else 0)
    if (((supported_exceptions >> i) & 1) != 0) {
      val en = exception && cause === i
      val delegable = (delegable_exceptions & (BigInt(1) << i).U) =/= 0
      cover(en && !delegate, s"EXCEPTION_M_$i")
      cover(en && delegable && delegate, s"EXCEPTION_S_$i")
    }
  }

  // trap返回
  when (insn_ret) {
    val ret_prv = WireInit(UInt(), DontCare)
    when (Bool(usingSupervisor) && !io.rw.addr(9)) {
      reg_mstatus.sie := reg_mstatus.spie
      reg_mstatus.spie := true
      reg_mstatus.spp := PRV.U
      ret_prv := reg_mstatus.spp
      io.evec := readEPC(reg_sepc)
    }.elsewhen (Bool(usingDebug) && io.rw.addr(10) && io.rw.addr(7)) {
      ret_prv := reg_dcsr.prv
      reg_debug := false
      io.evec := readEPC(reg_dpc)
    }.elsewhen (Bool(usingNMI) && io.rw.addr(10) && !io.rw.addr(7)) {
      ret_prv := reg_mnstatus.mpp
      reg_rnmie := true.B
      reg_unmie := true.B
      io.evec := readEPC(reg_mnepc)
    }.otherwise {
      reg_mstatus.mie := reg_mstatus.mpie
      reg_mstatus.mpie := true
      reg_mstatus.mpp := legalizePrivilege(PRV.U)
      ret_prv := reg_mstatus.mpp
      io.evec := readEPC(reg_mepc)
    }

    new_prv := ret_prv
    when (usingUser && ret_prv < PRV.M) {
      reg_mstatus.mprv := false
    }
  }

  // 时钟计数
  io.time := reg_cycle
  // 因等待或cease而暂停
  io.csr_stall := reg_wfi || io.status.cease
  io.status.cease := RegEnable(true.B, false.B, insn_cease)
  io.status.wfi := reg_wfi

  for ((io, reg) <- io.customCSRs zip reg_custom) {
    io.wen := false
    io.wdata := wdata
    io.value := reg
  }

  io.rw.rdata := Mux1H(for ((k, v) <- read_mapping) yield decoded_addr(k) -> v)

  // cover access to register
  read_mapping.foreach( {case (k, v) => {
    when (!k(11,10).andR) {  // Cover points for RW CSR registers
      cover(io.rw.cmd.isOneOf(CSR.W, CSR.S, CSR.C) && io.rw.addr===k, "CSR_access_"+k.toString, "Cover Accessing Core CSR field")
    } .otherwise { // Cover points for RO CSR registers
      cover(io.rw.cmd===CSR.R && io.rw.addr===k, "CSR_access_"+k.toString, "Cover Accessing Core CSR field")
    }
  }})

  /* 设置为脏 */
  val set_vs_dirty = Wire(init = io.vector.map(_.set_vs_dirty).getOrElse(false.B))
  io.vector.foreach { vio =>
    when (set_vs_dirty) {
      assert(reg_mstatus.vs > 0)
      reg_mstatus.vs := 3
    }
  }

  val set_fs_dirty = Wire(init = io.set_fs_dirty.getOrElse(false.B))
  if (coreParams.haveFSDirty) {
    when (set_fs_dirty) {
      assert(reg_mstatus.fs > 0)
      reg_mstatus.fs := 3
    }
  }

  io.fcsr_rm := reg_frm
  when (io.fcsr_flags.valid) {
    reg_fflags := reg_fflags | io.fcsr_flags.bits
    set_fs_dirty := true
  }

  io.vector.foreach { vio =>
    when (vio.set_vxsat) {
      reg_vxsat.get := true
      set_vs_dirty := true
    }
  }

  // 写CSR
  val csr_wen = io.rw.cmd.isOneOf(CSR.S, CSR.C, CSR.W)
  // 写计数器
  io.csrw_counter := Mux(coreParams.haveBasicCounters && csr_wen && (io.rw.addr.inRange(CSRs.mcycle, CSRs.mcycle + CSR.nCtr) || io.rw.addr.inRange(CSRs.mcycleh, CSRs.mcycleh + CSR.nCtr)), UIntToOH(io.rw.addr(log2Ceil(CSR.nCtr+nPerfCounters)-1, 0)), 0.U)
  when (csr_wen) {
    when (decoded_addr(CSRs.mstatus)) {
      val new_mstatus = new MStatus().fromBits(wdata)
      reg_mstatus.mie := new_mstatus.mie
      reg_mstatus.mpie := new_mstatus.mpie

      if (usingUser) {
        reg_mstatus.mprv := new_mstatus.mprv
        reg_mstatus.mpp := legalizePrivilege(new_mstatus.mpp)
        if (usingSupervisor) {
          reg_mstatus.spp := new_mstatus.spp
          reg_mstatus.spie := new_mstatus.spie
          reg_mstatus.sie := new_mstatus.sie
          reg_mstatus.tw := new_mstatus.tw
          reg_mstatus.tsr := new_mstatus.tsr
        }
        if (usingVM) {
          reg_mstatus.mxr := new_mstatus.mxr
          reg_mstatus.sum := new_mstatus.sum
          reg_mstatus.tvm := new_mstatus.tvm
        }
      }

      if (usingSupervisor || usingFPU) reg_mstatus.fs := formFS(new_mstatus.fs)
      reg_mstatus.vs := formVS(new_mstatus.vs)
    }
    when (decoded_addr(CSRs.misa)) {
      val mask = UInt(isaStringToMask(isaMaskString), xLen)
      val f = wdata('f' - 'a')
      // suppress write if it would cause the next fetch to be misaligned
      when (!usingCompressed || !io.pc(1) || wdata('c' - 'a')) {
        if (coreParams.misaWritable)
          reg_misa := ~(~wdata | (!f << ('d' - 'a'))) & mask | reg_misa & ~mask
      }
    }
    when (decoded_addr(CSRs.mip)) {
      // MIP should be modified based on the value in reg_mip, not the value
      // in read_mip, since read_mip.seip is the OR of reg_mip.seip and
      // io.interrupts.seip.  We don't want the value on the PLIC line to
      // inadvertently be OR'd into read_mip.seip.
      val new_mip = readModifyWriteCSR(io.rw.cmd, reg_mip.asUInt, io.rw.wdata).asTypeOf(new MIP)
      if (usingSupervisor) {
        reg_mip.ssip := new_mip.ssip
        reg_mip.stip := new_mip.stip
        reg_mip.seip := new_mip.seip
      }
    }
    when (decoded_addr(CSRs.mie))      { reg_mie := wdata & supported_interrupts }
    when (decoded_addr(CSRs.mepc))     { reg_mepc := formEPC(wdata) }
    when (decoded_addr(CSRs.mscratch)) { reg_mscratch := wdata }
    if (mtvecWritable)
      when (decoded_addr(CSRs.mtvec))  { reg_mtvec := wdata }
    when (decoded_addr(CSRs.mcause))   { reg_mcause := wdata & UInt((BigInt(1) << (xLen-1)) + (BigInt(1) << whichInterrupt.getWidth) - 1) }
    when (decoded_addr(CSRs.mtval))    { reg_mtval := wdata(vaddrBitsExtended-1,0) }

    if (usingNMI) {
      val new_mnstatus = new MStatus().fromBits(wdata)
      when (decoded_addr(CSRs.mnscratch)) { reg_mnscratch := wdata }
      when (decoded_addr(CSRs.mnepc))     { reg_mnepc := formEPC(wdata) }
      when (decoded_addr(CSRs.mncause))   { reg_mncause := wdata & UInt((BigInt(1) << (xLen-1)) + BigInt(3)) }
      when (decoded_addr(CSRs.mnstatus))  { reg_mnstatus.mpp := legalizePrivilege(new_mnstatus.mpp) }
    }

    for (((e, c), i) <- (reg_hpmevent zip reg_hpmcounter) zipWithIndex) {
      writeCounter(i + CSR.firstMHPC, c, wdata)
      when (decoded_addr(i + CSR.firstHPE)) { e := perfEventSets.maskEventSelector(wdata) }
    }
    if (coreParams.haveBasicCounters) {
      when (decoded_addr(CSRs.mcountinhibit)) { reg_mcountinhibit := wdata & ~2.U(xLen.W) }  // mcountinhibit bit [1] is tied zero
      writeCounter(CSRs.mcycle, reg_cycle, wdata)
      writeCounter(CSRs.minstret, reg_instret, wdata)
    }

    if (usingFPU) {
      when (decoded_addr(CSRs.fflags)) { set_fs_dirty := true; reg_fflags := wdata }
      when (decoded_addr(CSRs.frm))    { set_fs_dirty := true; reg_frm := wdata }
      when (decoded_addr(CSRs.fcsr)) {
        set_fs_dirty := true
        reg_fflags := wdata
        reg_frm := wdata >> reg_fflags.getWidth
      }
    }
    if (usingDebug) {
      when (decoded_addr(CSRs.dcsr)) {
        val new_dcsr = new DCSR().fromBits(wdata)
        reg_dcsr.step := new_dcsr.step
        reg_dcsr.ebreakm := new_dcsr.ebreakm
        if (usingSupervisor) reg_dcsr.ebreaks := new_dcsr.ebreaks
        if (usingUser) reg_dcsr.ebreaku := new_dcsr.ebreaku
        if (usingUser) reg_dcsr.prv := legalizePrivilege(new_dcsr.prv)
      }
      when (decoded_addr(CSRs.dpc))      { reg_dpc := formEPC(wdata) }
      when (decoded_addr(CSRs.dscratch)) { reg_dscratch := wdata }
      reg_dscratch1.foreach { r =>
        when (decoded_addr(CSRs.dscratch1)) { r := wdata }
      }
    }
    if (usingSupervisor) {
      when (decoded_addr(CSRs.sstatus)) {
        val new_sstatus = new MStatus().fromBits(wdata)
        reg_mstatus.sie := new_sstatus.sie
        reg_mstatus.spie := new_sstatus.spie
        reg_mstatus.spp := new_sstatus.spp
        reg_mstatus.fs := formFS(new_sstatus.fs)
        reg_mstatus.vs := formVS(new_sstatus.vs)
        if (usingVM) {
          reg_mstatus.mxr := new_sstatus.mxr
          reg_mstatus.sum := new_sstatus.sum
        }
      }
      when (decoded_addr(CSRs.sip)) {
        val new_sip = new MIP().fromBits((read_mip & ~read_mideleg) | (wdata & read_mideleg))
        reg_mip.ssip := new_sip.ssip
      }
      when (decoded_addr(CSRs.satp)) {
        if (usingVM) {
          val new_satp = new PTBR().fromBits(wdata)
          val valid_modes = 0 +: (minPgLevels to pgLevels).map(new_satp.pgLevelsToMode(_))
          when (new_satp.mode.isOneOf(valid_modes.map(_.U))) {
            reg_satp.mode := new_satp.mode & valid_modes.reduce(_|_)
            reg_satp.ppn := new_satp.ppn(ppnBits-1,0)
            if (asIdBits > 0) reg_satp.asid := new_satp.asid(asIdBits-1,0)
          }
        }
      }
      when (decoded_addr(CSRs.sie))      { reg_mie := (reg_mie & ~read_mideleg) | (wdata & read_mideleg) }
      when (decoded_addr(CSRs.sscratch)) { reg_sscratch := wdata }
      when (decoded_addr(CSRs.sepc))     { reg_sepc := formEPC(wdata) }
      when (decoded_addr(CSRs.stvec))    { reg_stvec := wdata }
      when (decoded_addr(CSRs.scause))   { reg_scause := wdata & UInt((BigInt(1) << (xLen-1)) + 31) /* only implement 5 LSBs and MSB */ }
      when (decoded_addr(CSRs.stval))    { reg_stval := wdata(vaddrBitsExtended-1,0) }
      when (decoded_addr(CSRs.mideleg))  { reg_mideleg := wdata }
      when (decoded_addr(CSRs.medeleg))  { reg_medeleg := wdata }
      when (decoded_addr(CSRs.scounteren)) { reg_scounteren := wdata }
    }
    if (usingUser) {
      when (decoded_addr(CSRs.mcounteren)) { reg_mcounteren := wdata }
    }
    if (nBreakpoints > 0) {
      when (decoded_addr(CSRs.tselect)) { reg_tselect := wdata }

      for ((bp, i) <- reg_bp.zipWithIndex) {
        when (i === reg_tselect && (!bp.control.dmode || reg_debug)) {
          when (decoded_addr(CSRs.tdata2)) { bp.address := wdata }
           when (decoded_addr(CSRs.tdata3)) {
            if (coreParams.mcontextWidth > 0) {
              bp.textra.mselect := wdata(bp.textra.mselectPos)
              bp.textra.mvalue  := wdata >> bp.textra.mvaluePos
            }
            if (coreParams.scontextWidth > 0) {
              bp.textra.sselect := wdata(bp.textra.sselectPos)
              bp.textra.svalue  := wdata >> bp.textra.svaluePos
            }
          }
          when (decoded_addr(CSRs.tdata1)) {
            bp.control := wdata.asTypeOf(bp.control)

            val prevChain = if (i == 0) false.B else reg_bp(i-1).control.chain
            val prevDMode = if (i == 0) false.B else reg_bp(i-1).control.dmode
            val nextChain = if (i >= nBreakpoints-1) true.B else reg_bp(i+1).control.chain
            val nextDMode = if (i >= nBreakpoints-1) true.B else reg_bp(i+1).control.dmode
            val newBPC = readModifyWriteCSR(io.rw.cmd, bp.control.asUInt, io.rw.wdata).asTypeOf(bp.control)
            val dMode = newBPC.dmode && reg_debug && (prevDMode || !prevChain)
            bp.control.dmode := dMode
            when (dMode || (newBPC.action > 1.U)) { bp.control.action := newBPC.action }.otherwise { bp.control.action := 0.U }
            bp.control.chain := newBPC.chain && !(prevChain || nextChain) && (dMode || !nextDMode)
          }
        }
      }
    }
    reg_mcontext.foreach { r => when (decoded_addr(CSRs.mcontext)) { r := wdata }}
    reg_scontext.foreach { r => when (decoded_addr(CSRs.scontext)) { r := wdata }}
    if (reg_pmp.nonEmpty) for (((pmp, next), i) <- (reg_pmp zip (reg_pmp.tail :+ reg_pmp.last)) zipWithIndex) {
      require(xLen % pmp.cfg.getWidth == 0)
      when (decoded_addr(CSRs.pmpcfg0 + pmpCfgIndex(i)) && !pmp.cfgLocked) {
        val newCfg = new PMPConfig().fromBits(wdata >> ((i * pmp.cfg.getWidth) % xLen))
        pmp.cfg := newCfg
        // disallow unreadable but writable PMPs
        pmp.cfg.w := newCfg.w && newCfg.r
        // can't select a=NA4 with coarse-grained PMPs
        if (pmpGranularity.log2 > PMP.lgAlign)
          pmp.cfg.a := Cat(newCfg.a(1), newCfg.a.orR)
      }
      when (decoded_addr(CSRs.pmpaddr0 + i) && !pmp.addrLocked(next)) {
        pmp.addr := wdata
      }
    }
    for ((io, csr, reg) <- (io.customCSRs, customCSRs, reg_custom).zipped) {
      val mask = csr.mask.U(xLen.W)
      when (decoded_addr(csr.id)) {
        reg := (wdata & mask) | (reg & ~mask)
        io.wen := true
      }
    }
    if (usingVector) {
      when (decoded_addr(CSRs.vstart)) { set_vs_dirty := true; reg_vstart.get := wdata }
      when (decoded_addr(CSRs.vxrm))   { set_vs_dirty := true; reg_vxrm.get := wdata }
      when (decoded_addr(CSRs.vxsat))  { set_vs_dirty := true; reg_vxsat.get := wdata }
      when (decoded_addr(CSRs.vcsr))   {
        set_vs_dirty := true
        reg_vxsat.get := wdata
        reg_vxrm.get := wdata >> 1
      }
    }
  }

  io.vector.map { vio =>
    when (vio.set_vconfig.valid) {
      // user of CSRFile is responsible for set_vs_dirty in this case
      assert(vio.set_vconfig.bits.vl <= vio.set_vconfig.bits.vtype.vlMax)
      reg_vconfig.get := vio.set_vconfig.bits
    }
    when (vio.set_vstart.valid) {
      set_vs_dirty := true
      reg_vstart.get := vio.set_vstart.bits
    }
    vio.vstart := reg_vstart.get
    vio.vconfig := reg_vconfig.get
    vio.vxrm := reg_vxrm.get

    when (reset.toBool) {
      reg_vconfig.get.vl := 0.U
      reg_vconfig.get.vtype := 0.U.asTypeOf(new VType)
      reg_vconfig.get.vtype.vill := true
    }
  }

  reg_satp.asid := 0
  if (!usingVM) {
    reg_satp.mode := 0
    reg_satp.ppn := 0
  }

  if (nBreakpoints <= 1) reg_tselect := 0
  for (bpc <- reg_bp map {_.control}) {
    bpc.ttype := bpc.tType
    bpc.maskmax := bpc.maskMax
    bpc.reserved := 0
    bpc.zero := 0
    bpc.h := false
    if (!usingSupervisor) bpc.s := false
    if (!usingUser) bpc.u := false
    if (!usingSupervisor && !usingUser) bpc.m := true
    when (reset) {
      bpc.action := 0.U
      bpc.dmode := false
      bpc.chain := false
      bpc.r := false
      bpc.w := false
      bpc.x := false
    }
  }
  for (bpx <- reg_bp map {_.textra}) {
    if (coreParams.mcontextWidth == 0) bpx.mselect := false.B
    if (coreParams.scontextWidth == 0) bpx.sselect := false.B
  }
  for (bp <- reg_bp drop nBreakpoints)
    bp := new BP().fromBits(0)
  for (pmp <- reg_pmp) {
    pmp.cfg.res := 0
    when (reset) { pmp.reset() }
  }

  for (((t, insn), i) <- (io.trace zip io.inst).zipWithIndex) {
    t.exception := io.retire >= i && exception
    t.valid := io.retire > i || t.exception
    t.insn := insn
    t.iaddr := io.pc
    t.priv := Cat(reg_debug, reg_mstatus.prv)
    t.cause := cause
    t.interrupt := cause(xLen-1)
    t.tval := io.tval
  }

  // 按照优先级选择中断
  def chooseInterrupt(masksIn: Seq[UInt]): (Bool, UInt) = {
    val nonstandard = supported_interrupts.getWidth-1 to 12 by -1
    // MEI, MSI, MTI, SEI, SSI, STI, UEI, USI, UTI
    val standard = Seq(11, 3, 7, 9, 1, 5, 8, 0, 4)
    val priority = nonstandard ++ standard
    val masks = masksIn.reverse
    val any = masks.flatMap(m => priority.filter(_ < m.getWidth).map(i => m(i))).reduce(_||_)
    val which = PriorityMux(masks.flatMap(m => priority.filter(_ < m.getWidth).map(i => (m(i), i.U))))
    (any, which)
  }

  // 读修改写CSR
  // 0x：wdata
  // 10：rdata
  // 11：0
  def readModifyWriteCSR(cmd: UInt, rdata: UInt, wdata: UInt) = {
    (Mux(cmd(1), rdata, UInt(0)) | wdata) & ~Mux(cmd(1,0).andR, wdata, UInt(0))
  }

  def legalizePrivilege(priv: UInt): UInt =
    if (usingSupervisor) Mux(priv === PRV.H, PRV.U, priv)
    else if (usingUser) Fill(2, priv(0))
    else PRV.M

  def trimPrivilege(priv: UInt): UInt =
    if (usingSupervisor) priv
    else legalizePrivilege(priv)

  def writeCounter(lo: Int, ctr: WideCounter, wdata: UInt) = {
    if (xLen == 32) {
      val hi = lo + CSRs.mcycleh - CSRs.mcycle
      when (decoded_addr(lo)) { ctr := Cat(ctr(ctr.getWidth-1, 32), wdata) }
      when (decoded_addr(hi)) { ctr := Cat(wdata(ctr.getWidth-33, 0), ctr(31, 0)) }
    } else {
      when (decoded_addr(lo)) { ctr := wdata(ctr.getWidth-1, 0) }
    }
  }
  // 生成EPC，根据是否压缩进行对齐
  def formEPC(x: UInt) = ~(~x | (if (usingCompressed) 1.U else 3.U))
  // 读EPC，根据是否压缩进行对齐
  def readEPC(x: UInt) = ~(~x | Mux(reg_misa('c' - 'a'), 1.U, 3.U))
  // 取mtvec寄存器，Mode为1时Base需要6位对齐，且保留Mode
  def formTVec(x: UInt) = x andNot Mux(x(0), ((((BigInt(1) << mtvecInterruptAlign) - 1) << mtvecBaseAlign) | 2).U, 2)
  // 所有字母减A乘2再进行或操作，即由字母生成掩码
  def isaStringToMask(s: String) = s.map(x => 1 << (x - 'A')).foldLeft(0)(_|_)
  def formFS(fs: UInt) = if (coreParams.haveFSDirty) fs else Fill(2, fs.orR)
  def formVS(vs: UInt) = if (usingVector) vs else 0.U
}
