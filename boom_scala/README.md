### Note
- 修改的代码: 修改了decode.scala中很少的代码
  ```scala
    uop.is_unique  := cs.inst_unique || uop.wevent
  ```
- 主要意图：
    - 将write event的指令标记为is_unique的属性
    - 该属性要求在dispatch该指令之前，需要保证之前的所有指令都提交了
    - 通过这种方式，避免原本设计中没有提交的指令修改计数器