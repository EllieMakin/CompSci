package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.markov_models.*;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Sentiment;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static java.lang.Math.log;

public class Exercise8 implements IExercise8
{
    @Override
    public List<DiceType> viterbi(HiddenMarkovModel<DiceRoll, DiceType> model, List<DiceRoll> observedSequence)
    {
        Map<DiceType, Map<DiceType, Double>> a = model.getTransitionMatrix();
        Map<DiceType, Map<DiceRoll, Double>> b = model.getEmissionMatrix();

        // (psi) The most probable previous state for each possible current state.
        List<Map<DiceType, DiceType>> psi = new ArrayList<>();
        // (delta)
        List<Map<DiceType, Double>> delta = new ArrayList<>();

        // Initialising step
        Map<DiceType, Double> delta0 = new HashMap<>();
        for (DiceType diceType : DiceType.values())
        {
            delta0.put(diceType, log(b.get(diceType).get(observedSequence.get(0))));
        }
        delta.add(delta0);
        psi.add(null);

        // Recursive step
        for (int t = 1; t < observedSequence.size(); t++)
        {
            Map<DiceType, Double> delta_t = new HashMap<>();
            Map<DiceType, DiceType> psi_t = new HashMap<>();

            for (DiceType currentType : DiceType.values())
            {
                double maxDelta = log(0.0);
                DiceType maxState = null;
                for (DiceType prevType : DiceType.values())
                {
                    double newDelta = (
                            delta.get(t-1).get(prevType)
                            + log(a.get(prevType).get(currentType))
                            + log(b.get(currentType).get(observedSequence.get(t)))
                    );

                    if (newDelta > maxDelta) {
                        maxDelta = newDelta;
                        maxState = prevType;
                    }
                }
                delta_t.put(currentType, maxDelta);
                psi_t.put(currentType, maxState);
            }
            delta.add(delta_t);
            psi.add(psi_t);
        }

        // Backtracking
        DiceType[] predictedSequence = new DiceType[observedSequence.size()];
        predictedSequence[observedSequence.size()-1] = DiceType.END;
        for (int jStep = observedSequence.size()-1; jStep >= 1; jStep--)
        {
            predictedSequence[jStep-1] = psi.get(jStep).get(predictedSequence[jStep]);
        }

        return Arrays.asList(predictedSequence);
    }

    @Override
    public Map<List<DiceType>, List<DiceType>> predictAll(HiddenMarkovModel<DiceRoll, DiceType> model, List<Path> testFiles) throws IOException
    {
        Map<List<DiceType>, List<DiceType>> predictions = new HashMap<>();

        for (Path p : testFiles)
        {
            HMMDataStore<DiceRoll, DiceType> diceFile = HMMDataStore.loadDiceFile(p);
            List<DiceType> x = viterbi(model, diceFile.observedSequence);

            predictions.put(diceFile.hiddenSequence, viterbi(model, diceFile.observedSequence));
        }

        return predictions;
    }

    @Override
    public double precision(Map<List<DiceType>, List<DiceType>> true2PredictedMap)
    {
        double nLPredicted = 0;
        double nLCorrect = 0;

        for (Map.Entry<List<DiceType>, List<DiceType>> entry : true2PredictedMap.entrySet())
        {
            for (int jRoll = 0; jRoll < entry.getValue().size(); jRoll++)
            {
                if (entry.getValue().get(jRoll) == DiceType.WEIGHTED)
                {
                    nLPredicted++;
                    if (entry.getValue().get(jRoll) == entry.getKey().get(jRoll))
                    {
                        nLCorrect++;
                    }
                }
            }
        }

        return nLCorrect/nLPredicted;
    }

    @Override
    public double recall(Map<List<DiceType>, List<DiceType>> true2PredictedMap)
    {
        double nLTrue = 0;
        double nLCorrect = 0;

        for (Map.Entry<List<DiceType>, List<DiceType>> entry : true2PredictedMap.entrySet())
        {
            for (int jRoll = 0; jRoll < entry.getKey().size(); jRoll++)
            {
                if (entry.getKey().get(jRoll) == DiceType.WEIGHTED)
                {
                    nLTrue++;
                    if (entry.getKey().get(jRoll) == entry.getValue().get(jRoll))
                    {
                        nLCorrect++;
                    }
                }
            }
        }

        return nLCorrect/nLTrue;
    }

    @Override
    public double fOneMeasure(Map<List<DiceType>, List<DiceType>> true2PredictedMap) {
        double p = precision(true2PredictedMap);
        double r = recall(true2PredictedMap);

        return 2*(p*r)/(p+r);
    }
}
