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

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;

public class Exercise1 implements IExercise1
{
    HashMap<String, Integer> lexiconMap;

    public Exercise1()
    {
        this.lexiconMap = new HashMap<String, Integer>();
    }

    private void readLexicon(Path lexiconFile, int weight) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(lexiconFile))
        {
            String line = reader.readLine();
            while(line != null) {
                String[] params = line.split(" ");

                String word = params[0].split("=")[1];
                String intensity = params[1].split("=")[1];
                String polarity = params[2].split("=")[1];

                Integer increment = polarity.equals("positive") ? 1 : -1;

                increment *= intensity.equals("strong") ? weight : 1;

                lexiconMap.put(word, increment);

                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e)
        {
            throw new IOException("Can't access file " + lexiconFile, e);
        }
    }

    private int getScore(String token)
    {
        Integer score = lexiconMap.get(token);
        return score == null ? 0 : score;
    }

    public Map<Path, Sentiment> simpleClassifier(Set<Path> testSet, Path lexiconFile) throws IOException
    {
        Map<Path, Sentiment> results = new HashMap<Path, Sentiment>();

        Path[] testArray = testSet.toArray(new Path[0]);

        readLexicon(lexiconFile, 1);

        for (Path p : testArray) {
            List<String> tokens = Tokenizer.tokenize(p);
            int rating = 0;

            for (String s : tokens) {
                rating += getScore(s);
            }

            Sentiment sentiment = rating >= 0 ? Sentiment.POSITIVE : Sentiment.NEGATIVE;

            results.put(p, sentiment);
        }
        return results;
    }

    public double calculateAccuracy(Map<Path, Sentiment> trueSentiments, Map<Path, Sentiment> predictedSentiments) {
        float c = 0;
        float i = 0;
        for (Map.Entry<Path, Sentiment> entry : trueSentiments.entrySet())
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

    public Map<Path, Sentiment> improvedClassifier(Set<Path> testSet, Path lexiconFile) throws IOException
    {
        Map<Path, Sentiment> results = new HashMap<Path, Sentiment>();

        Path[] testArray = testSet.toArray(new Path[0]);

        readLexicon(lexiconFile, 10);

        for (Path p : testArray) {
            List<String> tokens = Tokenizer.tokenize(p);
            int rating = 0;

            for (String s : tokens) {
                rating += getScore(s);
            }

            Sentiment sentiment = rating >= 3 ? Sentiment.POSITIVE : Sentiment.NEGATIVE;

            results.put(p, sentiment);
        }
        return results;
    }
}
