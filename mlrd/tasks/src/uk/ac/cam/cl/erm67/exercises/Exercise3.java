package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Sentiment;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Tokenizer;
import uk.ac.cam.cl.mlrd.utils.BestFit;
import uk.ac.cam.cl.mlrd.utils.BestFit.*;
import uk.ac.cam.cl.mlrd.utils.ChartPlotter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Exercise3
{
    public ArrayList<Path> loadReviews(Path dataDirectory) throws IOException
    {
        ArrayList<Path> dataSet = new ArrayList<Path>();

        try (DirectoryStream<Path> files = Files.newDirectoryStream(dataDirectory))
        {
            for (Path item : files) {
                dataSet.add(item);
            }
        }
        catch (IOException e)
        {
            throw new IOException("Can't read the reviews.", e);
        }

        return dataSet;
    }

    public HashMap<String, Integer> findFrequencies(ArrayList<Path> dataSet) throws IOException
    {
        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();

        for (Path entry : dataSet)
        {
            List<String> tokens = Tokenizer.tokenize(entry);
            for (String s : tokens) {
                wordCounts.put(s, wordCounts.getOrDefault(s, 0) + 1);
            }
        }

        return wordCounts;
    }

    public ArrayList<Point> getPoints(HashMap<String, Integer> wordCounts)
    {
        ArrayList<Integer> justCounts = new ArrayList<Integer>(wordCounts.values());
        justCounts.sort(Collections.reverseOrder());

        ArrayList<Point> pointsList = new ArrayList<Point>();
        for (int jCount = 0; jCount < 10000; jCount++) {
            pointsList.add(new Point(jCount, justCounts.get(jCount)));
        }

        return pointsList;
    }

    public ArrayList<Integer> getRankings(HashMap<String, Integer> wordCounts, String[] wordList) {
        ArrayList<Integer> rankings = new ArrayList<Integer>();
        ArrayList<Point> pointsList = new ArrayList<Point>();

        ArrayList<Integer> justCounts = new ArrayList<Integer>(wordCounts.values());
        justCounts.sort(Collections.reverseOrder());

        for (String s : wordList)
        {
            int ranking = justCounts.indexOf(wordCounts.get(s));
            rankings.add(ranking);
            System.out.println(s + ": r=" + ranking + ", f=" + wordCounts.get(s));

            pointsList.add(new Point(ranking, wordCounts.get(s)));
        }
        ChartPlotter.plotLines(getPoints(wordCounts), pointsList);

        return rankings;
    }

    public ArrayList<Point> getLogPoints(HashMap<String, Integer> wordCounts)
    {
        ArrayList<Integer> justCounts = new ArrayList<Integer>(wordCounts.values());
        justCounts.sort(Collections.reverseOrder());

        ArrayList<Point> logPointsList = new ArrayList<Point>();
        for (int jCount = 0; jCount < 10000; jCount++)
        {
            logPointsList.add(new Point(Math.log(jCount+1), Math.log(justCounts.get(jCount))));
        }

        return logPointsList;
    }

    public HashMap<Point, Double> getWeightedPoints(HashMap<String, Integer> wordCounts)
    {
        ArrayList<Integer> justCounts = new ArrayList<Integer>(wordCounts.values());
        justCounts.sort(Collections.reverseOrder());

        HashMap<Point, Double> weightedPoints = new HashMap<Point, Double>();
        for (int jCount = 0; jCount < 10000; jCount++)
        {
            weightedPoints.put(new Point(Math.log(jCount+1), Math.log(justCounts.get(jCount))), (double) justCounts.get(jCount));
        }

        return weightedPoints;
    }

    public ArrayList<Point> getPointsFromLine(Line line)
    {
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(0, line.yIntercept));
        points.add(new Point(-line.yIntercept/line.gradient, 0));
        return points;
    }

    public ArrayList<Point> getUniqueWordPoints(ArrayList<Path> dataSet) throws IOException
    {
        ArrayList<Point> dataPoints = new ArrayList<Point>();
        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
        int powerOfTwo = 0;
        int nTokens = 0;

        for (Path entry : dataSet)
        {
            List<String> tokens = Tokenizer.tokenize(entry);
            for (String s : tokens) {
                wordCounts.put(s, wordCounts.getOrDefault(s, 0) + 1);
                nTokens++;
                if (nTokens == Math.pow(2, powerOfTwo)) {
                    powerOfTwo++;
                    dataPoints.add(new Point(Math.log(nTokens), Math.log(wordCounts.size())));
                }
            }
        }

        dataPoints.add(new Point(Math.log(nTokens), Math.log(wordCounts.size())));
        return dataPoints;
    }
}
