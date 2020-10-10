package uk.ac.cam.erm67.Algorithms.Tick3;

import uk.ac.cam.cl.tester.Algorithms.LabelledGraph;
import uk.ac.cam.cl.tester.Algorithms.MaxFlow;

import java.io.IOException;

public class Tester
{
    public static void main(String[] args) throws IOException
    {
        MaxFlow m = new MaxFlowFinder();
        LabelledGraph g = new LabelledGraph("https://www.cl.cam.ac.uk/teaching/1920/Algorithms/ticks/res/flownetwork_09.csv");
        m.maximize(g, 0, 49);
        System.out.println(m.cut());
        System.out.println(m.flows());
        System.out.println(m.value());
    }
}
