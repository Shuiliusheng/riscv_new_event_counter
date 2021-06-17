cmd_arch/riscv/net/modules.order := {  :; } | awk '!x[$$0]++' - > arch/riscv/net/modules.order
