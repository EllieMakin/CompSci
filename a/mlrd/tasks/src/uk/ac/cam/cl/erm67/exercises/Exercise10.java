package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.social_networks.IExercise10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Exercise10 implements IExercise10
{
    @Override
    public Map<Integer, Set<Integer>> loadGraph(Path graphFile) throws IOException
    {
        HashMap<Integer, Set<Integer>> adjacencies = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(graphFile.toString()));

        String line = reader.readLine();

        int prevNode = -1;

        while (line != null) {
            String[] splitLine = line.split(" ");
            int node1 = Integer.parseInt(splitLine[0]);
            int node2 = Integer.parseInt(splitLine[1]);

            adjacencies.putIfAbsent(node1, new HashSet<Integer>());
            adjacencies.putIfAbsent(node2, new HashSet<Integer>());

            adjacencies.get(node1).add(node2);
            adjacencies.get(node2).add(node1);

            line = reader.readLine();
        }

        reader.close();

        return adjacencies;
    }

    @Override
    public Map<Integer, Integer> getConnectivities(Map<Integer, Set<Integer>> graph)
    {
        HashMap<Integer, Integer> degrees = new HashMap<>();

        for (Map.Entry<Integer, Set<Integer>> entry : graph.entrySet())
        {
            degrees.put(entry.getKey(), entry.getValue().size());
        }

        return degrees;
    }

    @Override
    public int getDiameter(Map<Integer, Set<Integer>> graph)
    {
        int maxDistance = 0;

        // do Dijkstra's algorithm on each node.
        for (int node1 = 0; node1 < graph.size(); node1++)
        {
            int d = dijkstra(graph, node1);
            if (d > maxDistance){
                maxDistance = d;
            }
        }

        return maxDistance;
    }

    int dijkstra(Map<Integer, Set<Integer>> graph, int node1)
    {
        int nNodes = Collections.max(graph.keySet())+1;
        boolean[] visited = new boolean[nNodes];
        int[] distances = new int[nNodes];
        Arrays.fill(visited, false);
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[node1] = 0;
        int currentNode = node1;
        boolean allVisited = false;
        int maxDistance = 0;

        while (!allVisited)
        {
            for (int neighbour : graph.getOrDefault(currentNode, new HashSet<>()))
            {
                if (!visited[neighbour]) {
                    distances[neighbour] = Math.min(distances[currentNode] + 1, distances[neighbour]);
                }
            }

            visited[currentNode] = true;
            if (distances[currentNode] > maxDistance)
            {
                maxDistance = distances[currentNode];
            }

            int newNode = currentNode;
            int minD = Integer.MAX_VALUE;
            for (int jNode = 0; jNode < nNodes; jNode++)
            {
                if (!visited[jNode] && distances[jNode] < minD) {
                    newNode = jNode;
                    minD = distances[jNode];
                }
            }

            if (newNode == currentNode) {
                allVisited = true;
            }
            else {
                currentNode = newNode;
            }
        }

        return maxDistance;
    }
}
