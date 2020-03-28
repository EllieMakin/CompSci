##### Ellie Makin (erm67)

# Algorithms example sheet 5

## Question 8

**In a directed graph with edge weights, give a formal proof of the triangle inequality**

$$
\text{
$d(u,v) \le d(u,w) + c(w \rightarrow v)$ for all vertices $u,v,w$ with $w\rightarrow v$
}
$$

**where $d(u,v)$ is the minimum weight of all paths from $u$ to $v$ (or $\infty$ if there are no such paths) and $c(w \rightarrow v)$ is the weight of edge $w \rightarrow v$. Make sure your proof covers the cases where no path exists.**

If no path from $u$ to $w$ exists, then $d(u, w) = \infty$, so the proposition holds.

Suppose a path from $u$ to $w$ exists, and the minimum path has weight $d(u,w)$.

If no edge $w \rightarrow v$ exists, then $c(w \rightarrow v) = \infty$, so the proposition holds.

If an edge $w \rightarrow v$ exists, then a path from $u$ to $v$ exists, namely the one through $w$, which has weight $d(u, w) + c(w \rightarrow v)$. Thus, the minimum path from $u$ to $v$ is at most this large, so we have $d(u, v) \le d(u, w) + c(w \rightarrow v)$, as required.

## Question 15

**An engineer friend tells you there is a simpler way to reweight edges than the method used in Johnson's algorithm. Let $w*$ be the minimum weight of all edges in the graph, and just define $w'(u \rightarrow v) = w(u \rightarrow v) - w*$ for all edges $𝑢 \rightarrow v$. What is wrong with your friend’s idea?**
