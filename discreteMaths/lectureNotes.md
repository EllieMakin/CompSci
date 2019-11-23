# Discrete Mathematics

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

### Divisibility

Let $d$ and $n$ be integers. We say that $d$ divides $n$, and write $d \mid n$, whenever there is an integer $k$ such that $n=k \cdot d$.

### Congruence

[Definition] Fix a positive integer $m$. For integers $a$ and $b$, we say that $a$ is congruent to $b$ modulo $m$, and write $a \equiv b$ (mod $m$), whenever $m \mid (a−b)$

### Universal Quantification

Universal statements are of the form `for all` individuals $x$ of the universe of discourse, the property $P(x)$ holds, or in symbols, $\forall x P(x)$

## Proof Patterns

### Deduction

To prove a goal of the form $P \implies Q$, assume that $P$ is true, and prove $Q$. When writing a proof, this is done by writing:

*Assume $P$.*

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

*We prove the contrapositive; that is, $\neg Q \implies \neg P$. Assume $\neg Q$.*

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

### Bi-implication

In order to prove that $P \iff Q$ we must prove that $P \implies Q$ and $P \impliedby Q$. When writing a proof, we write:

$(\implies)$, and give a proof of $P \implies Q$


$(\impliedby)$, and give a proof of $P \impliedby Q$

#### Example

[Proposition] Suppose that $n$ is an integer. Then, $n$ is even if and only if $n^2$ is even.

#### Proof

$(\implies)$ Assume $n$ is an even integer. That is, $n = 2a$ for some integer $a$. Then, $n^2 = 4a^2 = 2(2a^2) = 2k$, for $k = 2a^2$, showing $n^2$ is even.

$(\impliedby)$ We prove the contrapositive, that is, if $n$ is odd then $n^2$ is odd. Assume that $n$ is odd. That is, by definition, $n = 2b+1$ for some integer $b$. Then, $n^2 = (2b+1)^2 = 4b^2 + 4b + 1 = 2(2b^2 + 2b) + 1 = 2m + 1$, for $m = 2b^2 + 2b$, showing that $n^2$ is odd.

### Universal Statements

In order to prove that $\forall x P(x)$, we let $x$ stand for an arbitrary individual, and prove $P(x)$. When writing a proof, we write:

Let $x$ be an arbitrary individual.

And then show that $P(x)$ holds.

#### Example

[Proposition] Fix a positive integer $m$. For integers $a$ and $b$, we have that $a \equiv b$ (mod $m$), if and only if, for all positive integers $n$, we have that $n \cdot a \equiv n \cdot b$ (mod $n \cdot m$).

#### Proof

RTP: $\forall m \in \mathbb{Z}^+ \forall a, b \in \mathbb{Z} (\\
    a \equiv b \text{ (mod }m) \iff \forall n \in \mathbb{Z}^+(a \cdot n \equiv b \cdot n \text{ (mod }m \cdot n))\\
)$

($\implies$) Assume $m \in \mathbb{Z}^+$, $a, b \in \mathbb{Z}$, and $a \equiv b$ (mod $m$). That is, by definition, $m \mid (a-b)$, or $a - b = km$, for some integer $k$. Let $n$ be an arbitrary positive integer. Then, $n(a-b) = n(km)$, so $a \cdot n - b \cdot n = k(m \cdot n)$. Thus, $m \cdot n \mid (a \cdot n - b \cdot n)$, so $a \cdot n \equiv b \cdot n$ (mod $m$), as required.
