cmd_arch/riscv/kernel/fpu.o := /home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/bin/riscv32-unknown-linux-gnu-gcc -Wp,-MMD,arch/riscv/kernel/.fpu.o.d  -nostdinc -isystem /home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/lib/gcc/riscv32-unknown-linux-gnu/10.2.0/include -I./arch/riscv/include -I./arch/riscv/include/generated  -I./include -I./arch/riscv/include/uapi -I./arch/riscv/include/generated/uapi -I./include/uapi -I./include/generated/uapi -include ./include/linux/kconfig.h -D__KERNEL__ -fmacro-prefix-map=./= -D__ASSEMBLY__ -fno-PIE -mabi=lp64 -march=rv64imafdc -Wa,-gdwarf-2    -c -o arch/riscv/kernel/fpu.o arch/riscv/kernel/fpu.S

source_arch/riscv/kernel/fpu.o := arch/riscv/kernel/fpu.S

deps_arch/riscv/kernel/fpu.o := \
  include/linux/kconfig.h \
    $(wildcard include/config/cc/version/text.h) \
    $(wildcard include/config/cpu/big/endian.h) \
    $(wildcard include/config/booger.h) \
    $(wildcard include/config/foo.h) \
  include/linux/linkage.h \
    $(wildcard include/config/arch/use/sym/annotations.h) \
  include/linux/compiler_types.h \
    $(wildcard include/config/have/arch/compiler/h.h) \
    $(wildcard include/config/enable/must/check.h) \
    $(wildcard include/config/cc/has/asm/inline.h) \
  include/linux/stringify.h \
  include/linux/export.h \
    $(wildcard include/config/modversions.h) \
    $(wildcard include/config/module/rel/crcs.h) \
    $(wildcard include/config/have/arch/prel32/relocations.h) \
    $(wildcard include/config/modules.h) \
    $(wildcard include/config/trim/unused/ksyms.h) \
    $(wildcard include/config/unused/symbols.h) \
  arch/riscv/include/asm/linkage.h \
  arch/riscv/include/asm/asm.h \
  arch/riscv/include/asm/csr.h \
    $(wildcard include/config/64bit.h) \
    $(wildcard include/config/riscv/m/mode.h) \
  include/linux/const.h \
  include/vdso/const.h \
  include/uapi/linux/const.h \
  arch/riscv/include/asm/asm-offsets.h \
  include/generated/asm-offsets.h \

arch/riscv/kernel/fpu.o: $(deps_arch/riscv/kernel/fpu.o)

$(deps_arch/riscv/kernel/fpu.o):
