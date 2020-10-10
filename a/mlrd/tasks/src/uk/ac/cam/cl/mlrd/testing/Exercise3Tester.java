package uk.ac.cam.cl.mlrd.testing;

import uk.ac.cam.cl.erm67.exercises.Exercise3;
import uk.ac.cam.cl.mlrd.utils.BestFit.*;
import uk.ac.cam.cl.mlrd.utils.ChartPlotter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static uk.ac.cam.cl.mlrd.utils.BestFit.leastSquares;

public class Exercise3Tester {

    static final Path dataDirectory = Paths.get("data/large_dataset");
    static final String[] wordPredictions = {
            "excellent",
            "dry",
            "annoying",
            "spoiled",
            "unique",
            "uncomfortable",
            "mistaken",
            "profanity",
            "popcorn",
            "better"
    };

    public static void main(String[] args) throws IOException
    {
        Exercise3 ex3 = new Exercise3();
        ArrayList<Path> dataSet = ex3.loadReviews(dataDirectory);
        HashMap<String, Integer> wordCounts = ex3.findFrequencies(dataSet);
        //ArrayList<Point> pointsList = ex3.getPoints(wordCounts);
        //ChartPlotter.plotLines(pointsList);

        ArrayList<Integer> wordRankings = ex3.getRankings(wordCounts, wordPredictions);

        ArrayList<Point> logPointsList = ex3.getLogPoints(wordCounts);
        ChartPlotter.plotLines(logPointsList);

        HashMap<Point, Double> weightedPoints = ex3.getWeightedPoints(wordCounts);
        Line bestFitLine = leastSquares(weightedPoints);
        ArrayList<Point> bestFitPoints = ex3.getPointsFromLine(bestFitLine);
        ChartPlotter.plotLines(logPointsList, bestFitPoints);
        System.out.println(bestFitLine);

        ArrayList<Point> uniqueWordsPoints = ex3.getUniqueWordPoints(dataSet);
        ChartPlotter.plotLines(uniqueWordsPoints);
    }
}
