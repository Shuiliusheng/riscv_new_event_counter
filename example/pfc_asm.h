#ifndef   _PFC_ASM_  
#define   _PFC_ASM_  

#define RESET_COUNTER_LOW asm volatile( \
    " add x6,x0,x0     \n\t" \
    " sub x0,x6,x0     \n\t" \
    " sub x0,x6,x1     \n\t" \
    " sub x0,x6,x2     \n\t" \
    " sub x0,x6,x3     \n\t" \
    " sub x0,x6,x4     \n\t" \
    " sub x0,x6,x5     \n\t" \
    " sub x0,x6,x6     \n\t" \
    " sub x0,x6,x7 	   \n\t" \
	" add x6,x0,x0     \n\t" \
    " sub x0,x6,x8     \n\t" \
    " sub x0,x6,x9     \n\t" \
    " sub x0,x6,x10     \n\t" \
    " sub x0,x6,x11     \n\t" \
    " sub x0,x6,x12     \n\t" \
    " sub x0,x6,x13     \n\t" \
    " sub x0,x6,x14     \n\t" \
    " sub x0,x6,x15     \n\t" \
    " fence     \n\t" \
);

#define RESET_COUNTER_HIGH asm volatile( \
	" sub x0,x6,x16      \n\t" \
	" sub x0,x6,x17      \n\t" \
	" sub x0,x6,x18      \n\t" \
	" sub x0,x6,x19      \n\t" \
	" sub x0,x6,x20      \n\t" \
	" sub x0,x6,x21      \n\t" \
	" sub x0,x6,x22      \n\t" \
	" sub x0,x6,x23      \n\t" \
	" sub x0,x6,x24      \n\t" \
	" sub x0,x6,x25      \n\t" \
	" sub x0,x6,x26      \n\t" \
	" sub x0,x6,x27      \n\t" \
	" sub x0,x6,x28      \n\t" \
	" sub x0,x6,x29      \n\t" \
	" sub x0,x6,x30      \n\t" \
	" sub x0,x6,x31      \n\t" \
    " fence     \n\t" \
);

unsigned long long read_counter(int n){
	unsigned long long temp=0;
	switch(n){
		//add x0,x6,x0: 微指令，表示将0号计数器中的值读入到x6寄存器中
		case 0: asm volatile( " add x0,x6,x0    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 1: asm volatile( " add x0,x6,x1    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 2: asm volatile( " add x0,x6,x2    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 3: asm volatile( " add x0,x6,x3    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 4: asm volatile( " add x0,x6,x4    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 5: asm volatile( " add x0,x6,x5    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 6: asm volatile( " add x0,x6,x6    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 7: asm volatile( " add x0,x6,x7    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 8: asm volatile( " add x0,x6,x8    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 9: asm volatile( " add x0,x6,x9    \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 10: asm volatile( " add x0,x6,x10     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 11: asm volatile( " add x0,x6,x11     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 12: asm volatile( " add x0,x6,x12     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 13: asm volatile( " add x0,x6,x13     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 14: asm volatile( " add x0,x6,x14     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 15: asm volatile( " add x0,x6,x15     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		
		case 16: asm volatile( " add x0,x6,x16     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 17: asm volatile( " add x0,x6,x17     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 18: asm volatile( " add x0,x6,x18     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 19: asm volatile( " add x0,x6,x19     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 20: asm volatile( " add x0,x6,x20     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 21: asm volatile( " add x0,x6,x21     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 22: asm volatile( " add x0,x6,x22     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 23: asm volatile( " add x0,x6,x23     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 24: asm volatile( " add x0,x6,x24     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 25: asm volatile( " add x0,x6,x25     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 26: asm volatile( " add x0,x6,x26     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 27: asm volatile( " add x0,x6,x27     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 28: asm volatile( " add x0,x6,x28     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 29: asm volatile( " add x0,x6,x29     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 30: asm volatile( " add x0,x6,x30     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		case 31: asm volatile( " add x0,x6,x31     \n\t" " add %[o1],x6,0         \n\t" :[o1]"=r"(temp) :[temp]"r"(temp));; break;
		default: break;
	}
	return temp;
}

void write_counter(unsigned long long temp, int n){
	switch(n){
		//sub x0,x6,x0: 伪指令，使用寄存器x6中的值初始化0号计数器
		case 0: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x0     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 1: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x1     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 2: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x2     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 3: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x3     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 4: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x4     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 5: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x5     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 6: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x6     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 7: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x7     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 8: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x8     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 9: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x9     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 10: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x10     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 11: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x11     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 12: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x12     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 13: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x13     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 14: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x14     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 15: asm volatile( " add x6,%[in1],0        \n\t" " sub x0,x6,x15     \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		
		case 16 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x16    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 17 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x17    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 18 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x18    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 19 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x19    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 20 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x20    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 21 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x21    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 22 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x22    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 23 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x23    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 24 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x24    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 25 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x25    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 26 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x26    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 27 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x27    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 28 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x28    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 29 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x29    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 30 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x30    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		case 31 : asm volatile( " add x6,%[in1],0       \n\t" " sub x0,x6,x31    \n\t" :[t]"=r"(temp) :[in1]"r"(temp) );; break;
		default: break;
	}
}

void set_event_set(int set){
	switch(set){
		//or x0, x0, x0: 伪指令，用于选择将event_set0中的信号连接到perf counter进行统计
		case 0: asm volatile( " or x0,x0,x0    \n\t" " fence         \n\t" ); break;
		case 1: asm volatile( " or x0,x1,x1    \n\t" " fence         \n\t" ); break;
		case 2: asm volatile( " or x0,x2,x2    \n\t" " fence         \n\t" ); break;
		case 3: asm volatile( " or x0,x3,x3    \n\t" " fence         \n\t" ); break;
		case 4: asm volatile( " or x0,x4,x4    \n\t" " fence         \n\t" ); break;
		case 5: asm volatile( " or x0,x5,x5    \n\t" " fence         \n\t" ); break;
		case 6: asm volatile( " or x0,x6,x6    \n\t" " fence         \n\t" ); break;
		case 7: asm volatile( " or x0,x7,x7    \n\t" " fence         \n\t" ); break;
		case 8: asm volatile( " or x0,x8,x8    \n\t" " fence         \n\t" ); break;
		default: break;
	}
}

unsigned long long start_values[32] = {0};

__attribute((constructor)) void start_record(){
	RESET_COUNTER_LOW;
	printf("start read performance counters\n");
	for(int n=0;n<32;n++){
		start_values[n]=read_counter(n);
	}
}

__attribute((destructor)) void exit_record(){
	unsigned long long exit_values[32] = {0};
	int n=0;
	for(n=0;n<32;n++){
		exit_values[n]=read_counter(n);
	}

	for(n=0;n<32;n++){
		printf("event %2d: before: %32llu, after: %32llu, before-after: %32llu\n", n, start_values[n], exit_values[n], exit_values[n]-start_values[n]);
	}
}

#endif 