	.file	"asm-offsets.c"
	.option nopic
# GNU C89 (GCC) version 10.2.0 (riscv32-unknown-linux-gnu)
#	compiled by GNU C version 5.4.0 20160609, GMP version 6.1.0, MPFR version 3.1.4, MPC version 1.0.3, isl version none
# GGC heuristics: --param ggc-min-expand=100 --param ggc-min-heapsize=131072
#Versions of loaded plugins:
 #structleak_plugin: 20190125vanilla
# options passed:  -nostdinc -I ./arch/riscv/include
# -I ./arch/riscv/include/generated -I ./include
# -I ./arch/riscv/include/uapi -I ./arch/riscv/include/generated/uapi
# -I ./include/uapi -I ./include/generated/uapi
# -iplugindir=/home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/lib/gcc/riscv32-unknown-linux-gnu/10.2.0/plugin
# -D __KERNEL__ -D CONFIG_PAGE_OFFSET=0xffffffe000000000
# -D STRUCTLEAK_PLUGIN -D KBUILD_MODFILE="./asm-offsets"
# -D KBUILD_BASENAME="asm_offsets" -D KBUILD_MODNAME="asm_offsets"
# -isystem /home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/lib/gcc/riscv32-unknown-linux-gnu/10.2.0/include
# -include ./include/linux/kconfig.h
# -include ./include/linux/compiler_types.h
# -MMD arch/riscv/kernel/.asm-offsets.s.d arch/riscv/kernel/asm-offsets.c
# -iplugindir=/home/cuihongwei/server22/fpga-rocket/temp/rv32_toolchain/lib/gcc/riscv32-unknown-linux-gnu/10.2.0/plugin
# -mabi=lp64 -march=rv64imac -mno-save-restore -mcmodel=medany
# -mstrict-align -mtune=rocket -march=rv64imac
# -auxbase-strip arch/riscv/kernel/asm-offsets.s -O2 -Wall -Wundef
# -Werror=strict-prototypes -Wno-trigraphs
# -Werror=implicit-function-declaration -Werror=implicit-int
# -Werror=return-type -Wno-format-security -Wno-frame-address
# -Wformat-truncation=0 -Wformat-overflow=0 -Wno-address-of-packed-member
# -Wframe-larger-than=2048 -Wno-unused-but-set-variable
# -Wimplicit-fallthrough=3 -Wunused-const-variable=0
# -Wdeclaration-after-statement -Wvla -Wno-pointer-sign
# -Wno-stringop-truncation -Wno-zero-length-bounds -Wno-array-bounds
# -Wstringop-overflow=0 -Wno-restrict -Wno-maybe-uninitialized
# -Werror=date-time -Werror=incompatible-pointer-types
# -Werror=designated-init -Wno-packed-not-aligned -std=gnu90
# -fmacro-prefix-map=./= -fno-strict-aliasing -fno-common -fshort-wchar
# -fno-PIE -fno-delete-null-pointer-checks -fno-allow-store-data-races
# -fstack-protector-strong -fno-omit-frame-pointer
# -fno-optimize-sibling-calls -fno-strict-overflow -fstack-check=no
# -fconserve-stack -fplugin=./scripts/gcc-plugins/structleak_plugin.so
# -fplugin-arg-structleak_plugin-verbose
# -fplugin-arg-structleak_plugin-byref-all -fverbose-asm
# options enabled:  -faggressive-loop-optimizations -falign-functions
# -falign-jumps -falign-labels -falign-loops -fallocation-dce
# -fauto-inc-dec -fbranch-count-reg -fcaller-saves -fcode-hoisting
# -fcombine-stack-adjustments -fcompare-elim -fcprop-registers
# -fcrossjumping -fcse-follow-jumps -fdefer-pop -fdevirtualize
# -fdevirtualize-speculatively -fdwarf2-cfi-asm -fearly-inlining
# -feliminate-unused-debug-symbols -feliminate-unused-debug-types
# -fexpensive-optimizations -fforward-propagate -ffp-int-builtin-inexact
# -ffunction-cse -fgcse -fgcse-lm -fgnu-unique -fguess-branch-probability
# -fhoist-adjacent-loads -fident -fif-conversion -fif-conversion2
# -findirect-inlining -finline -finline-atomics -finline-functions
# -finline-functions-called-once -finline-small-functions -fipa-bit-cp
# -fipa-cp -fipa-icf -fipa-icf-functions -fipa-icf-variables -fipa-profile
# -fipa-pure-const -fipa-ra -fipa-reference -fipa-reference-addressable
# -fipa-sra -fipa-stack-alignment -fipa-vrp -fira-hoist-pressure
# -fira-share-save-slots -fira-share-spill-slots
# -fisolate-erroneous-paths-dereference -fivopts -fkeep-static-consts
# -fleading-underscore -flifetime-dse -flra-remat -fmath-errno
# -fmerge-constants -fmerge-debug-strings -fmove-loop-invariants
# -foptimize-strlen -fpartial-inlining -fpeephole -fpeephole2 -fplt
# -fprefetch-loop-arrays -free -freg-struct-return -freorder-blocks
# -freorder-functions -frerun-cse-after-loop
# -fsched-critical-path-heuristic -fsched-dep-count-heuristic
# -fsched-group-heuristic -fsched-interblock -fsched-last-insn-heuristic
# -fsched-rank-heuristic -fsched-spec -fsched-spec-insn-heuristic
# -fsched-stalled-insns-dep -fschedule-fusion -fschedule-insns
# -fschedule-insns2 -fsection-anchors -fsemantic-interposition
# -fshow-column -fshrink-wrap -fshrink-wrap-separate -fsigned-zeros
# -fsplit-ivs-in-unroller -fsplit-wide-types -fssa-backprop -fssa-phiopt
# -fstack-protector-strong -fstdarg-opt -fstore-merging
# -fstrict-volatile-bitfields -fsync-libcalls -fthread-jumps
# -ftoplevel-reorder -ftrapping-math -ftree-bit-ccp -ftree-builtin-call-dce
# -ftree-ccp -ftree-ch -ftree-coalesce-vars -ftree-copy-prop -ftree-cselim
# -ftree-dce -ftree-dominator-opts -ftree-dse -ftree-forwprop -ftree-fre
# -ftree-loop-distribute-patterns -ftree-loop-if-convert -ftree-loop-im
# -ftree-loop-ivcanon -ftree-loop-optimize -ftree-parallelize-loops=
# -ftree-phiprop -ftree-pre -ftree-pta -ftree-reassoc -ftree-scev-cprop
# -ftree-sink -ftree-slsr -ftree-sra -ftree-switch-conversion
# -ftree-tail-merge -ftree-ter -ftree-vrp -funit-at-a-time -fverbose-asm
# -fwrapv -fwrapv-pointer -fzero-initialized-in-bss -mdiv -mglibc -mplt
# -mstrict-align

	.text
	.align	1
	.globl	asm_offsets
	.type	asm_offsets, @function
asm_offsets:
	addi	sp,sp,-16	#,,
	sd	s0,8(sp)	#,
	addi	s0,sp,16	#,,
# arch/riscv/kernel/asm-offsets.c:16: 	OFFSET(TASK_THREAD_RA, task_struct, thread.ra);
#APP
# 16 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_RA 2048 offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:17: 	OFFSET(TASK_THREAD_SP, task_struct, thread.sp);
# 17 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_SP 2056 offsetof(struct task_struct, thread.sp)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:18: 	OFFSET(TASK_THREAD_S0, task_struct, thread.s[0]);
# 18 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S0 2064 offsetof(struct task_struct, thread.s[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:19: 	OFFSET(TASK_THREAD_S1, task_struct, thread.s[1]);
# 19 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S1 2072 offsetof(struct task_struct, thread.s[1])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:20: 	OFFSET(TASK_THREAD_S2, task_struct, thread.s[2]);
# 20 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S2 2080 offsetof(struct task_struct, thread.s[2])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:21: 	OFFSET(TASK_THREAD_S3, task_struct, thread.s[3]);
# 21 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S3 2088 offsetof(struct task_struct, thread.s[3])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:22: 	OFFSET(TASK_THREAD_S4, task_struct, thread.s[4]);
# 22 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S4 2096 offsetof(struct task_struct, thread.s[4])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:23: 	OFFSET(TASK_THREAD_S5, task_struct, thread.s[5]);
# 23 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S5 2104 offsetof(struct task_struct, thread.s[5])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:24: 	OFFSET(TASK_THREAD_S6, task_struct, thread.s[6]);
# 24 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S6 2112 offsetof(struct task_struct, thread.s[6])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:25: 	OFFSET(TASK_THREAD_S7, task_struct, thread.s[7]);
# 25 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S7 2120 offsetof(struct task_struct, thread.s[7])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:26: 	OFFSET(TASK_THREAD_S8, task_struct, thread.s[8]);
# 26 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S8 2128 offsetof(struct task_struct, thread.s[8])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:27: 	OFFSET(TASK_THREAD_S9, task_struct, thread.s[9]);
# 27 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S9 2136 offsetof(struct task_struct, thread.s[9])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:28: 	OFFSET(TASK_THREAD_S10, task_struct, thread.s[10]);
# 28 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S10 2144 offsetof(struct task_struct, thread.s[10])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:29: 	OFFSET(TASK_THREAD_S11, task_struct, thread.s[11]);
# 29 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S11 2152 offsetof(struct task_struct, thread.s[11])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:30: 	OFFSET(TASK_TI_FLAGS, task_struct, thread_info.flags);
# 30 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_TI_FLAGS 0 offsetof(struct task_struct, thread_info.flags)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:31: 	OFFSET(TASK_TI_PREEMPT_COUNT, task_struct, thread_info.preempt_count);
# 31 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_TI_PREEMPT_COUNT 8 offsetof(struct task_struct, thread_info.preempt_count)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:32: 	OFFSET(TASK_TI_KERNEL_SP, task_struct, thread_info.kernel_sp);
# 32 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_TI_KERNEL_SP 16 offsetof(struct task_struct, thread_info.kernel_sp)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:33: 	OFFSET(TASK_TI_USER_SP, task_struct, thread_info.user_sp);
# 33 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_TI_USER_SP 24 offsetof(struct task_struct, thread_info.user_sp)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:34: 	OFFSET(TASK_TI_CPU, task_struct, thread_info.cpu);
# 34 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_TI_CPU 32 offsetof(struct task_struct, thread_info.cpu)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:36: 	OFFSET(TASK_THREAD_F0,  task_struct, thread.fstate.f[0]);
# 36 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F0 2160 offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:37: 	OFFSET(TASK_THREAD_F1,  task_struct, thread.fstate.f[1]);
# 37 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F1 2168 offsetof(struct task_struct, thread.fstate.f[1])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:38: 	OFFSET(TASK_THREAD_F2,  task_struct, thread.fstate.f[2]);
# 38 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F2 2176 offsetof(struct task_struct, thread.fstate.f[2])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:39: 	OFFSET(TASK_THREAD_F3,  task_struct, thread.fstate.f[3]);
# 39 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F3 2184 offsetof(struct task_struct, thread.fstate.f[3])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:40: 	OFFSET(TASK_THREAD_F4,  task_struct, thread.fstate.f[4]);
# 40 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F4 2192 offsetof(struct task_struct, thread.fstate.f[4])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:41: 	OFFSET(TASK_THREAD_F5,  task_struct, thread.fstate.f[5]);
# 41 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F5 2200 offsetof(struct task_struct, thread.fstate.f[5])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:42: 	OFFSET(TASK_THREAD_F6,  task_struct, thread.fstate.f[6]);
# 42 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F6 2208 offsetof(struct task_struct, thread.fstate.f[6])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:43: 	OFFSET(TASK_THREAD_F7,  task_struct, thread.fstate.f[7]);
# 43 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F7 2216 offsetof(struct task_struct, thread.fstate.f[7])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:44: 	OFFSET(TASK_THREAD_F8,  task_struct, thread.fstate.f[8]);
# 44 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F8 2224 offsetof(struct task_struct, thread.fstate.f[8])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:45: 	OFFSET(TASK_THREAD_F9,  task_struct, thread.fstate.f[9]);
# 45 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F9 2232 offsetof(struct task_struct, thread.fstate.f[9])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:46: 	OFFSET(TASK_THREAD_F10, task_struct, thread.fstate.f[10]);
# 46 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F10 2240 offsetof(struct task_struct, thread.fstate.f[10])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:47: 	OFFSET(TASK_THREAD_F11, task_struct, thread.fstate.f[11]);
# 47 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F11 2248 offsetof(struct task_struct, thread.fstate.f[11])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:48: 	OFFSET(TASK_THREAD_F12, task_struct, thread.fstate.f[12]);
# 48 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F12 2256 offsetof(struct task_struct, thread.fstate.f[12])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:49: 	OFFSET(TASK_THREAD_F13, task_struct, thread.fstate.f[13]);
# 49 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F13 2264 offsetof(struct task_struct, thread.fstate.f[13])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:50: 	OFFSET(TASK_THREAD_F14, task_struct, thread.fstate.f[14]);
# 50 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F14 2272 offsetof(struct task_struct, thread.fstate.f[14])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:51: 	OFFSET(TASK_THREAD_F15, task_struct, thread.fstate.f[15]);
# 51 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F15 2280 offsetof(struct task_struct, thread.fstate.f[15])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:52: 	OFFSET(TASK_THREAD_F16, task_struct, thread.fstate.f[16]);
# 52 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F16 2288 offsetof(struct task_struct, thread.fstate.f[16])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:53: 	OFFSET(TASK_THREAD_F17, task_struct, thread.fstate.f[17]);
# 53 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F17 2296 offsetof(struct task_struct, thread.fstate.f[17])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:54: 	OFFSET(TASK_THREAD_F18, task_struct, thread.fstate.f[18]);
# 54 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F18 2304 offsetof(struct task_struct, thread.fstate.f[18])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:55: 	OFFSET(TASK_THREAD_F19, task_struct, thread.fstate.f[19]);
# 55 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F19 2312 offsetof(struct task_struct, thread.fstate.f[19])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:56: 	OFFSET(TASK_THREAD_F20, task_struct, thread.fstate.f[20]);
# 56 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F20 2320 offsetof(struct task_struct, thread.fstate.f[20])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:57: 	OFFSET(TASK_THREAD_F21, task_struct, thread.fstate.f[21]);
# 57 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F21 2328 offsetof(struct task_struct, thread.fstate.f[21])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:58: 	OFFSET(TASK_THREAD_F22, task_struct, thread.fstate.f[22]);
# 58 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F22 2336 offsetof(struct task_struct, thread.fstate.f[22])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:59: 	OFFSET(TASK_THREAD_F23, task_struct, thread.fstate.f[23]);
# 59 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F23 2344 offsetof(struct task_struct, thread.fstate.f[23])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:60: 	OFFSET(TASK_THREAD_F24, task_struct, thread.fstate.f[24]);
# 60 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F24 2352 offsetof(struct task_struct, thread.fstate.f[24])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:61: 	OFFSET(TASK_THREAD_F25, task_struct, thread.fstate.f[25]);
# 61 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F25 2360 offsetof(struct task_struct, thread.fstate.f[25])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:62: 	OFFSET(TASK_THREAD_F26, task_struct, thread.fstate.f[26]);
# 62 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F26 2368 offsetof(struct task_struct, thread.fstate.f[26])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:63: 	OFFSET(TASK_THREAD_F27, task_struct, thread.fstate.f[27]);
# 63 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F27 2376 offsetof(struct task_struct, thread.fstate.f[27])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:64: 	OFFSET(TASK_THREAD_F28, task_struct, thread.fstate.f[28]);
# 64 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F28 2384 offsetof(struct task_struct, thread.fstate.f[28])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:65: 	OFFSET(TASK_THREAD_F29, task_struct, thread.fstate.f[29]);
# 65 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F29 2392 offsetof(struct task_struct, thread.fstate.f[29])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:66: 	OFFSET(TASK_THREAD_F30, task_struct, thread.fstate.f[30]);
# 66 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F30 2400 offsetof(struct task_struct, thread.fstate.f[30])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:67: 	OFFSET(TASK_THREAD_F31, task_struct, thread.fstate.f[31]);
# 67 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F31 2408 offsetof(struct task_struct, thread.fstate.f[31])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:68: 	OFFSET(TASK_THREAD_FCSR, task_struct, thread.fstate.fcsr);
# 68 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_FCSR 2416 offsetof(struct task_struct, thread.fstate.fcsr)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:70: 	DEFINE(PT_SIZE, sizeof(struct pt_regs));
# 70 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_SIZE 288 sizeof(struct pt_regs)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:71: 	OFFSET(PT_EPC, pt_regs, epc);
# 71 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_EPC 0 offsetof(struct pt_regs, epc)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:72: 	OFFSET(PT_RA, pt_regs, ra);
# 72 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_RA 8 offsetof(struct pt_regs, ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:73: 	OFFSET(PT_FP, pt_regs, s0);
# 73 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_FP 64 offsetof(struct pt_regs, s0)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:74: 	OFFSET(PT_S0, pt_regs, s0);
# 74 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S0 64 offsetof(struct pt_regs, s0)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:75: 	OFFSET(PT_S1, pt_regs, s1);
# 75 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S1 72 offsetof(struct pt_regs, s1)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:76: 	OFFSET(PT_S2, pt_regs, s2);
# 76 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S2 144 offsetof(struct pt_regs, s2)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:77: 	OFFSET(PT_S3, pt_regs, s3);
# 77 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S3 152 offsetof(struct pt_regs, s3)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:78: 	OFFSET(PT_S4, pt_regs, s4);
# 78 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S4 160 offsetof(struct pt_regs, s4)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:79: 	OFFSET(PT_S5, pt_regs, s5);
# 79 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S5 168 offsetof(struct pt_regs, s5)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:80: 	OFFSET(PT_S6, pt_regs, s6);
# 80 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S6 176 offsetof(struct pt_regs, s6)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:81: 	OFFSET(PT_S7, pt_regs, s7);
# 81 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S7 184 offsetof(struct pt_regs, s7)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:82: 	OFFSET(PT_S8, pt_regs, s8);
# 82 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S8 192 offsetof(struct pt_regs, s8)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:83: 	OFFSET(PT_S9, pt_regs, s9);
# 83 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S9 200 offsetof(struct pt_regs, s9)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:84: 	OFFSET(PT_S10, pt_regs, s10);
# 84 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S10 208 offsetof(struct pt_regs, s10)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:85: 	OFFSET(PT_S11, pt_regs, s11);
# 85 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_S11 216 offsetof(struct pt_regs, s11)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:86: 	OFFSET(PT_SP, pt_regs, sp);
# 86 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_SP 16 offsetof(struct pt_regs, sp)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:87: 	OFFSET(PT_TP, pt_regs, tp);
# 87 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_TP 32 offsetof(struct pt_regs, tp)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:88: 	OFFSET(PT_A0, pt_regs, a0);
# 88 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_A0 80 offsetof(struct pt_regs, a0)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:89: 	OFFSET(PT_A1, pt_regs, a1);
# 89 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_A1 88 offsetof(struct pt_regs, a1)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:90: 	OFFSET(PT_A2, pt_regs, a2);
# 90 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_A2 96 offsetof(struct pt_regs, a2)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:91: 	OFFSET(PT_A3, pt_regs, a3);
# 91 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_A3 104 offsetof(struct pt_regs, a3)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:92: 	OFFSET(PT_A4, pt_regs, a4);
# 92 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_A4 112 offsetof(struct pt_regs, a4)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:93: 	OFFSET(PT_A5, pt_regs, a5);
# 93 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_A5 120 offsetof(struct pt_regs, a5)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:94: 	OFFSET(PT_A6, pt_regs, a6);
# 94 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_A6 128 offsetof(struct pt_regs, a6)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:95: 	OFFSET(PT_A7, pt_regs, a7);
# 95 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_A7 136 offsetof(struct pt_regs, a7)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:96: 	OFFSET(PT_T0, pt_regs, t0);
# 96 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_T0 40 offsetof(struct pt_regs, t0)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:97: 	OFFSET(PT_T1, pt_regs, t1);
# 97 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_T1 48 offsetof(struct pt_regs, t1)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:98: 	OFFSET(PT_T2, pt_regs, t2);
# 98 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_T2 56 offsetof(struct pt_regs, t2)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:99: 	OFFSET(PT_T3, pt_regs, t3);
# 99 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_T3 224 offsetof(struct pt_regs, t3)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:100: 	OFFSET(PT_T4, pt_regs, t4);
# 100 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_T4 232 offsetof(struct pt_regs, t4)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:101: 	OFFSET(PT_T5, pt_regs, t5);
# 101 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_T5 240 offsetof(struct pt_regs, t5)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:102: 	OFFSET(PT_T6, pt_regs, t6);
# 102 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_T6 248 offsetof(struct pt_regs, t6)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:103: 	OFFSET(PT_GP, pt_regs, gp);
# 103 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_GP 24 offsetof(struct pt_regs, gp)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:104: 	OFFSET(PT_ORIG_A0, pt_regs, orig_a0);
# 104 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_ORIG_A0 280 offsetof(struct pt_regs, orig_a0)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:105: 	OFFSET(PT_STATUS, pt_regs, status);
# 105 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_STATUS 256 offsetof(struct pt_regs, status)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:106: 	OFFSET(PT_BADADDR, pt_regs, badaddr);
# 106 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_BADADDR 264 offsetof(struct pt_regs, badaddr)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:107: 	OFFSET(PT_CAUSE, pt_regs, cause);
# 107 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_CAUSE 272 offsetof(struct pt_regs, cause)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:114: 	DEFINE(TASK_THREAD_RA_RA,
# 114 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_RA_RA 0 offsetof(struct task_struct, thread.ra) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:118: 	DEFINE(TASK_THREAD_SP_RA,
# 118 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_SP_RA 8 offsetof(struct task_struct, thread.sp) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:122: 	DEFINE(TASK_THREAD_S0_RA,
# 122 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S0_RA 16 offsetof(struct task_struct, thread.s[0]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:126: 	DEFINE(TASK_THREAD_S1_RA,
# 126 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S1_RA 24 offsetof(struct task_struct, thread.s[1]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:130: 	DEFINE(TASK_THREAD_S2_RA,
# 130 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S2_RA 32 offsetof(struct task_struct, thread.s[2]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:134: 	DEFINE(TASK_THREAD_S3_RA,
# 134 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S3_RA 40 offsetof(struct task_struct, thread.s[3]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:138: 	DEFINE(TASK_THREAD_S4_RA,
# 138 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S4_RA 48 offsetof(struct task_struct, thread.s[4]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:142: 	DEFINE(TASK_THREAD_S5_RA,
# 142 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S5_RA 56 offsetof(struct task_struct, thread.s[5]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:146: 	DEFINE(TASK_THREAD_S6_RA,
# 146 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S6_RA 64 offsetof(struct task_struct, thread.s[6]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:150: 	DEFINE(TASK_THREAD_S7_RA,
# 150 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S7_RA 72 offsetof(struct task_struct, thread.s[7]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:154: 	DEFINE(TASK_THREAD_S8_RA,
# 154 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S8_RA 80 offsetof(struct task_struct, thread.s[8]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:158: 	DEFINE(TASK_THREAD_S9_RA,
# 158 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S9_RA 88 offsetof(struct task_struct, thread.s[9]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:162: 	DEFINE(TASK_THREAD_S10_RA,
# 162 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S10_RA 96 offsetof(struct task_struct, thread.s[10]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:166: 	DEFINE(TASK_THREAD_S11_RA,
# 166 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_S11_RA 104 offsetof(struct task_struct, thread.s[11]) - offsetof(struct task_struct, thread.ra)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:171: 	DEFINE(TASK_THREAD_F0_F0,
# 171 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F0_F0 0 offsetof(struct task_struct, thread.fstate.f[0]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:175: 	DEFINE(TASK_THREAD_F1_F0,
# 175 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F1_F0 8 offsetof(struct task_struct, thread.fstate.f[1]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:179: 	DEFINE(TASK_THREAD_F2_F0,
# 179 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F2_F0 16 offsetof(struct task_struct, thread.fstate.f[2]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:183: 	DEFINE(TASK_THREAD_F3_F0,
# 183 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F3_F0 24 offsetof(struct task_struct, thread.fstate.f[3]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:187: 	DEFINE(TASK_THREAD_F4_F0,
# 187 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F4_F0 32 offsetof(struct task_struct, thread.fstate.f[4]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:191: 	DEFINE(TASK_THREAD_F5_F0,
# 191 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F5_F0 40 offsetof(struct task_struct, thread.fstate.f[5]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:195: 	DEFINE(TASK_THREAD_F6_F0,
# 195 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F6_F0 48 offsetof(struct task_struct, thread.fstate.f[6]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:199: 	DEFINE(TASK_THREAD_F7_F0,
# 199 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F7_F0 56 offsetof(struct task_struct, thread.fstate.f[7]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:203: 	DEFINE(TASK_THREAD_F8_F0,
# 203 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F8_F0 64 offsetof(struct task_struct, thread.fstate.f[8]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:207: 	DEFINE(TASK_THREAD_F9_F0,
# 207 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F9_F0 72 offsetof(struct task_struct, thread.fstate.f[9]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:211: 	DEFINE(TASK_THREAD_F10_F0,
# 211 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F10_F0 80 offsetof(struct task_struct, thread.fstate.f[10]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:215: 	DEFINE(TASK_THREAD_F11_F0,
# 215 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F11_F0 88 offsetof(struct task_struct, thread.fstate.f[11]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:219: 	DEFINE(TASK_THREAD_F12_F0,
# 219 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F12_F0 96 offsetof(struct task_struct, thread.fstate.f[12]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:223: 	DEFINE(TASK_THREAD_F13_F0,
# 223 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F13_F0 104 offsetof(struct task_struct, thread.fstate.f[13]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:227: 	DEFINE(TASK_THREAD_F14_F0,
# 227 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F14_F0 112 offsetof(struct task_struct, thread.fstate.f[14]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:231: 	DEFINE(TASK_THREAD_F15_F0,
# 231 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F15_F0 120 offsetof(struct task_struct, thread.fstate.f[15]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:235: 	DEFINE(TASK_THREAD_F16_F0,
# 235 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F16_F0 128 offsetof(struct task_struct, thread.fstate.f[16]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:239: 	DEFINE(TASK_THREAD_F17_F0,
# 239 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F17_F0 136 offsetof(struct task_struct, thread.fstate.f[17]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:243: 	DEFINE(TASK_THREAD_F18_F0,
# 243 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F18_F0 144 offsetof(struct task_struct, thread.fstate.f[18]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:247: 	DEFINE(TASK_THREAD_F19_F0,
# 247 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F19_F0 152 offsetof(struct task_struct, thread.fstate.f[19]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:251: 	DEFINE(TASK_THREAD_F20_F0,
# 251 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F20_F0 160 offsetof(struct task_struct, thread.fstate.f[20]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:255: 	DEFINE(TASK_THREAD_F21_F0,
# 255 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F21_F0 168 offsetof(struct task_struct, thread.fstate.f[21]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:259: 	DEFINE(TASK_THREAD_F22_F0,
# 259 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F22_F0 176 offsetof(struct task_struct, thread.fstate.f[22]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:263: 	DEFINE(TASK_THREAD_F23_F0,
# 263 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F23_F0 184 offsetof(struct task_struct, thread.fstate.f[23]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:267: 	DEFINE(TASK_THREAD_F24_F0,
# 267 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F24_F0 192 offsetof(struct task_struct, thread.fstate.f[24]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:271: 	DEFINE(TASK_THREAD_F25_F0,
# 271 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F25_F0 200 offsetof(struct task_struct, thread.fstate.f[25]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:275: 	DEFINE(TASK_THREAD_F26_F0,
# 275 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F26_F0 208 offsetof(struct task_struct, thread.fstate.f[26]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:279: 	DEFINE(TASK_THREAD_F27_F0,
# 279 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F27_F0 216 offsetof(struct task_struct, thread.fstate.f[27]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:283: 	DEFINE(TASK_THREAD_F28_F0,
# 283 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F28_F0 224 offsetof(struct task_struct, thread.fstate.f[28]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:287: 	DEFINE(TASK_THREAD_F29_F0,
# 287 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F29_F0 232 offsetof(struct task_struct, thread.fstate.f[29]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:291: 	DEFINE(TASK_THREAD_F30_F0,
# 291 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F30_F0 240 offsetof(struct task_struct, thread.fstate.f[30]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:295: 	DEFINE(TASK_THREAD_F31_F0,
# 295 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_F31_F0 248 offsetof(struct task_struct, thread.fstate.f[31]) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:299: 	DEFINE(TASK_THREAD_FCSR_F0,
# 299 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->TASK_THREAD_FCSR_F0 256 offsetof(struct task_struct, thread.fstate.fcsr) - offsetof(struct task_struct, thread.fstate.f[0])"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:308: 	DEFINE(PT_SIZE_ON_STACK, ALIGN(sizeof(struct pt_regs), STACK_ALIGN));
# 308 "arch/riscv/kernel/asm-offsets.c" 1
	
.ascii "->PT_SIZE_ON_STACK 288 ALIGN(sizeof(struct pt_regs), STACK_ALIGN)"	#
# 0 "" 2
# arch/riscv/kernel/asm-offsets.c:309: }
#NO_APP
	ld	s0,8(sp)		#,
	addi	sp,sp,16	#,,
	jr	ra		#
	.size	asm_offsets, .-asm_offsets
	.ident	"GCC: (GNU) 10.2.0"
	.section	.note.GNU-stack,"",@progbits
