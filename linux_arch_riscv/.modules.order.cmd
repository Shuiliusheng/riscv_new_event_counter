cmd_arch/riscv/modules.order := {   cat arch/riscv/kernel/modules.order;   cat arch/riscv/mm/modules.order;   cat arch/riscv/net/modules.order; :; } | awk '!x[$$0]++' - > arch/riscv/modules.order
