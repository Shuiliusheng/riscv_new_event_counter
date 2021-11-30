#include<stdio.h>
#include<stdlib.h>
#include"pfc_asm.h"

int test()
{
	
	long before[32]={0};
	long after[32]={0};
	int n=0;
	RESET_COUNTER();
	for(n=0;n<32;n++){
		before[n]=read_counter(n);
	}

	//start testing .....
	for(int i=0;i<30;i++){
		int *arr = (int *)malloc(sizeof(int)*1024);
		for(int c=0;c<1024;c++)
			for(int j=0;j<104;j++){
			int c=rand()%5;
			if(c>2)
				arr[(c+j)%1024]=1;
		}
	}
	//end testing .....

	for(n=0;n<32;n++){
		after[n]=read_counter(n);
	}

	for(n=0;n<32;n++){
		printf("event %2d: before testing: %64llu, after testing: %64llu, before-after: %64llu\n", n, before[n], after[n], after[n]-before[n]);
	}
	return 0;
}

int main()
{
	printf("test: event set 0\n");
	set_event_set(0);
	test();


	printf("test: event set 1\n");
	set_event_set(1);
	test();

	printf("test: event set 2\n");
	set_event_set(2);
	test();
	
	return 0;
}
	
