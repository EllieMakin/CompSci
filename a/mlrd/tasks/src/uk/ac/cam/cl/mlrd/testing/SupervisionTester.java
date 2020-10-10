package uk.ac.cam.cl.mlrd.testing;

import uk.ac.cam.cl.erm67.exercises.Exercise1;
import uk.ac.cam.cl.erm67.exercises.Exercise2;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.DataPreparation1;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.IExercise1;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.IExercise2;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Sentiment;
import uk.ac.cam.cl.mlrd.utils.DataSplit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;

public class SupervisionTester
{
    static final Path reviewPath = Paths.get("data/supervision_review.txt");
    static final Path dataDirectory = Paths.get("data/sentiment_dataset");

    public static void main(String[] args) throws IOException {
        Path lexiconFile = Paths.get("data/sentiment_lexicon");
        HashSet<Path> reviewSet = new HashSet<Path>();
        reviewSet.add(reviewPath);

        IExercise1 ex1 = new Exercise1();

        Map<Path, Sentiment> predictedSentiments = ex1.simpleClassifier(reviewSet, lexiconFile);
		System.out.println("Task 1 classifier prediction:");
		System.out.println(predictedSentiments);
		System.out.println();

        IExercise2 ex2 = new Exercise2();


        Path sentimentFile = dataDirectory.resolve("review_sentiment");
        // Loading the dataset.
        Map<Path, Sentiment> dataSet = DataPreparation1.loadSentimentDataset(dataDirectory.resolve("reviews"),
                sentimentFile);
        DataSplit<Sentiment> split = new DataSplit<Sentiment>(dataSet, 0);

        Map<Sentiment, Double> classProbabilities = ex2.calculateClassProbabilities(split.trainingSet);

        // With smoothing
        Map<String, Map<Sentiment, Double>> smoothedLogProbs = ex2
                .calculateSmoothedLogProbs(split.trainingSet);

        Map<Path, Sentiment> smoothedNBPredictions = ex2.naiveBayes(reviewSet,
                smoothedLogProbs, classProbabilities);

        System.out.println("Task 2 classifier prediction:");
        System.out.println(smoothedNBPredictions);
        System.out.println();
    }
}
