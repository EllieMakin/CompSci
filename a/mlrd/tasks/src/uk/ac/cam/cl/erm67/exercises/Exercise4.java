package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.IExercise4;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Sentiment;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.*;

public class Exercise4 implements IExercise4
{
    HashMap<String, Integer> lexiconMap;

    public Exercise4()
    {
        this.lexiconMap = new HashMap<String, Integer>();
    }

    private void readLexicon(Path lexiconFile) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(lexiconFile))
        {
            String line = reader.readLine();
            while(line != null) {
                String[] params = line.split(" ");

                String word = params[0].split("=")[1];
                String intensity = params[1].split("=")[1];
                String polarity = params[2].split("=")[1];

                int increment = polarity.equals("positive") ? 1 : -1;

                increment *= intensity.equals("strong") ? 2 : 1;

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

    @Override
    public Map<Path, Sentiment> magnitudeClassifier(Set<Path> testSet, Path lexiconFile) throws IOException
    {
        Map<Path, Sentiment> results = new HashMap<Path, Sentiment>();

        Path[] testArray = testSet.toArray(new Path[0]);

        readLexicon(lexiconFile);

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

    private BigDecimal factorial(int n)
    {
        BigDecimal product = BigDecimal.ONE;
        for (int i = 1; i <= n; i++) {
            product = product.multiply(BigDecimal.valueOf(i));
        }
        return product;
    }

    private BigDecimal choose(int n, int k)
    {
        return factorial(n).divide(factorial(k)).divide(factorial(n-k));
    }

    @Override
    public double signTest(Map<Path, Sentiment> actualSentiments, Map<Path, Sentiment> classificationA, Map<Path, Sentiment> classificationB) {
        int nPlus = 0;
        int nMinus = 0;
        int nNull = 0;

        for (Map.Entry<Path, Sentiment> entry : actualSentiments.entrySet()) {
            if (classificationA.get(entry.getKey()) == classificationB.get(entry.getKey())) {
                nNull++;
            } else if (entry.getValue() == classificationA.get(entry.getKey())) {
                nPlus++;
            } else if (entry.getValue() == classificationB.get(entry.getKey())) {
                nMinus++;
            }
        }

        int n = 2 * (int) ceil((double) nNull / 2.0) + nPlus + nMinus;
        int k = (int) ceil((double) nNull / 2.0) + min(nPlus, nMinus);
        BigDecimal q = BigDecimal.valueOf(0.5);
        BigDecimal pValue = BigDecimal.ZERO;
        for (int i = 0; i <= k; i++)
        {
            BigDecimal nCi = choose(n, i);
            pValue = pValue.add(
                    nCi.multiply(q.pow(i))
                    .multiply(BigDecimal.ONE.subtract(q).pow(n-i))
            );
        }
        return 2 * pValue.doubleValue();
    }
}
