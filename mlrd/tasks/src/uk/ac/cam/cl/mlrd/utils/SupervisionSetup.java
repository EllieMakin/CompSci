package uk.ac.cam.cl.mlrd.utils;

import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Tokenizer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SupervisionSetup
{
    public static void main(String[] args) throws IOException
    {
        // load and tokenize the review file
        Path reviewFile = Paths.get("data/supervision_review.txt");
        List<String> tokens = Tokenizer.tokenize(reviewFile);

        // sort 'em
        Collections.sort(tokens);

        //remove duplicates
        Set<String> words = new LinkedHashSet<>(tokens);

        // write the words to a file
        FileWriter fw = new FileWriter("data/supervision_words.txt");
        for (String word : words)
        {
            // ignore short words
            if (word.length() >= 3)
            {
                fw.write(word + "\n");
            }
        }
        fw.close();

        System.out.println("Success.");
    }
}
