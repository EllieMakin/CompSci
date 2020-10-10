class Graph:
    def __init__(self, neighbours):
        self.neighbours = neighbours
        self.vertices = list(range(len(neighbours)))

    def neighbours(self, node):
        return self.neighbours[node]

def findOrigin(g):
    visited = [False for v in g.vertices]
    toVisit = []
    nUnvisited = len(g.vertices)

    for s in g.vertices:
        if visited[s] == False:
            toVisit.append(s)
            while not len(toVisit) == 0:
                v = toVisit.pop()
                visited[v] = True
                nUnvisited -= 1
                for w in g.neighbours[v]:
                    if not visited[w]:
                        toVisit.append(w)
            if nUnvisited == 0:
                return s

def allOrigins(g):
    origins = []
    visited = [False for v in g.vertices]
    toVisit = []
    nUnvisited = len(g.vertices)

    for s in g.vertices:
        if visited[s] == False:
            toVisit.append(s)
            while not len(toVisit) == 0:
                v = toVisit.pop()
                visited[v] = True
                nUnvisited -= 1
                for w in g.neighbours[v]:
                    if not visited[w]:
                        toVisit.append(w)
            if nUnvisited == 0:
                visited = [False for v in g.vertices]
                nUnvisited = len(g.vertices)
                toVisit.append(s)
                while not len(toVisit) == 0:
                    v = toVisit.pop()
                    visited[v] = True
                    nUnvisited -= 1
                    for w in g.neighbours[v]:
                        if not visited[w]:
                            toVisit.append(w)
                if nUnvisited == 0:
                    origins

g = Graph([
    [],
    [2, 0],
    [3],
    [1]
])

print(findOrigin(g))
