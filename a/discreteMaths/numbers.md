# Numbers

## Reading

### On numbers

- Chapters 27 to 29 of How to Think Like a Mathematician by K. Houston.

- Chapter 8 of Mathematics for Computer Science by E. Lehman, F. T. Leighton, and A. R. Meyer.

- Chapters I and VIII of The Higher Arithmetic by H. Davenport.

### On induction

- Chapters 24 and 25 of How to Think Like a Mathematician by K.Houston.

- Chapter 4 of Mathematics for Computer Science by E. Lehman, F. T. Leighton, and A. R. Meyer.

- Chapter 6 of How to Prove it by D.J. Velleman.

## Natural Numbers

The natural numbers $\mathbb{N}$ are generated from zero by successive increment. In OCaml:

```ocaml
type N =
    | Zero
    | Succ of N
;;
```

The basic operations of this number system are addition and multiplication.

The additive structure $(\mathbb{N}, 0, +)$ of natural numbers with zero and addition satisfies the following:

#### Commutativity

For all $m, n \in \mathbb{N}$:

$m+n = n+m$

#### Monoid laws

##### Identity element

For all $n \in \mathbb{N}$:

$0+n = n+0 = n$

##### Associativity

For all $l, m, n \in \mathbb{N}$:

$(l+m)+n = l+(m+n)$

As such the set the additive structure $(\mathbb{N}, 0, +)$ is referred to as a *commutative monoid*.

The multiplicative structure $(\mathbb{N}, 1, \cdot)$ is also a commutative monoid.

The additive structures interact nicely to satisfy the distributive law:

#### Distributivity

For $l, m, n \in \mathbb{N}$:

$l \cdot (m + n) = l \cdot m = l \cdot n$

This makes the overall structure $(\mathbb{N}, 0, +, 1, \cdot)$ into what is called a *commutative semiring*.

### Cancellation

The additive and multiplicative structures of $\mathbb{N}$ further satisfy the following laws.

#### Additive Cancellation

For all $k, m, n \in \mathbb{N}$:

$k+m = k+n \implies m = n$

#### Multiplicative Cancellation

For all $k, m, n \in \mathbb{N}, k \neq 0$:

$k \cdot m = k \cdot n \implies m = n$

## Integers and Rationals

### Inverses

#### Additive Inverse

A number $x$ is said to admit an *additive inverse* whenever there exists a number $y$ such that $x+y=0$.

Extending $\mathbb{N}$ to admit all additive inverses yields the integers $\mathbb{Z}$, which form a *commutative ring*.

#### Multiplicative Inverse

A number $x$ is said to admit a *multiplicative inverse* whenever there exists a number $y$ sich that $x \cdot y = 1$.

Extending $\mathbb{Z}$ to admit all multiplicative inverses yields the rationals $\mathbb{Q}$, which form a *field*.

### The Division Theorem

[Theorem] *For every natural number $m$ and positive natural number $n$, there exists a unique pair of integers $q$ and $r$ such that $q \ge 0$, $0 \le r \lt n$, and $m = q \cdot n + r$.*

We say $q$ and $r$ from the Division Theorem are denoted $q = \text{quo}(m, n)$ and $r = \text{rem}(m, n)$.

### The Division Algorithm

The Division Algorithm finds $\text{quo}(m, n)$ and $\text{rem}(m, n)$ for any supplied $m$ and $n$, simply by subtracting $n$ from $m$ as many times as possible. The division algorithm written in OCaml:

```ocaml
let divalg m n =
    let rec diviter q r =
        if r < n then
            (q, r)
        else
            diviter (q+1) (r-n)
    in
        diviter 0 m
;;
```

### Modulo notation

For every integer $k$ we define a unique integer $[k]_ m$ such that

$$
0 \le [k]_ m \lt m, \text{ and } \, k \equiv [k]_ m (\text{mod } m)
$$

where

## Sets

### Set Membership

The symbol $\in$ known as the *set membership predicate* is central to the theory of sets, and its purpose is to build statements of the form

$x \in A$

that are true whenever it is the case that the object $x$ is an element of the set $A$, and false otherwise. Thus, for instance, $\pi \in \mathbb{R}$ is a true statement, while $\pi \in \mathbb{Z}$ is false. The negation of the membership predicate is written $\notin$.

The following notations are equivalent:

$$
\forall x \in A (P(x)) \iff \forall x (x \in A \implies P(x)) \\

\exists x \in A (P(x)) \iff \exists x (x \in A \land P(x))
$$

### Set Definition

The conventional way to write down a finite set is to list its elements in between curly brackets, for example the set of booleans is $\{\text{true}, \text{false}\}$. However, defining large or infinite sets with this notation is not possible, and requires *set comprehension*.

#### Set Comprehension

The basic idea behind set comprehension is to define a set by means of a property that precisely characterises all elements of the set.

Given an already constructed set $A$ and a statement $P(x)$ for the variable x ranging over the set $A$, the general set comprehension notation is

$$
\{x \in A \mid P(x)\} \\
$$
or
$$
\{x \in A : P(x)\}
$$

for example, $\mathbb{N} = \{ n \in \mathbb{Z} \mid n \ge 0 \}$.

## Linear Combinations

[Definition] An integer $r$ is said to be a *linear combination* of a pair of integers $m$ and $n$ if there exist a pair of integers $s$ and $t$, referred to as the *coefficients* of the linear combination, such that $s \cdot m + t \cdot n = r$, written

$$
\begin{bmatrix}
    s & t
\end{bmatrix}
\cdot
\begin{bmatrix}
    m \\
    n \\
\end{bmatrix}
= r
$$

Note that there are an infinite number of ways to express an integer as a linear combination of two others.

### Properties

For all integers $m$ and $n$:

- It is true that:

$\begin{bmatrix} 1 & 0 \end{bmatrix}
\cdot
\begin{bmatrix} m \\ n \\ \end{bmatrix}
= m
\text{, and }
\begin{bmatrix} 0 & 1 \end{bmatrix}
\cdot
\begin{bmatrix} m \\ n \\ \end{bmatrix}
= n$

- For all integers $k$ and $s, t, r$:

$\begin{bmatrix}
    s & t
\end{bmatrix}
\cdot
\begin{bmatrix}
    m \\
    n \\
\end{bmatrix}
= r
\implies
\begin{bmatrix}
    k \cdot s & k \cdot t
\end{bmatrix}
\cdot
\begin{bmatrix}
    m \\
    n \\
\end{bmatrix}
= k \cdot r$

- For all integers $s_1, t_1, r_1$, and $s_2, t_2, r_2$:

$\begin{bmatrix} s_1 & t_1 \end{bmatrix}
\cdot
\begin{bmatrix} m \\ n \\ \end{bmatrix}
= r_1
\, \land \,
\begin{bmatrix} s_2 & t_2 \end{bmatrix}
\cdot
\begin{bmatrix} m \\ n \\ \end{bmatrix}
= r_2
\implies
\begin{bmatrix} s_1 + s_2 & t_1 + t_2 \end{bmatrix}
\cdot
\begin{bmatrix} m \\ n \\ \end{bmatrix}
= r_1 + r_2$

## Greatest Common Divisor (GCD)

### Common Divisors

Given a natural number $n$, the set of its divisors is defined by the set $D(n) = \{ d \in \mathbb{N} : d \mid n \}$. Interestingly, $D(0) = \mathbb{N}$.

The common divisors of a pair of natural numbers are defined by the set $CD(n) = \{ d \in \mathbb{N} : d \mid m \land d \mid n \}$.

The computation of both divisors and common divisors is fairly time consuming, though we will see that this is not the case for the greatest common divisor.

[Lemma] Let $m$ and $m'$ be natural numbers, and let $n$ be a positive integer such that $m = m'$ (mod $n$). Then,

$$
CD(m,n) = CD(m',n)
$$

#### Proof

Let $m$ and $m'$ be natural numbers, and let $n$ be a positive integer such that $(i) \, m = m'$ (mod $n$).

We will prove that for all positive integers $d$,

$d \mid m \land d \mid n \iff d \mid m' \land d \mid n$.

$(\implies)$ Let $d$ be a positive integer that divides both $m$ and $n$. Then, $d \mid (k \cdot n + m)$ for all integers $k$, and since, by $(i)$: $m' - k_0 \cdot n + m$ for some integer $k_0$, it follows that $d \mid m'$. As $d \mid n$ by assumption, we have $d \mid m' \land d \mid n$ as required.

$(\impliedby)$ Analogous to the previous implication.

### Euclid's algorithm

As shown above, we have that $CD(m, n) = CD(m', n)$, where $m'=m$ (mod $n$). Since we also know that $\text{rem}(m, n) = m$ (mod $n$), we can substitute to give

$$
CD(m, n) = CD(n, \text{rem}(m, n))
$$

We can then derive a recursive definition:

$$
CD (m, n) =
    \begin{cases}
    D(n) & n \mid m \\
    CD(n, \text{rem}(m, n))) & \text{otherwise} \\
    \end{cases}
$$

Since the greatest divisor in $D(n)$ is simply $n$, we get

$$
\text{gcd}(m, n) =
    \begin{cases}
    n & n \mid m \\
    \text{gcd}(n, \text{rem}(m, n))) & \text{otherwise} \\
    \end{cases}
$$

for two positive integers $m$ and $n$.

We can write $\text{gcd}(m, n)$ in OCaml, using our implementation of the division algorithm from before:

```ocaml
let rec gcd m n =
    let (q, r) = divalg m n
    in
        if r == 0 then
            n
        else
            gcd n r
;;
```

Note that in the case where $m < n$, the result of `divalg m n` returns `(0, m)`, which leads to calling `gcd n m`, giving the desired result anyway.

#### Time Complexity

We can analyse the time complexity of Euclid's Algorithm by noting that after any call to $\text{gcd}(m, n)$ with $m > n$, if the function doesn't terminate, $n$ is at least halved. This can be seen intuitively, since if $n \gt m/2$, the remainder must be $r < m/2$, and if $n < m/2$, then we have $r < m/2$, since $r < n$.

This means that after two recursions of the function $\text{gcd}(m,n)$, both $m$ and $n$ will be less than half of their original values, so the time complexity is bounded by $\text{gcd}(m, n) = O(log(min(m,n)))$, hence the time complexity is at most of logarithmic order.

### Properties of GCD

These 3 basic properties are fairly intuitive:

- Commutativity: $\text{gcd}(m, n) = \text{gcd}(n, m)$

- Associativity: $\text{gcd}(l, \text{gcd}(m, n)) = \text{gcd}(\text{gcd}(l, m), n)$

- Linearity: $\text{gcd}(l \cdot m, l \cdot n) = l \cdot \text{gcd}(m, n)$

Fairly trivially, it can be shown that for all integers $k$ and $l$, $\text{gcd}(m, n) \mid (k \cdot m + l \cdot n)$. Following from this, we can see that if there exist integers $k$ and $l$ such that $k \cdot m + l \cdot n = 1$, then $\text{gcd}(m, n) = 1$, since the gcd must divide 1.

For all positive integers $m$ and $n$, $\text{gcd}(m, n)$ is the smallest positive linear combination of $m$ and $n$.This can be seen from the previous property, since if $\text{gcd}(m, n)$ divides all linear combinations of $m$ and $n$, then it must be the smallest of those.

### Extended Euclid's Algorithm

The Extended Euclid's Algorithm provides a means of finding the greatest common divisor of two integers $m$ and $n$, while concurrently finding the integers $s$ and $t$, such that

$$
\begin{bmatrix}
    s & t
\end{bmatrix}
\cdot
\begin{bmatrix}
    m \\
    n \\
\end{bmatrix}
= \text{gcd}(m, n)
$$

This is accomplished by making sure that the equation $\begin{bmatrix}
    s_i & t_i
\end{bmatrix}
\cdot
\begin{bmatrix}
    m \\
    n \\
\end{bmatrix}
= r_i$ is satisfied at each step $i$ in the algorithm, so that in the final step $N$, $\begin{bmatrix}
    s_N & t_N
\end{bmatrix}
\cdot
\begin{bmatrix}
    m \\
    n \\
\end{bmatrix} = r_N = \text{gcd}(m, n)$.

In order to do this, we use the properties of linear combinations discussed above.

1. We begin with $r_0 = m$, $r_1 = n$,  $\begin{bmatrix} s_0 & t_0 \end{bmatrix} = \begin{bmatrix} 1 & 0 \end{bmatrix}$, and $\begin{bmatrix} s_1 & t_1 \end{bmatrix} = \begin{bmatrix} 0 & 1 \end{bmatrix}$. This uses the first property of linear combinations to satisfy the requirement.

2. In each step of the Euclid's original algorithm, we produced the sequence $r$ such that $r_i = \text{rem}(r_{i-2}, r_{i-1})$. In other words, $r_{i-2} = q_i \cdot r_{i-1} + r_i$, so we have
$r_i = r_{i-2} - q_i \cdot r_{i-1}$.

3. We can then apply the second property of linear combinations, to find that $q_i \cdot r_{i-1} = \begin{bmatrix} q_i \cdot s_{i-1} & q_i \cdot t_{i-1} \end{bmatrix} \cdot \begin{bmatrix} m \\ n \end{bmatrix}$

4. Finally, by using this, and the third property of linear combinations, we get $r_i = r_{i-2} - q_i \cdot r_{i-1} = \begin{bmatrix} s_{i-2} - q_i \cdot s_{i-1} & t_{i-2} - q_i \cdot t_{i-1} \end{bmatrix} \cdot \begin{bmatrix} m \\ n \end{bmatrix}$, so:

$$
s_i = s_{i-2} - q_i \cdot s_{i-1}, \text{ and } t_i = t_{i-2} - q_i \cdot t_{i-1}
$$

We can use these formulae to calculate $s_i$ and $t_i$, such that the requirement mentioned above is satisfied for all $i$.


The algorithm written in OCaml:

```ocaml
let egcd m n =
    let rec egcditer ((s1, t1), r1) ((s2, t2), r2) =
        (* r = r1 - q*r2 *)
        let (q, r) = divalg r1 r2
        in
            if r = 0 then
                ((s2, t2), r2)
            else
                egcditer ((s2, t2), r2) ((s1 - q*s2, t1 - q*t2), r)
    in
        egcditer ((1, 0), m) ((0, 1), n)
;;
```

### Euclid's Theorem

[Euclid's Theorem] For positive integers $m$, $n$ and prime $p$, $p \mid (m \cdot n) \implies p \mid m \lor p \mid n$

#### Proof

To prove Euclid's theorem, we will instead prove a more general case, of which Euclid's Theorem is a corollary.

[Not Euclid's Theorem] For positive integers $k$, $m$ and $n$, if $k \mid (m \cdot n)$ and $\text{gcd}(k, m) = 1$, then $k \mid n$.

We now prove this new statement.

Let $k$, $m$, $n$ be positive integers, and assume that

$(i) \, k \mid (m \cdot n)$

and

$(ii) \, \text{gcd}(k, m) = 1$.

Using $(i)$, let $l$ be an integer such that

$(iii) \, k \cdot l = m \cdot n$.

Using $(ii)$, we have that $n = \text{gcd}(k, m) \cdot n$. Using linearity, we get $n = \text{gcd}(k \cdot n, m \cdot n)$, and by $(iii)$, $n = \text{gcd}(k \cdot n, k \cdot l) = k \cdot \text{gcd}(n, l)$, so $k$ divides $n$ as required.

## Modular Arithmetic

For every positive integer $m$, the integers *modulo* $m$ are

$$
\mathbb{Z}_m = \{ 0, 1, \ldots, m-1 \}
$$

with arithmetic operations of addition ${+}_m$ and multiplication $\cdot_m$ defines as follows, for $k,l \in \mathbb{Z}_m$:

$k {+}_m l = \text{rem}(k + l, m)$

$k \cdot_m l = \text{rem}(k \cdot l, m)$

The modular arithmetic structure $(\mathbb{Z}_2, 0, {+}_2, 1, \cdot_2)$ is that of booleans with `XOR` as addition and `AND` as multiplication.

The addition and multiplication tables for $\mathbb{Z}_5$ are:

| ${+}_5$ | 0 | 1 | 2 | 3 | 4 |
| ------- | - | - | - | - | - |
| **0**   | 0 | 1 | 2 | 3 | 4 |
| **1**   | 1 | 2 | 3 | 4 | 0 |
| **2**   | 2 | 3 | 4 | 0 | 1 |
| **3**   | 3 | 4 | 0 | 1 | 2 |
| **4**   | 4 | 0 | 1 | 2 | 3 |

The addition table has a cyclic pattern.

| $\cdot_5$ | 0 | 1 | 2 | 3 | 4 |
| --------- | - | - | - | - | - |
| **0**     | 0 | 0 | 0 | 0 | 0 |
| **1**     | 0 | 1 | 2 | 3 | 4 |
| **2**     | 0 | 2 | 4 | 1 | 3 |
| **3**     | 0 | 3 | 1 | 4 | 2 |
| **4**     | 0 | 4 | 3 | 2 | 1 |

### Inverses

From the addition and multiplication tables, we can readily read tables for additive and multiplicative inverses:

| $[n]_5$        | additive inverse |
| -------------- | ---------------- |
| 0              | 0                |
| 1              | 4                |
| 2              | 3                |
| 3              | 2                |
| 4              | 1                |

| $[n]_5$        | multiplicative inverse |
| -------------- | ---------------------- |
| 0              | none                   |
| 1              | 1                      |
| 2              | 3                      |
| 3              | 2                      |
| 4              | 4                      |

For all natural numbers $m > 1$, the modular arithmetic structure $(\mathbb{Z}_m, 0, {+}_m, 1, \cdot_m)$ is a *commutative ring*.

For primes $p$, every nonzero element of $\mathbb{Z}_p$ has a multiplicative inverse, so $(\mathbb{Z}_p, 0, {+}_p, 1, \cdot_p)$ forms a *field*.

#### Multiplicative inverse

We can use the Extended Euclid's Algorithm to find multiplicative inverses in modular arithmetic. Remembering that for any positive integers $m$ and $n$, the extended Euclid's Algorithm finds integers $s$ and $t$, such that

$$
\begin{bmatrix}
    s & t
\end{bmatrix}
\cdot
\begin{bmatrix}
    m \\
    n \\
\end{bmatrix}
= \text{gcd}(m, n)
$$

Since $s \cdot m + t \cdot n = \text{gcd}(m, n)$, we can say that $t \cdot n = \text{gcd}(m, n)$ (mod $m$). Thus, whenever $gcd(m, n) = 1$ then $t \cdot n = 1$ (mod $m$), or in other words, $[t \cdot n]_ m = 1$. This means that $[t]_ m$ is the multiplicative inverse of $[n]_ m$ in $\mathbb{Z}_m$.

For every pair of positive integers $m$ and $n$, we have that $[n]_ m$ has a multiplicative inverse in $\mathbb{Z}_m$ iff $\text{gcd}(m, n) = 1$. This is always true for prime $m$.

### Diffie-Hellman Key Exchange

[Lemma] Let $p$ be a prime, and $e$ a positive integer with $\text{gcd}(p-1, e) = 1$. Define

$$
d = [t]_ {p-1}
$$

where $\begin{bmatrix}
    s & t
\end{bmatrix}
\cdot
\begin{bmatrix}
    p-1 \\
    e \\
\end{bmatrix}
= \text{gcd}(p-1, e)$. Then, for all integers $k$,

$$
(k^e)^d \equiv k \, (\text{mod } p)
$$

#### Proof

Let $p$, $e$, $d$ be as stated in the lemma.

For integers $k$ which are a multiple of $p$, the results is trivial, since $k = 0$ (mod $p$).

By definition, $s \cdot (p-1) + t \cdot e = 1$. Then, $e \cdot d = 1 + c \cdot (p-1)$, and hence by Fermat's Little Theorem, for all integers $k$ not a multiple of $p$,

$$
k^{e \cdot d} = k \cdot k^{c \cdot (p-1)} \equiv k \, (\text{mod } p)
$$

and we are done.
