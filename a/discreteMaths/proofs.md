# Proof

## Definitions

- **Statement** - A sentence that is either true or false, but not both.

- **Predicate** - A statement whose truth depends on the value of one or more variables.

- **Theorem** - A very important true statement.

- **Proposition** - A less important but nonetheless interesting true statement. e.g.

- **Lemma** - A true statement used in proving other true statements.

- **Corollary** - A true statement that is a simple deduction from a theorem or proposition.

- **Conjecture** - A statement believed to be true, but for which we have no proof.

- **Proof** - A logical explanation of why a statement is true; a method for establishing truth.

- **Logic** - The study of methods and principles used to distinguish good (correct) from bad (incorrect) reasoning.

- **Axiom** - A basic assumption about a mathematical situation.

- **Definition** - An explanation of the mathematical meaning of a word or phrase.

- **Simple statement** - A statement that cannot be broken down into other statements.

- **Composite statement** - A statement that is built using several (simple or composite) statements, connected by logical expressions, such as `if _ then _; either _ or _; for all _;` etc.

## Proofs

A mathematical proof is a sequence of logical deductions from axioms and previously proved statements that concludes with the proposition in question. The axiom-and-proof approach is called the *axiomatic method*.

Mathematical proofs play a growing role in computer science: they are used to certify that software and hardware will always behave correctly; something that no amount of testing can do.

### Proof Technique

- Keep a linear flow.

- A proof is an essay, not a calculation.

- Avoid excessive symbolism.

- Revise and simplify.

- Introduce notation thoughtfully.

- Structure long proofs.

- Be wary of the “obvious”.

### Implication

Theorems can usually be written in the form

*`if` a collection of assumptions holds, `then` so does some conclusion*

or

*a collection of assumptions `implies` some conclusion.*

or

*a collection of hypotheses $\implies$ some conclusion.*

### Modus Ponens

 A main rule of logical deduction is that of Modus Ponens:

 *If $P$ and $P \implies Q$ hold, then so does $Q$.*

### Bi-implication

Some theorems can be written in the form $P$ `is equivalent to` $Q$, or $P$ `if and only if` $Q$, or $P$ `iff` $Q$. In symbols, this is written $P \iff Q$

To use an assumption of the form $P \iff Q$, use it as two separate assumptions $P \implies Q$, and $Q \implies P$.

### Conjunction

Conjunctive statements are of the form:

$P$ `and` $Q$

or, in symbols,

$P \land Q$

### Disjunction

Disjunctive statements are of the form

$P$ `or` $Q$

or in symbols,

$P \lor Q$

### Divisibility

Let $d$ and $n$ be integers. We say that $d$ divides $n$, and write $d \mid n$, whenever there is an integer $k$ such that $n=k \cdot d$.

### Congruence

[Definition] Fix a positive integer $m$. For integers $a$ and $b$, we say that $a$ is congruent to $b$ modulo $m$, and write $a \equiv b$ (mod $m$), whenever $m \mid (a−b)$

### Equality Axioms

- Every individual is equal to itself: $\forall x (x = x)$.
- For any pair of equal individuals, if a property holds for one of them, then it also holds for the other: $\forall x \forall y (x = y \implies (P(x) \iff P(y)))$

From these axioms you can deduce the usual intuitive properties of equality, such as $\forall x \forall y(x = y \iff y = x)$ and $\forall x \forall y \forall z (x = y \implies (x = z \implies y = z))$.

### Universal Quantification

Universal statements are of the form `for all` individuals $x$ of the universe of discourse, the property $P(x)$ holds, or in symbols, $\forall x P(x)$

### Existential Quantification

Existential statements are of the form:

`There exists` an individual $x$ in the universe of discourse for which the property $P(x)$ holds

or in symbols:

$\exists x P(x)$

### Unique Existence

The notation $\exists! x P(x)$ stands for the `unique existence` of some $x$ for which the property $P(x)$ holds. This may be expressed in a variety of equivalent ways as follows:

- $\exists x P(x) \land(\forall y \forall z(P(y) \land P(z)) \implies y = z)$

- $\exists x (P(x) \land \forall y (P(y) \implies y = x))$

- $\exists x \forall y (P(y) \iff y = x)$

### Logical Equivalences
| Statement               | Equivalent            |
| ----------------------- | --------------------- |
| $\neg P$                | $P \implies false$    |
| $\neg(\neg P)$          | $P$                   |
| $\neg (P \land Q)$      | $\neg P \lor \neg Q$  |
| $\neg(P \lor Q)$        | $\neg P \land \neg Q$ |
| $\neg(P \implies Q)$    | $P \land \neg Q$      |
| $\neg(P \iff Q)$        | $P \iff \neg Q$       |
| $\neg (\forall x P(x))$ | $\exists x \neg P(x)$ |
| $\neg (\exists x P(x))$ | $\forall x \neg P(x)$ |

## Proof Patterns

### Deduction

To prove a goal of the form $P \implies Q$, assume that $P$ is true, and prove $Q$. When writing a proof, this is done by writing:

$\text{Assume $P$.}$

and then showing that $Q$ logically follows.

#### Example

[Definition] An integer is said to be *odd* whenever it is of the form $2i+1$ for some (necessarily unique) integer $i$.

[Proposition] If $m$ and $n$ are odd integers, then so is $m \cdot n$.

#### Proof

Assume that $m$ and $n$ are odd integers. That is, $m=2i+1$ and $n=2j+1$ for some integers i and j. Hence, we have that

$$
m \cdot n = (2i+1) \cdot (2j+1) \\
= 4ij + 2i + 2j + 1 \\
= 2(2ij + i + j)+1 \\
= 2k+1
$$

for $k = 2ij + i + j$, showing that $m \cdot n$ is indeed odd.

### Contrapositive

An equivalent to the statement $P \implies Q$, is $\neg P \impliedby \neg Q$, or alternatively, $\neg Q \implies \neg P$. This is called the *contrapositive* of the statement. In order to prove an implication, we can instead prove the equivalent statement given by its contrapositive. When writing a proof, this is done by writing:

$\text{We prove the contrapositive; that is, $\neg Q \implies \neg P$. Assume $\neg Q$.}$

Then show that $\neg P$ logically follows.

#### Example

[Definition] A real number is:

- rational if it is of the form $\frac{m}{n}$ for a pair of integers $m$ and $n$; otherwise it is irrational.
- positive if it is greater than $0$, and negative if it is smaller than $0$.
- nonnegative if it is greater than or equal to $0$, and nonpositive if it is smaller than or equal to $0$.
- natural if it is a nonnegative integer.

[Proposition] Let $x$ be a positive real number. If $x$ is irrational then so is $\sqrt{x}$.

#### Proof

Assume $x$ is a positive real number. We prove the contrapositive; that is, if $\sqrt{x}$ is rational then $x$ is rational. That is, $\sqrt{x} = \frac{m}{n}$ for some integers $m$ and $n$. Hence, we have $x = \frac{m^2}{n^2} = \frac{a}{b}$, for $a=m^2$ and $b=n^2$, showing that $x$ is indeed rational.


### Proof by Contradiction

In order to prove a statement $P$, we can use the contrapositive to instead prove the equivalent statement, $\neg P \implies false$. When writing a proof, we can write

$\text{We use a proof by contradiction. So, suppose $\neg P$.}$

then deduce a logical contradiction, and write

$\text{This is a contradiction, therefore $P$ must be true.}$

#### Example

[Theorem] For all statements $P$ and $Q$,
$$
(¬Q \implies ¬P) \implies (P \implies Q)
$$

#### Proof

Assume $(i)$ $¬Q \implies ¬P$, and $(ii)$ $P$. We need show $Q$.

Assume, by way of contradiction, that $(iii)$ $¬Q$ holds.

From $(i)$ and $(iii)$, we have $(iv)$ $¬P$ and now, from $(ii)$ and $(iv)$, we obtain a contradiction. Thus, $¬Q$ cannot be the case; hence $Q$ as required.

### Bi-implication

In order to prove that $P \iff Q$ we must prove that $P \implies Q$ and $P \impliedby Q$. When writing a proof, we write:

$(\implies)$, and give a proof of $P \implies Q$


$(\impliedby)$, and give a proof of $P \impliedby Q$

#### Example

[Proposition] Suppose that $n$ is an integer. Then, $n$ is even if and only if $n^2$ is even.

#### Proof

$(\implies)$ Assume $n$ is an even integer. That is, $n = 2a$ for some integer $a$. Then, $n^2 = 4a^2 = 2(2a^2) = 2k$, for $k = 2a^2$, showing $n^2$ is even.

$(\impliedby)$ We prove the contrapositive, that is, if $n$ is odd then $n^2$ is odd. Assume that $n$ is odd. That is, by definition, $n = 2b+1$ for some integer $b$. Then, $n^2 = (2b+1)^2 = 4b^2 + 4b + 1 = 2(2b^2 + 2b) + 1 = 2m + 1$, for $m = 2b^2 + 2b$, showing that $n^2$ is odd.

### Conjunctive Statements

To prove a goal of the form $P \land Q$, first prove $P$, and then prove $Q$, or vice versa. When writing a proof, write:

$\text{Firstly, we prove $P$.}$

then give a proof for $P$, and write

$\text{Secondly, we prove $Q$.}$

then give a proof for $Q$.

To use an assumption of the form $P \land Q$, treat it as two separate assumptions, $P$ and $Q$.

#### Example

[Theorem] For every integer $n$, we have that $6 \mid n$ if and only if $2 \mid n$ and $3 \mid n$.

#### Proof

$( \implies )$ Assume $n$ is an integer, and that $6 \mid n$. That is, by definition, $n = 6k$, for some integer $k$.

Firstly, we prove that $2 \mid n$. We have that $n = 6k = 2 \cdot 3k = 2l$, where $l = 3k$, so $2 \mid n$ as required.

Secondly, we prove that $3 \mid n$. We have that $n = 6k = 3 \cdot 2k = 3m$, where $m = 2k$, so $3 \mid n$ as required.

$( \impliedby )$ Assume that $n$ is an integer, and that $2 \mid n$ and $3 \mid n$. That is, by definition, $n = 2p$, and $n = 3q$ for some integers $p$ and $q$. Then, $n = 3n - 2n = 3 \cdot 2p - 2 \cdot 3q = 6p - 6q = 6(p-q) = 6r$, where $r = p - q$. We now have $6 \mid n$ as required.

### Disjunctive Statements

To prove a goal of the form $P \lor Q$, you can do one of three things:

1. Prove $P$

2. Prove $Q$

3. Break the proof into cases, in each case proving either $P$ or $Q$.

One particular way to write this is a proof is to write

$\text{If $P$ is true, then of course $P \lor Q$ is true. Now suppose that $P$ is false.}$

and then provide a proof of $Q$.

To use an assumption of the form $P \lor Q$ to establish a statement $S$, two different proofs must be given:

1. Assume $P$, and then prove $S$

2. Assume $Q$ and then prove $S$.

When writing a proof, this is written

$\text{We prove the following two cases in turn:}$

$\text{$(i)$ that assuming $P$, we have $S$; and}$

$\text{$(ii)$ that assuming $Q$, we have $S$.}$

$\text{Case $(i)$: Assume $P$}.$

and then provide a proof for $S$. Then write

$\text{Case $(ii)$: Assume $Q$.}$

and then provide a proof for $S$.

#### Example

[Definition]
$$
\binom{a}{b} = \frac{a!}{b! (a-b)!}
$$

[Lemma] For all nonnegative integers $p$ and $m$, if $m = 0$ or $m = p$, then $\binom{p}{m} \equiv 1$ (mod $p$).

#### Proof

Let $p$ and $m$ be arbitrary nonnegative integers. We prove the following two cases in turn: $(i)$ that assuming $m = 0$, we have $\binom{p}{m} \equiv 1$ (mod $p$); and $(ii)$ that assuming $m = p$,we have $\binom{p}{m} \equiv 1$ (mod $p$).

Case $(i)$: Assume $m = 0$. Then, by definition, $\binom{p}{m} = \frac{p!}{m! (p-m)!} = \frac{p!}{0! (p-0)!} = \frac{p!}{p!} = 1$, so $\binom{p}{m} \equiv 1$ (mod $p$).

Case $(ii)$: Assume $m = p$. Then, by definition, $\binom{p}{m} = \frac{p!}{m! (p-m)!} = \frac{p!}{p! (p-p)!}= \frac{p!}{p!} = 1$, so $\binom{p}{m} \equiv 1$ (mod $p$).

### Universal Statements

In order to prove that $\forall x \, P(x)$, we let $x$ stand for an arbitrary individual, and prove $P(x)$. When writing a proof, we write:

$\text{Let $x$ be an arbitrary individual.}$

And then show that $P(x)$ holds.

To use an assumption of the form $\forall x P(x)$, you can plug in any value $a$ for $x$ to conclude that $P(a)$ is true, and so further assume it. This rule is called *universal instantiation*.

#### Example

[Proposition] Fix a positive integer $m$. For integers $a$ and $b$, we have that $a \equiv b$ (mod $m$), if and only if, for all positive integers $n$, we have that $a \cdot n \equiv b \cdot n$ (mod $m \cdot n$).

#### Proof

$(\implies)$ Assume $m \in \mathbb{Z}^+$, $a, b \in \mathbb{Z}$, and $a \equiv b$ (mod $m$). That is, by definition, $m \mid (a-b)$, or $a - b = km$, for some integer $k$. Let $n$ be an arbitrary positive integer. Then, $n(a-b) = n(km)$, so $a \cdot n - b \cdot n = k(m \cdot n)$. Thus, $m \cdot n \mid (a \cdot n - b \cdot n)$, so $a \cdot n \equiv b \cdot n$ (mod $m \cdot n$), as required.

$(\impliedby)$ Assume that for all positive integers $n$, we have that $a \cdot n \equiv b \cdot n$ (mod $m \cdot n$). In particular, we have his property for $n = 1$, which states that $1 \cdot a \equiv 1 \cdot b$ (mod $1 \cdot m$). Thus, $a \equiv b$ (mod $m$), as required.

### Existential Statements

In order to prove a goal of the form $\exists x \, P(x)$, find a *witness* for the existential statement; that is, a value of $x$, say $w$, for which you think that $P(x)$ will be true, and then show that $P(w)$ is indeed true. When writing proofs, write:

$\text{Let $w =$ [the witness you decided on].}$

Then provide a proof for $P(w)$.

To use an assumption of the form $\exists x \, P(x)$, introduce a new variable $x_0$ into the proof to stand for some individual for which the property $P(x)$ holds. This means that you can now assume $P(x_0)$ to be true.

#### Example

[Proposition] For every positive integer $n$, there exists a natural number $l$ such that $2^l \leq n \lt 2^{l+1}$

#### Proof

For an arbitrary positive integer $n$, let $l = \lfloor \log_2{n} \rfloor$. We then have that $l \leq \log_2{n} \lt l+1$. Since the exponential function is increasing, we have $2^l \leq 2^{log_2{n}} \lt 2^{l+1}$, so $2^l \leq n \lt 2^{l+1}$ as required.

### Induction

In order to prove that $\forall x \in \mathbb{N} \, (P(x))$, write

$\text{Induction hypothesis:}$

and write out $P(x)$. Then,

$\text{Base case:}$

and give a proof of $P(0)$. Then write

$\text{Inductive step:}$

and give a proof that $\forall n \in \mathbb{N} \, (P(n) \implies P(n+1))$. Finally, write

$\text{By the principle of induction, we conclude that $P(m)$ holds for all natural numbers $x$}$.
