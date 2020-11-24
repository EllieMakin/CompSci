##### Ellie Makin (erm67)

### ECAD+arch tick answers

1. **Making use of the timer in your simulation and showing your reasoning, calculate the best-case and worst-case number of instructions your division code would take to complete.**

```
div:
    addi sp, sp, -32        # start setup
    sw   ra, 28(sp)
    sw   s0, 24(sp)
    
    # do your work
    bne  a1, zero, notzero
    ecall
notzero:
    add  t0, x0, x0
    add  t1, x0, x0
    addi t2, x0, 31
    addi t3, x0, 1
    sll  t3, t3, t2         # end setup
iter:
    slli t1, t1, 1
    srl  t4, a0, t2
    andi t4, t4, 1
    or   t1, t1, t4

    bltu t1, a1, rltd       # condition
    sub  t1, t1, a1         # only runs if condition
    or   t0, t0, t3         # only runs if condition
rltd:
    addi t2, t2, -1
    srli t3, t3, 1
    bge  t2, x0, iter

    add  a0, t0, x0         # start outro
    add  a1, t1, x0

    lw   ra, 28(sp)
    lw   s0, 24(sp)
    addi sp, sp, 32
    ret                 # expands to 2 instructions
```

The setup and outro code combined will take a constant 16 clock cycles (assuming `a1` is not zero). The `iter` loop will run 32 times. Within the loop, there is only one conditional section, which can be satisfied every time if N is all ones, and D is 1. If this conditional section is run, then the `iter` loop takes 10 instructions, which means that this would take 320 instructions in the worst case. The best case is when N is zero, when this conditional section is never run, which takes 8 instructions per loop for a total of 256 instructions. Adding the setup and outro instructions, this gives a best case of 272 instructions, and a worst case of 336 instructions.

2. **What factors control the number of instructions?**

When N has many 1s, or when D is small, the number of instructions executed is large, since these cases satisfy the condition most often. If N has few ones, or if D is small, then the condition is rarely met, so the number of instructions is lower. If I had programmed `div` to calculate the length of N first, then the most significant digit of N would also be a factor.

3. **In a pipelined processor, what would cause the number of instructions to differ from the number of clock cycles?**

If a branch instruction is predicted wrong, then the pipeline would be emptied, which would cause delays. Also, memory accesses, such as pushing values onto the stack would have latency associated with them.
