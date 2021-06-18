//produce the assembly of riscv for insert unicore code
//input: x -> x6, y -> x7
//output: x7 -> z
//switch to unicore: add x0,x0,x1 
//switch back riscv: add x0,x0,x2 
//start insert flag: add x0,x0,x1 
//end   insert flag: add x0,x0,x2  " fence     \n\t" \
//placefold: add x0,x0,x0 
#define ASM(x,y,z) asm volatile( \
    " add x6,%[x],0        \n\t" \
    " add x7,%[y],0        \n\t" \
    " sub x0,x6,x2     \n\t" \
    " sub x0,x7,x3     \n\t" \
    " add x0,x6,x0     \n\t" \
    " add %[o1],x6,0         \n\t" \
    " add x0,x7,x2     \n\t" \
    " add %[o2],x7,0         \n\t" \
    " add x0,x7,x3     \n\t" \
	" add %[z],x7,0         \n\t" \
	:[z]"=r"(z),[o1]"=r"(y),[o2]"=r"(x)           \
	:[x]"r"(x),[y]"r"(y) \
);       


#define ReadCounter(x,y,z) asm volatile( \
    " add x7,x7,x0        \n\t" \
    " add %[x],x7,x0         \n\t" \
    :[x]"=r"(x)   \
);


// #define ReadCounter(x,y,z) asm volatile( \
//     " add x0,x7,x0        \n\t" \
//     " add x0,x6,x1        \n\t" \
//     " add x0,x5,x2        \n\t" \
//     " add %[x],x7,x0         \n\t" \
//     " add %[y],x6,x0         \n\t" \
//     " add %[z],x5,x0         \n\t" \
//     :[z]"=r"(z),[y]"=r"(y),[x]"=r"(x)   \
// );


#define WriteCounter(x,y,z) asm volatile( \
    " add x5,%[x],x0         \n\t" \
    " add x6,%[y],x0         \n\t" \
    " add x7,%[z],x0         \n\t" \
	" sub x0,x5,x0        \n\t" \
    " sub x0,x6,x1        \n\t" \
    " sub x0,x7,x2        \n\t" \
    :[z]"=r"(z),[y]"=r"(y),[x]"=r"(x)   \
);       
