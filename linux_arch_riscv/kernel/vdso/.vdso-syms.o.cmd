cmd_arch/riscv/kernel/vdso/vdso-syms.o := /home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/bin/riscv32-unknown-linux-gnu-gcc -Wp,-MMD,arch/riscv/kernel/vdso/.vdso-syms.o.d  -nostdinc -isystem /home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/lib/gcc/riscv32-unknown-linux-gnu/10.2.0/include -I./arch/riscv/include -I./arch/riscv/include/generated  -I./include -I./arch/riscv/include/uapi -I./arch/riscv/include/generated/uapi -I./include/uapi -I./include/generated/uapi -include ./include/linux/kconfig.h -D__KERNEL__ -fmacro-prefix-map=./= -D__ASSEMBLY__ -fno-PIE -mabi=lp64 -march=rv64imafdc -Wa,-gdwarf-2    -c -o arch/riscv/kernel/vdso/vdso-syms.o arch/riscv/kernel/vdso/vdso-syms.S

source_arch/riscv/kernel/vdso/vdso-syms.o := arch/riscv/kernel/vdso/vdso-syms.S

deps_arch/riscv/kernel/vdso/vdso-syms.o := \
  include/linux/kconfig.h \
    $(wildcard include/config/cc/version/text.h) \
    $(wildcard include/config/cpu/big/endian.h) \
    $(wildcard include/config/booger.h) \
    $(wildcard include/config/foo.h) \

arch/riscv/kernel/vdso/vdso-syms.o: $(deps_arch/riscv/kernel/vdso/vdso-syms.o)

$(deps_arch/riscv/kernel/vdso/vdso-syms.o):