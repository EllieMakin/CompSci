# Semantics of Programming languages

## Introduction

Where syntax specifies what forms a program can take in a certain language, semantics specifies what meaning that program has, or what behaviour it has.

This specification could be described in a few different ways
- Definition by whatever some compiler does - if there is a bug in the compiler, this may not be what the designers intended.
- Definition by natural language description - can be verbose and confusing.
- Definition by mathematics - can be used for proof and is generally useful.

Semantics can be used to understand a particular language better, as a tool for language design and specification, or to make proofs about certain language or program properties.

There are three main approaches to semantic definitions:

- Denotational semantics - Phrases in the language correspond to mathematical objects which describe their meaning.
- Operational semantics - The execution of the language is described by referring to changes to an abstract machine, or syntactic changes to the language itself.
- Axiomatic semantics - a sort of middle ground between operational and denotational semantics.

We will mostly use operational semantics.

Sometimes, instead of considering the full features of a language when reasoning about it, we may choose to define a "toy" language with a subset of the features of the main language, to reduce the complexity.

## L1

Here we define an simple imperative language, L1, with . Here is an example snippet:
```ocaml
l2 := 0;
while !l1 >= 1 do (
    l2 := !l2 + !l1;
    l1 := !l1 + -1;
)
```

### BNF grammar

Backus-Naur form (BNF) is a way to describe the syntax of a programming language. A BNF grammar is a set of rules, written as
$$
\text{symbol} ::= \text{exp}_0 | \text{exp}_1 | \cdots
\newcommand{\op}{\operatorname{op}}
\newcommand{\if}{\operatorname{if}}
\newcommand{\then}{\operatorname{then}}
\newcommand{\else}{\operatorname{else}}
\newcommand{\while}{\operatorname{while}}
\newcommand{\do}{\operatorname{do}}
\newcommand{\skip}{\operatorname{skip}}
\newcommand{\domain}{\operatorname{domain}}
%hahaha I'm so sneaky
$$
where the vertical bar indicates a choice.

Here I haven't defined a full BNF grammar for L1 (but I could've!). The basic symbols are are

- Booleans $b \in \mathbb{B} = \{ true, false \}$
- Integers $n \in \mathbb{Z} = \{ \ldots, -1, 0, 1, \ldots \}$
- Locations $\ell \in \mathbb{L} = \{ l, l_0, l_1, l_2, \ldots \}$
- Operations $\op \in \{ +, \ge \}$

We can combine these operations to form expressions $e$:

- Literals - $n$ or $b$
- Arithmetic - $e_1 \op e_2$
- Sequencing - $e_1; e_2$
- Assignments - $\ell := e$ or $\ell := !\ell$
- Conditionals - $\if e_1 \then e_2 \else e_3$`
- While loops - $\while e_1 \do e_2$
- No op - $\skip$

We will write $L_1$ for the set of all expressions.

#### Transition Systems

A transition system consists of a set $C$ of possible configurations for the system, and a binary relation $(\longrightarrow) \subseteq C \times C$. The notation $c \longrightarrow c'$ means that state $c$ can make a transition to state $c'$. We use a relation rather than a function here, since it may be the case that in a concurrent program, there are multiple possible successor states.

We will define a **store** $s$ to be a finite partial function from $\mathbb{L}$ to $\mathbb{Z}$, representing the variables which are currently assigned, e.g.
$$
\{ l_1 \mapsto 7, l_3 \mapsto 23 \}
$$

We take configurations to be pairs $\langle e, s \rangle$ of an expression $e$ and a store $s$ so that our transition relation will have the form
$$
\langle e, s \rangle \longrightarrow \langle e', s' \rangle
$$

Transitions are single steps in the evaluation of expressions:
$$
\begin{aligned}
                &\langle l := 2 + !l&, \{ l \mapsto 3 \} \rangle \\
\longrightarrow &\langle l := 2 + 3 &, \{ l \mapsto 3 \} \rangle \\
\longrightarrow &\langle l := 5     &, \{ l \mapsto 3 \} \rangle \\
\longrightarrow &\langle \text{skip}&, \{ l \mapsto 5 \} \rangle \\
\not \longrightarrow
\end{aligned}
$$

We want to continue with transitions until we get a **value** $v \in \mathbb{V} = \mathbb{Z} \cup \mathbb{B} \cup \{ \skip \}$, which is either an integer, a boolean, or `skip`. We say that $\langle e, s \rangle$ is **stuck** if $\langle e, s \rangle \not \longrightarrow$ but $e$ is not a value, for example $2 + \text{true}$.

##### Reduction rules

We define what is in the transition relation using **reduction rules**. The following rules are required to evaluate all arithmetic operations:
$$
\langle n_1 + n_2, s \rangle \longrightarrow \langle n, s \rangle \quad \{ n = n_1 + n_2 \} \\
\langle n_1 \ge n_2, s \rangle \longrightarrow \langle b, s \rangle \quad \{ b = (n_1 \ge n_2) \} \\
\frac{\langle e_1, s \rangle \longrightarrow \langle e_1', s' \rangle}{\langle e_1 \op e_2, s \rangle \longrightarrow \langle e_1' \op e_2, s' \rangle} \\
\frac{\langle e_2, s \rangle \longrightarrow \langle e_2', s' \rangle}{\langle v \op e_2, s \rangle \longrightarrow \langle v \op e_2', s' \rangle}
$$

More rules are required to reduce dereference and assignments, sequencing, conditionals, and loops - I haven't shown this here.

There are many possible ways to implement the semantics of a language:
- following the rules like by hand
- using an interpreter
- compiling to a different form of code, like assembly.

#### Determinacy

In order for our language L1 to be uniquely evaluable, we need the property of **determinacy**.

[Theorem] If $\langle e, s \rangle \longrightarrow \langle e_1, s_1 \rangle$ and $\langle e, s \rangle \longrightarrow \langle e_2, s_2 \rangle$ then $\langle e_1, s_1 \rangle = \langle e_2, s_2 \rangle$.

#### Runtime errors

There are two types of runtime error:

1. Trapped error - The execution halts immediately on error.
2. Untrapped error - May go unnoticed, and later cause arbitrary behaviour (like undefined behaviour in C).

If we have a precise definition of what will cause an untrapped error, then a language is **safe** if all its syntactically legal programs cannot cause such errors. This is usually desirable.

We would also like to have as few trapped errors as possible.

### Type Systems

In order to mitigate the problem of L1 getting stuck, we can employ a type system. A type system is used for:
- describing when programs make sense
- preventing certain kinds of errors
- structuring programs
- guiding language design

The types for L1 will be
- expression types - $T ::=$ `int | bool | unit`
- location types - $T_{loc} ::=$ `intref`

#### Typing relation

We will define a ternary relation $\Gamma \vdash e : T$, which means that under assumptions $\Gamma$ about the types within $e$, expression $e$ has type $T$. We let $\Gamma$ range over the set of finite partial functions from $\mathbb{L}$ to $T_{loc}$, and write it as $l_1:intref, \ldots, l_k:intref$.

Similar to the reduction rules, we will define this relation as follows:

$$
\Gamma \vdash n: \mathtt{int} \quad \{ n \in \mathbb{Z} \} \\
\Gamma \vdash b: \mathtt{bool} \quad \{ b \in \{ true, false \} \} \\
\frac{\Gamma \vdash e_1: \mathtt{int} \land \Gamma \vdash e_2: \mathtt{int}}{\Gamma \vdash e_1 + e_2: \mathtt{int}} \\
\frac{\Gamma \vdash e_1: \mathtt{int} \land \Gamma \vdash e_2: \mathtt{int}}{\Gamma \vdash e_1 \ge e_2: \mathtt{bool}} \\
\frac{\Gamma \vdash e_1: \mathtt{bool} \land \Gamma \vdash e_2: \mathtt{T} \land \Gamma \vdash e_3: \mathtt{T}}{\Gamma \vdash \if e_1 \then e_2 \else e_3: \mathtt{bool}} \\
$$

We can also specify typing rules for assignments, dereference, skip, sequencing, and while loops, left out here.

A configuration $\langle e, s \rangle$ is well-typed if $\Gamma \vdash e: T$ and $\domain(\Gamma) \subseteq \domain(s)$

#### Progress, preservation, safety

The progress and preservation theorems together give type safety.

##### Progress

[Theorem] If $\Gamma \vdash e : T$ and $\domain(\Gamma) \subseteq \domain(s)$ then either $e$ is a value or there exist $e'$, $s'$ such that $\langle e, s \rangle \longrightarrow \langle e', s' \rangle$.

This theorem says that the evaluation of a well typed expression will not get stuck.

##### Type Preservation

[Theorem] If $\Gamma \vdash e : T$ and $\domain(\Gamma) \subseteq \domain(s)$ and $\langle e, s \rangle \longrightarrow \langle e', s' \rangle$ then $\Gamma \vdash e': T$ and $\domain(\Gamma) \subseteq \domain(s')$.

This theorem says that if a well-typed program makes progress, it will always evaluate to give another well-typed program.

##### Safety

[Theorem] If $\Gamma \vdash e : T$ and $\domain(\Gamma) \subseteq \domain(s)$ and $\langle e, s \rangle \longrightarrow^* \langle e', s' \rangle$ then either $e'$ is a value or there exist $e'', s''$ such that $\langle e', s' \rangle \longrightarrow \langle e'', s'' \rangle$

This theorem is the combination of progress and preservation. It says that if we start with a well-typed program, for any sequence of reductions, it will never become stuck.

#### Type checking and type inference

Type checking is checking wether an expression $e$ is well-typed, given $\Gamma, T$. This is usually fairly straightforward.

Type inference is finding a type $T$ given an expression $e$ and type environment $\Gamma$. This is a more difficult problem, and usually requires a type inference algorithm. For L1 this is a more simple problem.

##### Decidability of type checking

[Theorem] Given $\Gamma, e, T$, we can decide $\Gamma \vdash e : T$.

##### Uniqueness of typing

[Theorem] If $\Gamma \vdash e : T$ and $\Gamma \vdash e: T'$ then $T = T'$.

##### Type inference

[Theorem] Given $\Gamma, e$, we can find $T$ such that $\Gamma \vdash e : T$, or show that there is none.

#### Type inference algorithm

The type inference algorithm for L1 is implemented in OCaml in the `L1.ml` file.

### Proofs

The main proof technique for proving facts about programming languages is induction. This might be:

- mathematical induction over natural numbers
- stuctural induction over terms of a grammar, such as the expressions in L1
- rule induction over all elements of a relation defined by rules, such as the transition relation, or the typing relation.

The underlying idea behind all of these is induction on trees.

We represent programs with an abstract syntax tree, based on the grammar that we defined for it. Each node in the tree has a corresponding **tree constructor**, which takes $k>0$ subtrees as arguments. For L1 these are `true`, `34`, `if _ then _ else _` etc.

#### Principle of structural induction

For any property $\Phi(e)$ of expressions $e$, in order to prove that $\forall e \in L_1 : \Phi(e)$, it's enough to prove that for each tree constructor $c$ taking $k \ge 0$ arguments, if $\Phi$ holds for the subtrees $e_1, \ldots, e_k$, then $\Phi$ holds for the tree $c(e_1, \ldots, e_k)$. That is,

$$
\forall c: \forall e_1, \ldots, e_k : (\Phi(e_1) \land \ldots \land \Phi(e_k)) \implies \Phi(c(e_1, \ldots, e_k))
$$

For $c = \while e_1 \do e_2$, this means proving that
$$
\forall e_1, e_2 : (\Phi(e_1) \land \Phi(e_2)) \implies \Phi(\while e_1 \do e_2))
$$

If the property we are trying to prove is about more than one expression, or includes a store variable, then we include all the requires variables for the property instead of just $e$, for example in the decidability property, we may have $\Phi(e, s, e', s', e'', s'')$.

##### Concrete rule instances

Before, when we spoke about reduction and typing rules, we only outlined the form they would take, without defining any concrete instances. We will do that now.

For each rule, we can construct the set of all concrete rule instances by taking all values of the metavariables that satisfy the rule. For example, for reducing the addition of two integers, we take all $n_0, n_1, n, s$ that satisfy $\langle n_0 + n_1, s \rangle \longrightarrow \langle n, s \rangle$, and say that each of those form an instance of that rule.

Now, a derivation of a transition, or typing judgement is a finite tree such that each step in the process is a concrete rule instance. Then, a transition or judgement is in their respective relation if and only if there is a derivation with it as the root node.

We refer to the set of concrete rule instances as $A$.

#### Principle of Rule Induction

For any property $\Phi(a)$ of elements $a$ of $A$, and any set of rules which define a subset $S_R$ of $A$, in order to prove that
$$
\forall a \in S_R: \Phi(a)
$$
it is sufficient to prove that $\{a | \Phi(a)\}$ is closed under the rules. i.e. for each concrete rule instance of the form
$$
\frac {h_1 \ldots h_k}{c}
$$
, if $\Phi(h_1) \land \ldots \land \Phi(h_k) \land h_1 \in S_R \land \ldots \land h_k \in S_R$, then $\Phi(c)$.
