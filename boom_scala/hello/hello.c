#include<stdio.h>
#include"asm.h"

int main()
{
    unsigned int x=3,y=7,z=0;
    printf("hello world: %d %d %d\n", x, y, z);
    ASM(x,y,z);
    printf("hello world: %d %d %d\n", x, y, z);
	return 0;
}

