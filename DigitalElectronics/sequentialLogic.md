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

The Master-Slave configuration has now been superseded by new F-F circuits which are easier to implement and have better performance; when designing synchronous circuits it is best to use truly edge-triggered flip flop devices

### JK Flip Flop

![jkFlipFlop](notesImages/jkFlipFlop.png)

The JK flip flop is similar in function to a clocked RS flip flop, but with the undefined state - when both inputs are `1` - being replaced by a 'toggle' function. The state transition table for the JK flip flop:

| `J` | `K` | `Q'` |
| --- | --- | ---- |
| 0   | 0   | `Q`  |
| 0   | 1   | 0    |
| 1   | 0   | 1    |
| 1   | 1   | `!Q` |

### T Flip Flop

![tFlipFlop](notesImages/tFlipFlop.png)

The T flip flop is essentially a JK flip flop with its inputs combined, so if the input `T` is `0`, it holds its state, and if the input is `1` it toggles (hence **T** flip flop).

| `T` | `Q'` |
| --- | ---- |
| 0   | `Q`  |
| 1   | `!Q` |

### Asynchronous inputs

It is common for flip flops to also have additional so called *asynchronous* inputs, such as clear, or set, which function independently of clock pulses. These are often used to force a synchronous circuit into a known state, say at startup.

### Timing requirements

Various timings must be satisfied if a flip flop is to operate properly:

- Setup time - the minimum duration that the data must be stable at the input before the clock edge

- Hold time: Is the minimum duration that the data must remain stable on the FF input after the clock edge

![timingRequirements](notesImages/timingRequirements.png)

## Counters

### Ripple Counters

A ripple counter can be made be cascading together negative edge triggered T-type FFs operating in ‘toggle’ mode:

![rippleCounter](notesImages/rippleCounter.png)

This is not a synchronous design, since the flip flops are not all connected to the same clock signal, rather each gets the signal from the previous one. This results in a timing diagram like this:

![rippleCounterTiming](notesImages/rippleCounterTiming.png)

As you can see, this design is not ideal, since the outputs do not change at the same time due to propagation delay - it takes a while for the outputs to become coherent. This propagation delay stacks between each stage, limiting the maximum clock speed that will prevent miscounting.

Ignoring propagation delay, you can see that the frequency of each successive counter signal is 1/2 of the previous one, but we may want to use a counter which is not based on a power of 2, e.g. a binary coded decimal (BCD) counter. In order to achieve this, we can use flip flops that have a set/clear asynchronous input, and use an `AND` gate to detect the upper limit, and trigger the lower limit to be written into the flip flops.

### Synchronous counters

Ripple counters should not usually be used to implement counter functions; It is recommended that synchronous counter designs be used instead. In a synchronous design, all the flip flop clock inputs are directly connected to the same clock signal, so all flip flop outputs change at the same time. More complex combinational logic is also needed to generate the appropriate flip flop input signals.

### Excitation table

An excitation table for a flip flop describes which input values are required to achieve a particular next from a given current state. The excitation table can be derived by rearranging the columns of the state transition table. For example, for a D flip flop, the excitation table is:

| `Q` | `Q'` | `D` |
| --- | ---- | --- |
| 0   | 0    | 0   |
| 0   | 1    | 1   |
| 1   | 0    | 0   |
| 1   | 1    | 1   |

Clearly for a D flip flop, `D = Q'`, but this is not generally true for other flip flop types, when the excitation table will be more useful. The excitation table for a JK flip flop:

| `Q` | `Q'` | `J` | `K` |
| --- | ---- | --- | --- |
| 0   | 0    | 0   | x   |
| 0   | 1    | 1   | x   |
| 1   | 0    | x   | 1   |
| 1   | 1    | x   | 0   |

We can use these excitation tables to derive the logic needed for a synchronous counter. For a 0 to 7 counter, 3 D flip flops are required. Since we know that `D = Q'` for D flip flops, we can use a state transition table to work out what the D inputs should be based on the current `Q` values.

| `Q2` | `Q1` | `Q0` | `D2` | `D1` | `D0` |
| ---- | ---- | ---- | ---- | ---- | ---- |
| 0    | 0    | 0    | 0    | 0    | 1    |
| 0    | 0    | 1    | 0    | 1    | 0    |
| 0    | 1    | 0    | 0    | 1    | 1    |
| 0    | 1    | 1    | 1    | 0    | 0    |
| 1    | 0    | 0    | 1    | 0    | 1    |
| 1    | 0    | 1    | 1    | 1    | 0    |
| 1    | 1    | 0    | 1    | 1    | 1    |
| 1    | 1    | 1    | 0    | 0    | 0    |

From inspection, we can see that `D0 = !Q0`, and `D1 = Q0 XOR Q1`. We can use a K-map to derive `D2 = !Q0.Q2 + !Q1.Q2 + Q0.Q1.!Q2`. This results in the following circuitry for a 3-bit synchronous counter:

![synchronousCounter](notesImages/synchronousCounter.png)

A similar method can be used to design counters for an arbitrary count sequence.
