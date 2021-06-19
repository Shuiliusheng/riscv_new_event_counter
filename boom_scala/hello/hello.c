#include<stdio.h>
#include"asm.h"

int main()
{
    unsigned int x=3,y=7,z=0,a=0,b=0,c=0,d=0;
    printf("hello world: %6d %6d %6d %6d %6d %6d %6d\n", x, y, z, a, b, c, d);
    //SET_COUNTER(x,y,z,a,b,c,d);
    ASM(x,y,z,a,b,c,d);
    printf("hello world: %6d %6d %6d %6d %6d %6d %6d\n", x, y, z, a, b, c, d);
    int temp[100];
    for(int i=0;i<100;i++){
        temp[i]=i/10;
    }
    ASM(x,y,z,a,b,c,d);
    printf("hello world: %6d %6d %6d %6d %6d %6d %6d\n", x, y, z, a, b, c, d);
	return 0;
}

