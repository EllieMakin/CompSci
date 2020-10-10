package uk.ac.cam.erm67.Algorithms.Tick3;

import uk.ac.cam.cl.tester.Algorithms.LabelledGraph;
import uk.ac.cam.cl.tester.Algorithms.MaxFlow;

import java.util.*;

public class MaxFlowFinder implements MaxFlow {

    private LabelledGraph g;
    private int source;
    private int[][] flow;
    private boolean[] cut;
    private int iter = 0;
    private int iterLimit = 10000;

    //
    public int value() {
        int v = 0;
        for (int j=0; j<g.numVertices(); j++) v += flow[source][j];
        for (int i=0; i<g.numVertices(); i++) v -= flow[i][source];
        return v;
    }


    public Iterable<LabelledGraph.Edge> flows() {
        List<LabelledGraph.Edge> res = new ArrayList<LabelledGraph.Edge>();
        for (int i=0; i<g.numVertices(); i++) for (int j=0; j<g.numVertices(); j++) if (flow[i][j]>0)
            res.add(new LabelledGraph.Edge(i, j, flow[i][j]));
        return res;
    }

    public Set<Integer> cut() {
        Set<Integer> res = new HashSet<Integer>();
        for (int i=0; i<cut.length; i++) if (cut[i]) res.add(i);
        return res;
    }

    public void maximize(LabelledGraph g, int s, int t) {
        this.g = g;
        this.source = source;
        int n = g.numVertices();
        this.flow = new int[n][n];
        this.cut = new boolean[n];
        // TODO: run Ford-Fulkerson to fill in flow[][] and cut[]
        // using your getAugmentingPath function.
        List<Integer> augmentingPath = getAugmentingPath(s, t);

        while (augmentingPath != null)
        {
            int dF = Integer.MAX_VALUE;
            for (int jNode = 0; jNode < augmentingPath.size()-1; jNode++)
            {
                int from = augmentingPath.get(jNode);
                int to = augmentingPath.get(jNode+1);
                if (g.capacity(from, to) == 0)
                {
                    dF = Math.min(flow[to][from], dF);
                }
                else
                {
                    dF = Math.min(g.capacity(from, to)-flow[from][to], dF);
                }
            }

            for (int jNode = 0; jNode < augmentingPath.size()-1; jNode++)
            {
                int from = augmentingPath.get(jNode);
                int to = augmentingPath.get(jNode+1);
                if (g.capacity(from, to) == 0)
                {
                    flow[to][from] -= dF;
                }
                else
                {
                    flow[from][to] += dF;
                }
            }

            augmentingPath = getAugmentingPath(s, t);
        }
    }

    // TODO: Define a function getAugmentingPath(int src, int dst)
    // that returns an augmenting path from src to dst,
    // which it finds using BFS.
    List<Integer> getAugmentingPath(int src, int dst)
    {
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        Integer[] pred = new Integer[g.numVertices()];
        Arrays.fill(cut, false);

        while (!q.isEmpty())
        {
            int cur = q.remove();
            cut[cur] = true;
            for (LabelledGraph.Edge e : g.edges())
            {
                if (e.from == cur) {
                    if (pred[e.to] == null && e.to != src && e.label > flow[e.from][e.to]) {
                        System.out.println("first");
                        pred[e.to] = cur;
                        q.add(e.to);
                    }
                }
                if (e.to == cur) {
                    if (pred[e.from] == null && e.from != src && 0 < flow[e.from][e.to]) {
                        System.out.println("second");
                        pred[e.from] = cur;
                        q.add(e.from);
                    }
                }
            }
        }

        if (pred[dst] == null) {
            return null;
        }
        else {
            LinkedList<Integer> path = new LinkedList<>();
            int cur = dst;
            path.addFirst(cur);
            while (cur != src) {
                cur = pred[cur];
                path.addFirst(cur);
            }
            return path;
        }
    }
}
