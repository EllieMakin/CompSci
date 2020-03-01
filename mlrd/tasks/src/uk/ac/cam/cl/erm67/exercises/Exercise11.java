package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.social_networks.IExercise10;
import uk.ac.cam.cl.mlrd.exercises.social_networks.IExercise11;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Exercise11 implements IExercise11
{
    @Override
    public Map<Integer, Double> getNodeBetweenness(Path graphFile) throws IOException
    {
        IExercise10 ex10 = new Exercise10();
        Map<Integer, Set<Integer>> graph = ex10.loadGraph(graphFile);

        Set<Integer> V                      = graph.keySet();
        Queue<Integer> Q                    = new LinkedList<>();
        Stack<Integer> S                    = new Stack<>();
        Map<Integer, Integer> dist          = new HashMap<>();
        Map<Integer, List<Integer>> Pred    = new HashMap<>();
        Map<Integer, Double> sigma          = new HashMap<>();
        Map<Integer, Double> delta          = new HashMap<>();
        Map<Integer, Double> c_B            = new HashMap<>();
        for (int v : V)
        {
            c_B.put(v, (double) 0);
        }

        for (int s : V)
        {
            // single source shortest-paths problem

            //initialization
            for (int w : V)
            {
                Pred.put(w, new ArrayList<>());
            }
            for (int t : V)
            {
                dist.put(t, -1);
                sigma.put(t, (double) 0);
            }
            dist.put(s, 0);
            sigma.put(s, (double) 1);
            Q.add(s);

            while (!Q.isEmpty())
            {
                Integer v = Q.remove();
                S.push(v);
                for (int w : graph.get(v))
                {
                    // path discovery
                    if (dist.get(w) == -1)
                    {
                        dist.put(w, dist.get(v)+1);
                        Q.add(w);
                    }

                    // path counting
                    if (dist.get(w) == dist.get(v)+1)
                    {
                        sigma.put(w, sigma.get(w) + sigma.get(v));
                        Pred.get(w).add(v);
                    }
                }
            }

            // accumulation
            for (int v : V)
            {
                delta.put(v, (double) 0);
            }
            while (!S.empty())
            {
                int w = S.pop();
                for (int v : Pred.get(w))
                {
                    delta.put(v, delta.get(v) + sigma.get(v)/sigma.get(w) * (1+delta.get(w)));
                }
                if (w != s)
                {
                    c_B.put(w, c_B.get(w)+delta.get(w));
                }
            }
        }

        // halve scores to account for undirected graph
        for (int v : V){
            c_B.put(v, c_B.get(v)/2);
        }

        return c_B;
    }
}
