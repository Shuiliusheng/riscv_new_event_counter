# riscv_new_event_counter
 
1. 目标：在boom中增加一些事件计数器，用于记录某些硬件事件，类似于HPM所做的事。
   没有使用HPM的原因：
   (1) 没有看到linux对hpm很好的支持，
   (2) 希望能够将这些计数器添加到进程/线程的数据结构中，支持在线程切换时同步保存和替换为新的线程的值；
   (3) 希望不需要考虑权限问题

2. boom的scala代码修改部分：大致部分，还有一些细节没列出来
   (1) decode: 使用add zero,x,x 和sub zero,x,x来实现读取和写入事件计数器的值
   (2) core: 增加事件计数器单元，初始化event信号接口和执行单元的读入/写入接口
   (3) functional unit: 增加对新的指令的支持

3. linux/arch/riscv代码的修改：
    (1) include/asm/processor.h: thread_struct增加记录counter的数组
    (2) kernel/entry.S: 在switch_to函数中，增加记录写入counter的指令
    (3) kernel/asm_offset.c: 增加counter的偏移值定义

4. 使用时应将改动的地方添加到自己当前的项目中，不能直接拷贝过去，会出现错误