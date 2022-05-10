#ifndef   _PFC_DEFINE_  
#define   _PFC_DEFINE_  

//---------------------------------------------------------------------
#define InitEnv(priv) asm volatile( \
    "li t0, 0x1234567  # tag \n\t"  \
    "addi x0, t0, 1 \n\t"      		\
    "li t0, 0  # warminst \n\t"  	\
    "addi x0, t0, 10 \n\t"     		\
	"li t0, " #priv "  # priv: user/super/machine \n\t"  \
    "addi x0, t0, 6 \n\t"      	\
    : : \
); 

#define SetWarmInsts(num) asm volatile( \
    "mv t0, %[r]  # startinst \n\t"  \
    "addi x0, t0, 10 \n\t"      \
    : \
    :[r]"r"(num)  \
); 


#define GetCounter(basereg, dstreg, start, n)    \
              "andi x0, " dstreg ", 512+" #start "+" #n " \n\t" \
              "sd " dstreg ", " #n "*8(" basereg ") \n\t"

#define ReadCounter8(base, start) asm volatile( \
    "mv t1, %[addr]  # set base addr \n\t"  \
    GetCounter("t1", "t0", start, 0)  \
    GetCounter("t1", "t0", start, 1)  \
    GetCounter("t1", "t0", start, 2)  \
    GetCounter("t1", "t0", start, 3)  \
    GetCounter("t1", "t0", start, 4)  \
    GetCounter("t1", "t0", start, 5)  \
    GetCounter("t1", "t0", start, 6)  \
    GetCounter("t1", "t0", start, 7)  \
    : \
    :[addr]"r"(base) \
); 

#define ReadCounter16(base, start) \
    ReadCounter8(base, start) \
    ReadCounter8(base+8, start+8) 


#define RESET_COUNTER asm volatile(" andi x0, t0, 1024 \n\t" );
//---------------------------------------------------------------------

unsigned long long exit_counters[64] = {0};

__attribute((constructor)) void start_record()
{
	printf("start read performance counters\n");
	
	int warminst = 10000;
	InitEnv(0); //只统计user模式的事件
	RESET_COUNTER;
	SetWarmInsts(warminst); //执行warminst后，重置计数器
}

__attribute((destructor)) void exit_record()
{
	ReadCounter16(&exit_counters[0], 0);	//读取0-15计数器的值
	ReadCounter16(&exit_counters[16], 16);	//读取16-31计数器的值
	ReadCounter16(&exit_counters[32], 32);	//读取32-47计数器的值

	for(int n=0;n<48;n++){
		printf("event %2d: exit_counters: %llu\n", n, exit_counters[n]);
    }
}

#endif 