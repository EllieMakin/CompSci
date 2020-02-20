package uk.ac.cam.cl.mlrd.testing;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//TODO: Replace with your package.
import uk.ac.cam.cl.erm67.exercises.*;
import uk.ac.cam.cl.mlrd.exercises.markov_models.DiceRoll;
import uk.ac.cam.cl.mlrd.exercises.markov_models.DiceType;
import uk.ac.cam.cl.mlrd.exercises.markov_models.HMMDataStore;
import uk.ac.cam.cl.mlrd.exercises.markov_models.HiddenMarkovModel;
import uk.ac.cam.cl.mlrd.exercises.markov_models.IExercise7;
import uk.ac.cam.cl.mlrd.exercises.markov_models.IExercise8;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.IExercise5;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Sentiment;

public class Exercise8Tester {

	static final Path dataDirectory = Paths.get("data/dice_dataset");

	public static void main(String[] args)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		List<Path> sequenceFiles = new ArrayList<>();
		try (DirectoryStream<Path> files = Files.newDirectoryStream(dataDirectory)) {
			for (Path item : files) {
				sequenceFiles.add(item);
			}
		} catch (IOException e) {
			throw new IOException("Cant access the dataset.", e);
		}

		// Use for testing the code
		Collections.shuffle(sequenceFiles, new Random(0));
		int testSize = sequenceFiles.size() / 10;
		List<Path> devSet = sequenceFiles.subList(0, testSize);
		List<Path> testingSet = sequenceFiles.subList(testSize, 2 * testSize);
		List<Path> trainSet = sequenceFiles.subList(testSize * 2, sequenceFiles.size());
		// But:
		// TODO: Replace with cross-validation for the tick.

		IExercise7 implementation7 = (IExercise7) new Exercise7();
		HiddenMarkovModel<DiceRoll, DiceType> model = implementation7.estimateHMM(trainSet);

		IExercise8 implementation = (IExercise8) new Exercise8();

		HMMDataStore<DiceRoll, DiceType> data = HMMDataStore.loadDiceFile(devSet.get(0));
		List<DiceType> predicted = implementation.viterbi(model, data.observedSequence);
		System.out.println("True hidden sequence:");
		System.out.println(data.hiddenSequence);
		System.out.println();

		System.out.println("Predicted hidden sequence:");
		System.out.println(predicted);
		System.out.println();

		Map<List<DiceType>, List<DiceType>> true2PredictedMap = implementation.predictAll(model, devSet);
		double precision = implementation.precision(true2PredictedMap);
		System.out.println("Prediction precision:");
		System.out.println(precision);
		System.out.println();

		double recall = implementation.recall(true2PredictedMap);
		System.out.println("Prediction recall:");
		System.out.println(recall);
		System.out.println();

		double fOneMeasure = implementation.fOneMeasure(true2PredictedMap);
		System.out.println("Prediction fOneMeasure:");
		System.out.println(fOneMeasure);
		System.out.println();

		int nFolds = 10;
        int nFiles = sequenceFiles.size();
        double[] scores = new double[nFolds];

        for (int jTestFold = 0; jTestFold < nFolds; jTestFold++)
        {
            List<Path> testSet = sequenceFiles.subList(nFiles*jTestFold/nFolds, nFiles*(jTestFold+1)/nFolds);
            List<Path> trainingSet = new ArrayList<>();
            trainingSet.addAll(sequenceFiles.subList(0, nFiles*jTestFold/nFolds));
            trainingSet.addAll(sequenceFiles.subList(nFiles*(jTestFold+1)/nFolds, nFiles));

            IExercise7 ex7 = new Exercise7();
            IExercise8 ex8 = new Exercise8();

            HiddenMarkovModel<DiceRoll, DiceType> HMM = ex7.estimateHMM(trainingSet);
            Map<List<DiceType>, List<DiceType>> trueToPredictedMap = ex8.predictAll(HMM, testSet);
            scores[jTestFold] = ex8.fOneMeasure(trueToPredictedMap);
        }

        IExercise5 ex5 = new Exercise5();
        System.out.println("Cross-validation scores:");
        System.out.println(Arrays.toString(scores));
        System.out.println();
        System.out.println("Cross-validation average:");
        System.out.println(ex5.cvAccuracy(scores));
        System.out.println();
        System.out.println("Cross-validation variance:");
        System.out.println(ex5.cvVariance(scores));
        System.out.println();
	}
}
