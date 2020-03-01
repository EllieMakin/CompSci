package uk.ac.cam.cl.mlrd.testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TODO: Replace with your package.
import uk.ac.cam.cl.erm67.exercises.Exercise5;
import uk.ac.cam.cl.erm67.exercises.Exercise7;
import uk.ac.cam.cl.erm67.exercises.Exercise8;
import uk.ac.cam.cl.erm67.exercises.Exercise9;
import uk.ac.cam.cl.mlrd.exercises.markov_models.*;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.IExercise5;

public class Exercise9Tester {

	static final Path dataFile = Paths.get("data/bio_dataset.txt");

	public static void main(String[] args) throws IOException {

		List<HMMDataStore<AminoAcid, Feature>> sequencePairs = HMMDataStore.loadBioFile(dataFile);

		// Use for testing the code
		Collections.shuffle(sequencePairs, new Random(0));
		int testSize = sequencePairs.size() / 10;
		List<HMMDataStore<AminoAcid, Feature>> devSet = sequencePairs.subList(0, testSize);
		List<HMMDataStore<AminoAcid, Feature>> testSet = sequencePairs.subList(testSize, 2 * testSize);
		List<HMMDataStore<AminoAcid, Feature>> trainingSet = sequencePairs.subList(testSize * 2, sequencePairs.size());
		// But:
		// TODO: Replace with cross-validation for the tick.

		IExercise9 implementation = (IExercise9) new Exercise9();

		HiddenMarkovModel<AminoAcid, Feature> model = implementation.estimateHMM(trainingSet);
		System.out.println("Predicted transitions:");
		System.out.println(model.getTransitionMatrix());
		System.out.println();
		System.out.println("Predicted emissions:");
		System.out.println(model.getEmissionMatrix());
		System.out.println();

		HMMDataStore<AminoAcid, Feature> data = devSet.get(0);
		List<Feature> predicted = implementation.viterbi(model, data.observedSequence);
		System.out.println("True hidden sequence:");
		System.out.println(data.hiddenSequence);
		System.out.println();

		System.out.println("Predicted hidden sequence:");
		System.out.println(predicted);
		System.out.println();

		Map<List<Feature>, List<Feature>> true2PredictedSequences = implementation.predictAll(model, devSet);
		double accuracy = implementation.precision(true2PredictedSequences);
		System.out.println("Prediction precision:");
		System.out.println(accuracy);
		System.out.println();

		double recall = implementation.recall(true2PredictedSequences);
		System.out.println("Prediction recall:");
		System.out.println(recall);
		System.out.println();

		double f1Score = implementation.fOneMeasure(true2PredictedSequences);
		System.out.println("Prediction F1 score:");
		System.out.println(f1Score);
		System.out.println();

        int nFolds = 10;
        int nFiles = sequencePairs.size();
        double[] scores = new double[nFolds];

        for (int jTestFold = 0; jTestFold < nFolds; jTestFold++)
        {
            List<HMMDataStore<AminoAcid, Feature>> testingSet = sequencePairs.subList(nFiles*jTestFold/nFolds, nFiles*(jTestFold+1)/nFolds);
            List<HMMDataStore<AminoAcid, Feature>> trainSet = new ArrayList<>();
            trainSet.addAll(sequencePairs.subList(0, nFiles*jTestFold/nFolds));
            trainSet.addAll(sequencePairs.subList(nFiles*(jTestFold+1)/nFolds, nFiles));

            IExercise9 ex9 = new Exercise9();

            HiddenMarkovModel<AminoAcid, Feature> HMM = ex9.estimateHMM(trainSet);
            Map<List<Feature>, List<Feature>> trueToPredictedMap = ex9.predictAll(HMM, testingSet);
            scores[jTestFold] = ex9.fOneMeasure(trueToPredictedMap);
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
