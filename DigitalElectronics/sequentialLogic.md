# Sequential Logic

## Introduction

The logic circuits discussed previously are known as combinational, in that the output depends only on the *combination* of the inputs at any point in time.

Sequential logic is a type of logic where the output depends not only on the latest inputs, but also on the *sequence* of earlier inputs. These circuits implicitly contain memory elements.

## Memory elements

A memory element stores data - usually one bit per element. A snapshot of the memory is called the *state*. A one bit memory is often called a bistable, i.e. it has 2 stable internal states. *Flip-flops* and *latches* are particular implementations of bistables

### RS latch

An RS latch is a memory element with 2 inputs: `R` (reset) and `S` (set) and 2 outputs: `Q` (memory) and `!Q` (complement memory).

![rsLatch](notesImages/rsLatch.png)

We can describe the operation of a RS Latch by using a *state transition table*, where `Q'` represents the next value of `Q`:

| `S` | `R` | `Q` | `Q'` |
| --- | --- | --- | ---- |
| 0   | 0   | 0   | 0    |
| 0   | 0   | 1   | 1    |
| 0   | 1   | 0   | 0    |
| 0   | 1   | 1   | 0    |
| 1   | 0   | 0   | 1    |
| 1   | 0   | 1   | 1    |
| 1   | 1   | 0   | `x`  |
| 1   | 1   | 1   | `x`  |

The value of `Q'` when both `R` and `S` are `1` is undefined, shown here by an `x`. We can also represent the relationship between `S`, `R` and `Q` using a *state diagram*:

![rsLatchStateDiagram](notesImages/rsLatchStateDiagram.png)

### Synchronous/Asynchronous operation


We can see that for the RS Latch, the output state changes immediately in response to the inputs. This is known as *asynchronous* operation. However, most sequential circuits use *synchronous* operation, where the output of a sequential circuit can only change when a global *enabling signal* is given. This signal is generally the system clock.

### Transparent D Latch

We can modify the RS latch so that its output can only change when it is given an enable signal. This is achieved by introducing some `AND` gates on the `R` and `S` inputs, so that they are only active when the `EN` input is `1`:

![transparentDLatch](notesImages/transparentDLatch.png)

The state transition table for the transparent D latch is as follows:

| `EN` | `D` | `Q'` |
| ---- | --- | ---- |
| 0    | 0   | `Q`  |
| 0    | 1   | `Q`  |
| 1    | 0   | 0    |
| 1    | 1   | 1    |

The transparent D latch is a *level-triggered* device, meaning it exhibits transparent behaviour when `EN = 1`, but it is often simpler to design sequential circuits if they are *edge-triggered*. We can achieve this kind of operation by combining 2 transparent D latches in a *master-slave* configuration:

![masterSlaveDFlipFlop](notesImages/masterSlaveDFlipFlop.png)

# !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

## Counters

### Ripple Counters

A ripple counter can be made be cascading together negative edge triggered T-type FFs operating in ‘toggle’ mode:

![rippleCounter](notesImages/rippleCounter.png)

This is not a synchronous design, since the flip flops are not all connected to the same clock signal, rather each gets the signal from the previous one. This results in a timing diagram like this:

![rippleCounterTiming](notesImages/rippleCounterTiming.png)

As you can see, this design is not ideal, since the outputs do not change at the same time due to propagation delay - it takes a while for the outputs to become coherent. This propagation delay stacks between each stage, limiting the maximum clock speed that will prevent miscounting.
