##### Ellie Makin

# Supervision questions: set 4.

## Graph algorithms

**There is a method of calculating the diameter of an acyclic graph with undirected, unweighted edges which only requires two breadth-first searches. What is it? Give an example of a graph with cycles where this approach doesn't work.**

## Betweenness centrality and Newman-Girvan method examples

**The following 5-node graphs are described by their edge lists.**

1. `(1 2) (2 3) (3 4) (4 5) (1 5)`
2. `(1 2) (1 3) (1 4) (1 5)`
3. `(1 2) (1 3) (1 4) (1 5) (2 3)`
4. `(1 2) (2 3) (2 4) (3 4) (4 5)`
5. `(1 2) (2 3) (3 4) (4 5) (3 5)`
6. `(1 2) (2 3) (3 4) (2 4) (2 5)`
7. `(1 2) (2 3) (2 4) (3 4) (1 4) (1 5)`

**For each graph:**

**What is the betweenness centrality of each node?**

| Graph \ Node | 1 | 2 | 3 | 4 | 5 |
| ------------ | - | - | - | - | - |
| 1            | 1 | 1 | 1 | 1 | 1 |
| 2            | 6 | 0 | 0 | 0 | 0 |
| 3            | 5 | 0 | 0 | 0 | 0 |
| 4            | 0 | 3 | 0 | 3 | 0 |
| 5            | 0 | 3 | 4 | 0 | 0 |
| 6            | 0 | 5 | 0 | 0 | 0 |
| 7            | 3 | 1 | 0 | 1 | 0 |

Which edge or edges have the highest betweenness centrality?

| Graph | Edge(s)      |
| ----- | ------------ |
| 1     | All equal    |
| 2     | All equal    |
| 3     | (1,5), (1,4) |
| 4     | (1,2), (4,5) |
| 5     | (2, 3)       |
| 6     | (1,2), (2,5) |
| 7     | (1, 5)       |

What graphs do you get if you remove the highest betweenness centrality edges (removing multiple edges in the case of ties)?

| Graph | Edges                             |
| ----- | --------------------------------- |
| 1     | No edges                          |
| 2     | No edges                          |
| 3     | (1,2), (1,3), (2,3)               |
| 4     | (2,3), (2,4), (3,4)               |
| 5     | (1,2), (3,4), (3,5), (4,5)        |
| 6     | (2,3), (2,4), (3,4)               |
| 7     | (1,2), (1,4), (2,3), (2,4), (3,4) |

Try working the examples out manually (but check your results using your code).

## Random graphs and metrics

**In the course, we have discussed a number of attributes of graphs including:**

1. **(distribution of) degrees of nodes**
2. **number of connected components**
3. **(distribution of) shortest paths**
4. **extent of clustering. We have not formalized this concept (unless you did the starred tick and looked at modularity), but an informal notion is all that is needed here.**

**The additional notes in random-graphs.html supplement the brief discussion of random networks in Session 12.**

**Consider the following types of graph:**

1. **Erdős–Rényi**
2. **Watts-Strogatz**
3. **A collaboration network (e.g., the AMS-derived network discussed in Session 12)**

**How do you expect they will vary with respect to the four attributes listed above? (Proofs are not expected!)**

Erdős-Rényi:
- Small variance in degrees of nodes
- Several connected components
- Shortest paths will tend to be quite long
- Not much clustering.

Watts-Strogatz:
- Nodes all with similar degree
- Few connected components
- Shortest paths will be quite short
- Not much clustering

Collaboration network:
- Large variance in degree
- Many connected components
- Shortest paths may be quite long
- High clustering
