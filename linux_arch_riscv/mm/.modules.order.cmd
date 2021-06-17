cmd_arch/riscv/mm/modules.order := {  :; } | awk '!x[$$0]++' - > arch/riscv/mm/modules.order
