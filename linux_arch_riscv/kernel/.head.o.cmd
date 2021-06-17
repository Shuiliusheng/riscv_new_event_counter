cmd_arch/riscv/kernel/head.o := /home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/bin/riscv32-unknown-linux-gnu-gcc -Wp,-MMD,arch/riscv/kernel/.head.o.d  -nostdinc -isystem /home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/lib/gcc/riscv32-unknown-linux-gnu/10.2.0/include -I./arch/riscv/include -I./arch/riscv/include/generated  -I./include -I./arch/riscv/include/uapi -I./arch/riscv/include/generated/uapi -I./include/uapi -I./include/generated/uapi -include ./include/linux/kconfig.h -D__KERNEL__ -fmacro-prefix-map=./= -D__ASSEMBLY__ -fno-PIE -mabi=lp64 -march=rv64imafdc -Wa,-gdwarf-2    -c -o arch/riscv/kernel/head.o arch/riscv/kernel/head.S

source_arch/riscv/kernel/head.o := arch/riscv/kernel/head.S

deps_arch/riscv/kernel/head.o := \
    $(wildcard include/config/efi.h) \
    $(wildcard include/config/riscv/m/mode.h) \
    $(wildcard include/config/mmu.h) \
    $(wildcard include/config/smp.h) \
    $(wildcard include/config/nr/cpus.h) \
    $(wildcard include/config/kasan.h) \
    $(wildcard include/config/fpu.h) \
  include/linux/kconfig.h \
    $(wildcard include/config/cc/version/text.h) \
    $(wildcard include/config/cpu/big/endian.h) \
    $(wildcard include/config/booger.h) \
    $(wildcard include/config/foo.h) \
  arch/riscv/include/asm/asm-offsets.h \
  include/generated/asm-offsets.h \
  arch/riscv/include/asm/asm.h \
  include/linux/init.h \
    $(wildcard include/config/have/arch/prel32/relocations.h) \
    $(wildcard include/config/strict/kernel/rwx.h) \
    $(wildcard include/config/strict/module/rwx.h) \
  include/linux/compiler.h \
    $(wildcard include/config/trace/branch/profiling.h) \
    $(wildcard include/config/profile/all/branches.h) \
    $(wildcard include/config/stack/validation.h) \
  include/linux/compiler_types.h \
    $(wildcard include/config/have/arch/compiler/h.h) \
    $(wildcard include/config/enable/must/check.h) \
    $(wildcard include/config/cc/has/asm/inline.h) \
  arch/riscv/include/generated/asm/rwonce.h \
  include/asm-generic/rwonce.h \
  include/linux/types.h \
    $(wildcard include/config/have/uid16.h) \
    $(wildcard include/config/uid16.h) \
    $(wildcard include/config/arch/dma/addr/t/64bit.h) \
    $(wildcard include/config/phys/addr/t/64bit.h) \
    $(wildcard include/config/64bit.h) \
  include/uapi/linux/types.h \
  arch/riscv/include/generated/uapi/asm/types.h \
  include/uapi/asm-generic/types.h \
  include/asm-generic/int-ll64.h \
  include/uapi/asm-generic/int-ll64.h \
  arch/riscv/include/uapi/asm/bitsperlong.h \
  include/asm-generic/bitsperlong.h \
  include/uapi/asm-generic/bitsperlong.h \
  include/linux/linkage.h \
    $(wildcard include/config/arch/use/sym/annotations.h) \
  include/linux/stringify.h \
  include/linux/export.h \
    $(wildcard include/config/modversions.h) \
    $(wildcard include/config/module/rel/crcs.h) \
    $(wildcard include/config/modules.h) \
    $(wildcard include/config/trim/unused/ksyms.h) \
    $(wildcard include/config/unused/symbols.h) \
  arch/riscv/include/asm/linkage.h \
  arch/riscv/include/asm/thread_info.h \
  arch/riscv/include/asm/page.h \
    $(wildcard include/config/page/offset.h) \
    $(wildcard include/config/debug/virtual.h) \
    $(wildcard include/config/flatmem.h) \
  include/linux/pfn.h \
  include/linux/const.h \
  include/vdso/const.h \
  include/uapi/linux/const.h \
  include/asm-generic/memory_model.h \
    $(wildcard include/config/discontigmem.h) \
    $(wildcard include/config/sparsemem/vmemmap.h) \
    $(wildcard include/config/sparsemem.h) \
  include/asm-generic/getorder.h \
  arch/riscv/include/asm/csr.h \
  arch/riscv/include/asm/hwcap.h \
  include/linux/bits.h \
  include/vdso/bits.h \
  arch/riscv/include/uapi/asm/hwcap.h \
  arch/riscv/include/asm/image.h \
  arch/riscv/kernel/efi-header.S \
    $(wildcard include/config/32bit.h) \
  include/linux/pe.h \
  include/linux/sizes.h \

arch/riscv/kernel/head.o: $(deps_arch/riscv/kernel/head.o)

$(deps_arch/riscv/kernel/head.o):
