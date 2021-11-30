
RISCV_GCC=riscv64-unknown-linux-gcc
build: perfcounter_test.c pfc_asm.h
	${RISCV_GCC} perfcounter_test.c -static -o perfrun

run: perfrun
	echo "./perfrun | tee -a perfrun.log"

clean:
	rm perfrun