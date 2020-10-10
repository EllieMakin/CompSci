package uk.ac.cam.cl.tester.Algorithms;

public interface MaxFlow {
    void maximize(LabelledGraph g, int s, int t);
    int value();
    Iterable<LabelledGraph.Edge> flows();
    java.util.Set<Integer> cut();
}
