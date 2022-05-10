# riscv_new_event_counter
 
1. 主要原理：
   - 通过将要测试的进程设置一个唯一的标号0x1234567来使得硬件仅统计该标号的进程事件信息
   - 操作系统仅负责保存和恢复该标号，而不关注于保存计数器事件信息
   - 硬件上仅会在当前进程标号为0x1234567的情况下，尝试统计事件
   - 由软件来设置需要统计事件的级别：user/super/machine，硬件来决定事件是否记录计数器
   - warminst: 由硬件统计当前进程在设置完warminst后执行了多少条用户态的指令，当达到warminst指令数后，重置计数器的值

2. 功能介绍：
   - 软件上：
      - 支持读取计数器的值；
      - 支持重置所有计数器的值；
      - 支持设置计数器统计的事件级别：user/(super+user)/(machine+super+user)
      - 支持设置warmup指令数，当到达该指令数时，所有计数器重置为0
   - 硬件上：
      - 所有的代码修改使用PerfCounterSupport进行标注
      - 通过设置parameters中的subECounterNum的值，能够更改硬件计数器的数量：subECounterNum表示共有多少个子计数器模块，每个模块16个计数器
      - 最高支持256个计数器（也可以修改为更多的，比较容易扩展）

3. 相关汇编指令介绍
   - 设置一些控制寄存器: addi x0, rs1, #tag
      - tag = 1 : 设置进程独有标识, processTag = Reg[rs1]
      - tag = 10 : 设置startInsts = Reg[rs1]，用于控制程序执行到该数量的指令时，reset所有计数器
      - tag = 6 : 设置maxpriv寄存器,  maxPriv = Reg[rs1] % 4
         - maxPriv = 0：只统计user
         - maxPriv = 1：只统计user + super
         - maxPriv = 3：统计所有事件
      - tag = 1025 : 读取processTag, Reg[rs1] = processTag，用于进程切换时保存和恢复processTag
   
   - 计数器相关操作
      - reset: andi x0, rs1, 1024
      - 读取计数器: andi x0, rs1, 512-1023  #用于读取第1-n个计数器到rs1寄存器中

4. 文件夹介绍
   - boom_scala: 以SonicBOOM为例，修改的cpu代码
   - linux_arch_riscv: 修改的linux riscv部分的代码，用于恢复和保存processTag
   - riscv_test: 用于提供对verilator生成的boom模拟器的测试，可以直接运行
   - example: 实际使用时的测试样例
      - pfc_define.h: 最为主要的文件，其中定义了设置控制寄存器和计数器操作的宏
         - InitEnv(priv)：初始化一些控制寄存器，同时设置计数器要统计的级别
         - SetWarmInsts(num)： 设置要warmup的指令数，设置之后立马开始计算指令数
         - ReadCounter8(base, start) & ReadCounter16(base, start)
            - 读取从start开始读取8个或者16个计数器的值到以base为基址的数组中
            - start从0开始
         - RESET_COUNTER： 重置所有计数器的值为0
         - start_record() & exit_record() 在main之前和之后分别被调用