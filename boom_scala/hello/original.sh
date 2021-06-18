#!/bin/bash
cd ..
riscv64-unknown-linux-gnu-gcc -I./../env -I./common -I./hello -DPREALLOCATE=1 -mcmodel=medany -static -std=gnu99 -O2 -ffast-math -fno-common -fno-builtin-printf -fno-tree-loop-distribute-patterns -o hello.riscv ./hello/hello.c ./common/syscalls.c ./common/crt.S -static -nostdlib -nostartfiles -lm -lgcc -T ./common/test.ld

riscv64-unknown-linux-gnu-objdump --disassemble-all --disassemble-zeroes --section=.text --section=.text.startup --section=.text.init --section=.data hello.riscv > hello/hello.riscv.dump

mv hello.riscv ../../nopk_hello.riscv

