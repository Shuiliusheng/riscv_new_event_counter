cmd_arch/riscv/lib/modules.order := {  :; } | awk '!x[$$0]++' - > arch/riscv/lib/modules.order
