
RISCV_GCC=riscv64-unknown-linux-gnu-gcc
build: perfcounter_test.c pfc_define.h
	${RISCV_GCC} perfcounter_test.c -static -o perfrun

run: perfrun
	echo "./perfrun | tee -a perfrun.log"

clean:
	rm perfrun
