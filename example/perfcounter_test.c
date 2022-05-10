#include<stdio.h>
#include<stdlib.h>
#include"pfc_define.h"

int test()
{
	int arr[10240];
	for(int i=0;i<100;i++){
		for(int c=0;c<200;c++){
			arr[(i*c)%10240]=i+c;	
		}
	}
	//end testing .....
	return 0;
}

int main()
{
	test();
	return 0;
}
	
