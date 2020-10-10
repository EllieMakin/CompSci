package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.IExercise6;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.NuancedSentiment;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Sentiment;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Tokenizer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Exercise6 implements IExercise6
{
    @Override
    public Map<NuancedSentiment, Double> calculateClassProbabilities(Map<Path, NuancedSentiment> trainingSet) throws IOException
    {
        Map<NuancedSentiment, Double> classCounts = new HashMap<NuancedSentiment, Double>();
        double nTotal = (double) 0;

        for (NuancedSentiment sentiment : NuancedSentiment.values())
        {
            double classCount = Collections.frequency(trainingSet.values(), sentiment);
            nTotal += classCount;
            classCounts.put(sentiment, classCount);
        }

        HashMap<NuancedSentiment, Double> classProbs = new HashMap<NuancedSentiment, Double>();

        for (NuancedSentiment sentiment : NuancedSentiment.values())
        {
            classProbs.put(sentiment, classCounts.get(sentiment)/nTotal);
        }

        return classProbs;
    }

    @Override
    public Map<String, Map<NuancedSentiment, Double>> calculateNuancedLogProbs(Map<Path, NuancedSentiment> trainingSet) throws IOException
    {
        double nPositiveWords = (double) 0;
        double nNegativeWords = (double) 0;
        double nNeutralWords = (double) 0;
        HashMap<String, Double> positiveCounts = new HashMap<String, Double>();
        HashMap<String, Double> negativeCounts = new HashMap<String, Double>();
        HashMap<String, Double> neutralCounts = new HashMap<String, Double>();

        for (Map.Entry<Path, NuancedSentiment> entry : trainingSet.entrySet())
        {
            List<String> tokens = Tokenizer.tokenize(entry.getKey());

            for (String s : tokens) {
                positiveCounts.putIfAbsent(s, (double) 0);
                negativeCounts.putIfAbsent(s, (double) 0);
                neutralCounts.putIfAbsent(s, (double) 0);
                if (entry.getValue() == NuancedSentiment.POSITIVE)
                {
                    nPositiveWords += 1;
                    positiveCounts.replace(s, positiveCounts.get(s) + 1);
                }
                else if (entry.getValue() == NuancedSentiment.NEGATIVE)
                {
                    nNegativeWords += 1;
                    negativeCounts.replace(s, negativeCounts.get(s) + 1);
                }
                else
                {
                    nNeutralWords += 1;
                    neutralCounts.replace(s, neutralCounts.get(s) + 1);
                }
            }
        }

        HashMap<String, Map<NuancedSentiment, Double>> logLikelihoods = new HashMap<String, Map<NuancedSentiment, Double>>();

        for (Map.Entry<String, Double> count : positiveCounts.entrySet())
        {
            HashMap<NuancedSentiment, Double> sentimentMap = new HashMap<NuancedSentiment, Double>();

            Double posCount = count.getValue();
            Double negCount = negativeCounts.get(count.getKey());
            Double neutCount = neutralCounts.get(count.getKey());
            double posDenominator = nPositiveWords + positiveCounts.size();
            double negDenominator = nNegativeWords + negativeCounts.size();
            double neutDenominator = nNeutralWords + neutralCounts.size();

            sentimentMap.put(NuancedSentiment.POSITIVE, Math.log((posCount + 1)/posDenominator));
            sentimentMap.put(NuancedSentiment.NEGATIVE, Math.log((negCount + 1)/negDenominator));
            sentimentMap.put(NuancedSentiment.NEUTRAL, Math.log((neutCount + 1)/neutDenominator));

            logLikelihoods.put(count.getKey(), sentimentMap);
        }

        return logLikelihoods;
    }

    @Override
    public Map<Path, NuancedSentiment> nuancedClassifier(Set<Path> testSet, Map<String, Map<NuancedSentiment, Double>> tokenLogProbs, Map<NuancedSentiment, Double> classProbabilities) throws IOException {
        Map<Path, NuancedSentiment> results = new HashMap<Path, NuancedSentiment>();

        Path[] testArray = testSet.toArray(new Path[0]);

        for (Path p : testArray) {
            List<String> tokens = Tokenizer.tokenize(p);

            NuancedSentiment maxClass = null;
            double maxRating = Double.NEGATIVE_INFINITY;
            for (NuancedSentiment sentiment : NuancedSentiment.values())
            {
                double classRating = Math.log(classProbabilities.get(sentiment));

                for (String s : tokens) {
                    if (tokenLogProbs.containsKey(s)){
                        classRating += tokenLogProbs.get(s).get(sentiment);
                    }
                }

                if (classRating > maxRating) {
                    maxRating = classRating;
                    maxClass = sentiment;
                }
            }
            results.put(p, maxClass);
        }
        return results;
    }

    @Override
    public double nuancedAccuracy(Map<Path, NuancedSentiment> trueSentiments, Map<Path, NuancedSentiment> predictedSentiments) {
        double c = 0;
        double i = 0;
        for (Map.Entry<Path, NuancedSentiment> entry : trueSentiments.entrySet())
        {
            if (entry.getValue() == predictedSentiments.get(entry.getKey()))
            {
                c++; // :)
            }
            else
            {
                i++;
            }
        }
        return c / (c + i);
    }

    @Override
    public Map<Integer, Map<Sentiment, Integer>> agreementTable(Collection<Map<Integer, Sentiment>> predictedSentiments) {
        HashMap<Integer, Map<Sentiment, Integer>> table = new HashMap<Integer, Map<Sentiment, Integer>>();

        for (int reviewNum = 1; reviewNum <= 4; reviewNum++)
        {
            HashMap<Sentiment, Integer> sentimentCounts = new HashMap<Sentiment, Integer>();
            for (Sentiment sentiment : Sentiment.values())
            {
                sentimentCounts.put(sentiment, 0);
            }
            table.put(reviewNum, sentimentCounts);
        }

        for (Map<Integer, Sentiment> sentimentMap : predictedSentiments)
        {
            for (Map.Entry<Integer, Sentiment> entry : sentimentMap.entrySet())
            {
                int reviewNum = entry.getKey();
                Sentiment reviewSentiment = entry.getValue();

                Map<Sentiment, Integer> sentimentCounts = table.get(reviewNum);
                sentimentCounts.put(reviewSentiment, sentimentCounts.get(reviewSentiment) + 1);
            }
        }

        return table;
    }

    @Override
    public double kappa(Map<Integer, Map<Sentiment, Integer>> agreementTable)
    {
        double N = agreementTable.size();

        Map<Integer, Integer> n_iMap = new HashMap<Integer, Integer>();
        for (int i : agreementTable.keySet())
        {
            int n_i = 0;
            for (Map.Entry<Sentiment, Integer> entry : agreementTable.get(i).entrySet()) {
                n_i += entry.getValue();
            }
            n_iMap.put(i, n_i);
        }

        double P_e = 0;

        for (Sentiment sentiment : Sentiment.values())
        {
            double innerSum = 0;

            for (int i : agreementTable.keySet())
            {
                double n_ij = agreementTable.getOrDefault(i, new HashMap<>()).getOrDefault(sentiment, 0);
                double n_i = n_iMap.get(i);
                innerSum += n_ij/n_i;
            }
            P_e += Math.pow(innerSum/N, 2);
        }

        double P_a = 0;

        for (int i : agreementTable.keySet())
        {
            double n_i = n_iMap.get(i);
            double innerSum = 0;
            for (Sentiment sentiment : Sentiment.values())
            {
                double n_ij = agreementTable.getOrDefault(i, new HashMap<>()).getOrDefault(sentiment, 0);
                innerSum += n_ij*(n_ij - 1);
            }
            P_a += innerSum/n_i/(n_i - 1)/N;
        }

        double kappa = (P_a - P_e)/(1 - P_e);

        return kappa;
    }
}
