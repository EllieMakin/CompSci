package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.IExercise5;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Sentiment;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Exercise5 implements IExercise5 {
    private int nFolds = 10;

    @Override
    public List<Map<Path, Sentiment>> splitCVRandom(Map<Path, Sentiment> dataSet, int seed) {
        List<Map<Path, Sentiment>> folds = new ArrayList<Map<Path, Sentiment>>();
        int targetSize = dataSet.size()/nFolds;

        for (int jFold = 0; jFold < nFolds; jFold++) {
            folds.add(new HashMap<Path, Sentiment>());
        }

        Random generator = new Random(seed);

        for (Map.Entry<Path, Sentiment> entry : dataSet.entrySet()) {
            int chosenFold = generator.nextInt(nFolds);
            while (folds.get(chosenFold).size() >= targetSize)
            {
                chosenFold = (chosenFold + 1) % nFolds;
            }
            folds.get(chosenFold).put(entry.getKey(), entry.getValue());
        }

        return folds;
    }

    @Override
    public List<Map<Path, Sentiment>> splitCVStratifiedRandom(Map<Path, Sentiment> dataSet, int seed) {
        List<Map<Path, Sentiment>> folds = new ArrayList<Map<Path, Sentiment>>();
        int targetSize = dataSet.size()/nFolds/2;
        int[] positiveCounts = new int[10];
        int[] negativeCounts = new int[10];

        for (int jFold = 0; jFold < nFolds; jFold++) {
            folds.add(new HashMap<Path, Sentiment>());
        }

        Random generator = new Random(seed);

        for (Map.Entry<Path, Sentiment> entry : dataSet.entrySet()) {
            int chosenFold = generator.nextInt(nFolds);
            if (entry.getValue() == Sentiment.POSITIVE)
            {
                while (positiveCounts[chosenFold] >= targetSize)
                {
                    chosenFold = (chosenFold + 1) % nFolds;
                }
                positiveCounts[chosenFold]++;
            }
            else if (entry.getValue() == Sentiment.NEGATIVE)
            {
                while (negativeCounts[chosenFold] >= targetSize)
                {
                    chosenFold = (chosenFold + 1) % nFolds;
                }
                negativeCounts[chosenFold]++;
            }
            folds.get(chosenFold).put(entry.getKey(), entry.getValue());
        }

        return folds;
    }

    @Override
    public double[] crossValidate(List<Map<Path, Sentiment>> folds) throws IOException
    {
        Exercise1 ex1 = new Exercise1();
        Exercise2 ex2 = new Exercise2();
        double[] scores = new double[nFolds];

        for (int jTestFold = 0; jTestFold < nFolds; jTestFold++)
        {
            Map<Path, Sentiment> trueSentiments = new HashMap<Path, Sentiment>();
            Map<Path, Sentiment> trainingSet = new HashMap<Path, Sentiment>();
            Set<Path> testSet = new HashSet<Path>();
            for (Map<Path, Sentiment> fold : folds)
            {
                if (fold != folds.get(jTestFold))
                {
                    trainingSet.putAll(fold);
                }
                else
                {
                    testSet.addAll(fold.keySet());
                    trueSentiments.putAll(fold);
                }
            }
            scores[jTestFold] = ex1.calculateAccuracy(
                    trueSentiments,
                    ex2.naiveBayes(
                            testSet,
                            ex2.calculateSmoothedLogProbs(trainingSet),
                            ex2.calculateClassProbabilities(trainingSet)
                    )
            );
        }
        return scores;
    }

    @Override
    public double cvAccuracy(double[] scores)
    {
        double sum = 0;
        for (int jScore = 0; jScore < scores.length; jScore++) {
            sum += scores[jScore];
        }
        return sum / scores.length;
    }

    @Override
    public double cvVariance(double[] scores)
    {
        double mu = cvAccuracy(scores);
        double sum = 0;
        for (int jScore = 0; jScore < scores.length; jScore++) {
            sum += (scores[jScore]-mu)*(scores[jScore]-mu);
        }
        return sum / scores.length;
    }
}
