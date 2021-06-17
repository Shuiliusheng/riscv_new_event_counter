cmd_arch/riscv/kernel/vdso/modules.order := {  :; } | awk '!x[$$0]++' - > arch/riscv/kernel/vdso/modules.order
