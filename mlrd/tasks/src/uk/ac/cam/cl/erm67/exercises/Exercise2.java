package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;
import java.lang.Math;

public class Exercise2 implements IExercise2
{
    public Exercise2(){

    }

    public Map<Sentiment, Double> calculateClassProbabilities(Map<Path, Sentiment> trainingSet) throws IOException
    {
        Double nPos = new Double(Collections.frequency(trainingSet.values(), Sentiment.POSITIVE));
        Double nNeg = new Double(Collections.frequency(trainingSet.values(), Sentiment.NEGATIVE));
        Double nTotal = nPos + nNeg;

        HashMap<Sentiment, Double> classProbs = new HashMap<Sentiment, Double>();
        classProbs.put(Sentiment.POSITIVE, nPos/nTotal);
        classProbs.put(Sentiment.NEGATIVE, nNeg/nTotal);
        return classProbs;
    }

    public Map<String, Map<Sentiment, Double>> calculateUnsmoothedLogProbs(Map<Path, Sentiment> trainingSet) throws IOException
    {
        Double nPositiveWords = new Double(0);
        Double nNegativeWords = new Double(0);
        HashMap<String, Double> positiveCounts = new HashMap<String, Double>();
        HashMap<String, Double> negativeCounts = new HashMap<String, Double>();

        for (Map.Entry<Path, Sentiment> entry : trainingSet.entrySet())
        {
            List<String> tokens = Tokenizer.tokenize(entry.getKey());

            for (String s : tokens) {
                positiveCounts.putIfAbsent(s, new Double(0));
                negativeCounts.putIfAbsent(s, new Double(0));
                if (entry.getValue() == Sentiment.POSITIVE)
                {
                    nPositiveWords += 1;
                    positiveCounts.replace(s, positiveCounts.get(s) + 1);
                }
                else
                {
                    nNegativeWords += 1;
                    negativeCounts.replace(s, negativeCounts.get(s) + 1);
                }
            }
        }

        HashMap<String, Map<Sentiment, Double>> logLikelihoods = new HashMap<String, Map<Sentiment, Double>>();

        for (Map.Entry<String, Double> count : positiveCounts.entrySet())
        {
            HashMap<Sentiment, Double> sentimentMap = new HashMap<Sentiment, Double>();

            Double posCount = count.getValue();
            Double negCount = negativeCounts.get(count.getKey());
            Double posDenominator = nPositiveWords;
            Double negDenominator = nNegativeWords;

            sentimentMap.put(Sentiment.POSITIVE, Math.log(posCount/posDenominator));
            sentimentMap.put(Sentiment.NEGATIVE, Math.log(negCount/negDenominator));

            logLikelihoods.put(count.getKey(), sentimentMap);
        }

        return logLikelihoods;
    }

    public Map<String, Map<Sentiment, Double>> calculateSmoothedLogProbs(Map<Path, Sentiment> trainingSet) throws IOException
    {
        Double nPositiveWords = new Double(0);
        Double nNegativeWords = new Double(0);
        HashMap<String, Double> positiveCounts = new HashMap<String, Double>();
        HashMap<String, Double> negativeCounts = new HashMap<String, Double>();

        for (Map.Entry<Path, Sentiment> entry : trainingSet.entrySet())
        {
            List<String> tokens = Tokenizer.tokenize(entry.getKey());

            for (String s : tokens) {
                positiveCounts.putIfAbsent(s, new Double(0));
                negativeCounts.putIfAbsent(s, new Double(0));
                if (entry.getValue() == Sentiment.POSITIVE)
                {
                    nPositiveWords += 1;
                    positiveCounts.replace(s, positiveCounts.get(s) + 1);
                }
                else
                {
                    nNegativeWords += 1;
                    negativeCounts.replace(s, negativeCounts.get(s) + 1);
                }
            }
        }

        HashMap<String, Map<Sentiment, Double>> logLikelihoods = new HashMap<String, Map<Sentiment, Double>>();

        for (Map.Entry<String, Double> count : positiveCounts.entrySet())
        {
            HashMap<Sentiment, Double> sentimentMap = new HashMap<Sentiment, Double>();

            Double posCount = count.getValue();
            Double negCount = negativeCounts.get(count.getKey());
            Double posDenominator = nPositiveWords + positiveCounts.size();
            Double negDenominator = nNegativeWords + negativeCounts.size();

            sentimentMap.put(Sentiment.POSITIVE, Math.log((posCount + 1)/posDenominator));
            sentimentMap.put(Sentiment.NEGATIVE, Math.log((negCount + 1)/negDenominator));

            logLikelihoods.put(count.getKey(), sentimentMap);
        }

        return logLikelihoods;
    }

    public Map<Path, Sentiment> naiveBayes(Set<Path> testSet, Map<String, Map<Sentiment, Double>> tokenLogProbs, Map<Sentiment, Double> classProbabilities) throws IOException
    {
        Map<Path, Sentiment> results = new HashMap<Path, Sentiment>();

        Path[] testArray = testSet.toArray(new Path[0]);

        for (Path p : testArray) {
            List<String> tokens = Tokenizer.tokenize(p);
            double posRating = Math.log( classProbabilities.get(Sentiment.POSITIVE));
            double negRating = Math.log(classProbabilities.get(Sentiment.NEGATIVE));

            for (String s : tokens) {
                if (tokenLogProbs.containsKey(s)){
                    posRating += tokenLogProbs.get(s).get(Sentiment.POSITIVE);
                    negRating += tokenLogProbs.get(s).get(Sentiment.NEGATIVE);
                }
            }

            Sentiment sentiment = posRating >= negRating ? Sentiment.POSITIVE : Sentiment.NEGATIVE;

            results.put(p, sentiment);
        }
        return results;
    }
}
