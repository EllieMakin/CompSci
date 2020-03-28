# Graphs

## Definitions

A graph is a collection of vertices, with edges between them. We write a graph as $g=(V,E)$, where $V$ is the set of vertices, and $E$ is the set of edges.

A graph may be directed or undirected.
- in a directed graph, $v_1 \rightarrow v_2$ is the edge from $v_1$ to $v_2$.
- in an undirected graph, $v_1 \leftrightarrow v_2$ is the edge between $v_1$ and $v_2$.

The neighbours of a vertex $v$ are the vertices you reach by following an edge from $v$.
- in a directed graph, $\text{neighbours}(v) = \{ w \in V : (v \rightarrow w) \in E \}$
- in an undirected graph, $\text{neighbours}(v) = \{ w \in V : (v \leftrightarrow w) \in E \}$

A path is a sequence of vertices connected by edges.
- in a directed graph, $v_0 \rightarrow v_1 \rightarrow \cdots \rightarrow v_k$, or $v_0 \rightsquigarrow v_k$
- in an undirected graph, $v_0 \leftrightarrow v_1 \leftrightarrow \cdots \leftrightarrow v_k$, or $v_0 \leftrightsquigarrow v_k$

A cycle is a path from a vertex to itself, i.e. where $v_0 = v_k$.

The density of a graph is defined as $\frac{|E|}{|V|^2}$, commonly written as just $\frac{E}{V^2}$.

## Algorithms

Two standard ways to store graphs in computer code are as an array of *adjacency lists*, or as an *adjacency matrix*. The first is a list of all the vertices connected to a given vertex, and takes up $O(V+E)$ space. The second is a boolean matrix with a $1$ if vertices $i$ and $j$ are connected, and takes up $O(V^2)$ space.

### Depth-first search (DFS)

With DFS, we visit each vertex in a graph, prioritizing neighbours of the vertex we are currently at, rather than neighbours of previous vertices.

We can do this either using recursion, or by storing the vertices we want to visit on a stack. In either case, we must keep track of vertices that we have visited already, so that we do not become stuck in a loop.

The complexity of this alorithm is $O(V+E)$, since each vertex is visited once, and each edge must be checked the same amount of times to see if the vertex there has been visited yet.

```python
def dfs_recurse(g, s):
    for v in g.vertices:
        v.visited = False
    visit(s)

def visit(v):
    # Do some work here
    v.visited = True
    for w in v.neighbours:
        if not w.visited:
            visit(w)
```

```python
def dfs(g, s):
    for v in g.vertices:
        v.seen = False
    toexplore = Stack([s])
    s.seen = True

    while not toexplore.is_empty():
        v = toexplore.popright()
        for w in v.neighbours:
            if not w.seen:
                toexplore.pushright(w)
                w.seen = True
```

### Breadth-first search (BFS)

Breadth first search is another form of graph traversal, but now we prioritise vertices which were closer to the starting vertex.

We can accomplish this by using a queue to store unvisited vertices, rather than a stack.

BFS has time complexity $O(V+E)$, for the same reasons as DFS.

```python
def bfs(g, s):
    for v in g.vertices:
        v.seen = False
    toexplore = Queue([s])
    s.seen = True

    while not toexplore.is_empty():
        v = toexplore.popright()
        for w in v.neighbours:
            if not w.seen:
                toexplore.pushleft(w)
                w.seen = True
```

An advantage of BFS is that we can naturally use it to find the shortest path between two nodes, as long as each edge has the same weight, simply by keeping track of which node we came from when we get to each vertex. This works because the queue means that we alway visit closer vertices first.

```python
def bfs_path(g, s, t):
    for v in g.vertices:
        v.seen = False
        v.come_from = None
    s.seen = True
    toexplore = Queue([s])

    while not toexplore.is_empty():
        v = toexplore.popright()
        for w in v.neighbours:
            if not w.seen:
                toexplore.pushleft(w)
                w.seen = True
                w.come_from = v

    if t.come_from is None:
        return None
    else:
        path = [t]
        while path[0].come_from != s:
            path.insert(0, path[0].come_from)  # i.e. prepend
        path.insert(0,s)
        return path
```

### Dijkstra's algorithm

Dijkstra's algorithm solves the problem of the shortest path when the graph has edge weights - with no edge weights this can simply be done by BFS. Here, we prioritise vertices with the shortest total distance from the start node, this time taking into account the edge weights. The edge weights must all be $\ge 0$, or the algorithm will fail.

This is most naturally done using a priority queue. If the priority queue is implemented as a fibonacci heap, then we can achieve $O(E + V \lg V)$ complexity, by noting that `popmin()` is called at most once per vertex, and the inner `for` loop is run at most once per edge.

```python
function dijkstra(graph, source):
    # Initialization
    dist[source] = 0
    toExplore = PriorityQueue()

    for v in graph.vertices:
        if v != source:
            dist[v] = INFINITY
            pred[v] = UNDEFINED
        toExplore.add_with_priority(v, dist[v])

    # Main loop
    while not toExplore.isEmpty():
        u = toExplore.extract_min()
        for each neighbor v of u:
            altDist = dist[u] + length(u, v)
            if altDist < dist[v]:
                dist[v] = altDist
                prev[v] = u
                toExplore.decrease_priority(v, altDist)

    return dist, prev
```

#### Proof

Proof of Dijkstra's algorithm is constructed by induction on the number of visited nodes.

A good proof of Dijkstra's algorithm has two key ingredients:

- It needs to establish that when a vertex is popped from the priority queue, its computed distance is equal to the true distance.
- It needs to make use of the fact that all edge weights are $\ge 0$, otherwise the algorithm will fail.

**Invariant hypothesis:** For each visited node $v$, $\text{dist}[v]$ is considered the shortest distance from $s$ to $v$; and for each unvisited node $u$, $\text{dist}[u]$ is assumed the shortest distance when traveling via visited nodes only, from $s$ to u. This assumption is only considered if a path exists, otherwise the distance is set to infinity. (Note : we do not assume $\text{dist}[u]$ is the actual shortest distance for unvisited nodes)

The base case is when there is just one visited node, namely the initial node $s$, in which case the hypothesis is trivial.

Otherwise, assume the hypothesis for $n-1$ visited nodes. In which case, we choose an edge $v \rightarrow u$ where $u$ has the least $\text{dist}[u]$ of any unvisited nodes and the edge $v \rightarrow u$ is such that $\text{dist}[u] = \text{dist}[v] + \text{length}[v,u]$.

$\text{dist}[u]$ is considered to be the shortest distance from $s$ to $u$ because if there were a shorter path, and if $w$ was the first unvisited node on that path then $\text{dist}[w] < \text{dist}[u]$, since all edge weights are $\ge 0$, which creates a contradiction since we chose $u$ for the smallest $\text{dist}[u]$ of unvisited nodes.

Similarly if there were a shorter path to $u$ without using unvisited nodes, and if the second last node on that path were $w$, then we would have had $\text{dist}[u] = \text{dist}[w] + \text{length}[w,u] \implies \text{dist}[w] < \text{dist}[u]$ since all edge weights are $\ge 0$, also a contradiction.

After processing $u$ it will still be true that for each unvisited node $w$, $\text{dist}[w]$ will be the shortest distance from source to $w$ using visited nodes only, because if there were a shorter path that doesn't go by $u$ we would have found it previously, and if there were a shorter path using $u$ we would have updated it when processing $u$.

The algorithm is bound to terminate, since the vertices are only added to the queue once: at the start.

### Bellman-Ford algorithm

The Bellman-Ford algorithm is again a shortest-path algorithm, but this time the graph may have negative edge-weights as well. This introduction complicates things significantly, since now a graph like the following has a cycle ($b \rightarrow c \rightarrow d \rightarrow b$) which can be traversed infinitely many times to reduce the path weight:

![negativeWeightCycle](notesImages/negativeWeightCycle.png)

This is called a *negative weight cycle*, and if there is a path between two nodes that includes such a cycle, then the minimum path weight between those nodes is $-\infty$.

The Bellman-Ford algorithm gives the following behaviour:

Given a directed graph where each edge is labelled with a weight, and a start vertex $s$,

- if the graph contains no negative-weight cycles reachable from $s$ then for every vertex $v$ compute the minimum weight from $s$ to $v$;
- otherwise report that there is a negative weight cycle reachable from $s$.

```python
def bf(g, s):
    # initialization
    for v in g.vertices:
        minweight[v] = float('inf')
    minweight[s] = 0

    # relax all the edges
    for i in range(len(g.vertices) - 1):
        for each edge (u, v) with weight w in g.edges:
            minweight[v] = min(minweight[u] + c, minweight[v])

    # check for negative weight cycles
    for each edge (u, v) with weight w in g.edges:
        if minweight[u] + c < minweight[v]:
            raise ValueError("Negative-weight cycle detected")
```

The running time is $O(V \cdot E)$, since we iterate over each edge $V$ times.

#### Proof

We want to prove that the algorithm gives the behaviour described above. In this proof we perform induction on the successive nodes in a shortest path.

Pick any vertex $v$, and consider a minimal-weight path from $s$ to $v$:

$$
s = u_0 \rightarrow u_1 \rightarrow \cdots \rightarrow u_k = v
$$

Initially $\text{minweight}[u_0] = w(s) = 0$, which is correct. After iteration $i$, $\text{minweight}[u_i]$ must be correct, since it is calculated based on $u_{i-1}$, which is correct by inductive assumption.

If there are no negative weight cycles, the most nodes that a minimal-weight path can contain is $|V|$, so after $|V|-1$ iterations, $\text{minweight}[v]$ is guaranteed to be correct for any choice of $v$.

To prove that the exception will be thrown if there is a negative weight cycle present, assume that there is such a cycle, but that the exception was not thrown. We label the nodes in the cycle

$$
v_0 \rightarrow v_1 \rightarrow \cdots \rightarrow v_k \rightarrow v_0
$$

Since the algorithm terminated without throwing an exception, then all these edges pass the test:

$$
\text{minweight}[v_0] + \text{weight}(v_0 \rightarrow v_1) \ge \text{minweight}[v_1] \\
\text{minweight}[v_1] + \text{weight}(v_1 \rightarrow v_2) \ge \text{minweight}[v_2] \\
\vdots \\
\text{minweight}[v_k] + \text{weight}(v_k \rightarrow v_0) \ge \text{minweight}[v_0] \\
$$

Putting these equations together,

$$
\text{minweight}[v_0] + \sum_{i=0}^k\text{weight}(v_i \rightarrow v_{i+1}) \ge \text{minweight}[v_0]
$$

so the cycle has total weight $\ge 0$, which contradicts the assumption that this was a negative weight cycle, so the exception must be thrown if a negative weight cycle is present.

### Johnson's algorithm

## Bellman Equation
