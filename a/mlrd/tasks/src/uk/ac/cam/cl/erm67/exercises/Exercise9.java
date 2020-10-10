package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.markov_models.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static java.lang.Math.log;

public class Exercise9 implements IExercise9 {
    @Override
    public HiddenMarkovModel<AminoAcid, Feature> estimateHMM(List<HMMDataStore<AminoAcid, Feature>> sequencePairs) throws IOException {
        // probability of moving from state i to state j
        HashMap<Feature, Map<Feature, Double>> transitionMatrix = new HashMap<>();
        // probability of emitting observation j from state i
        HashMap<Feature, Map<AminoAcid, Double>> emissionMatrix = new HashMap<>();
        for (Feature jStartState : Feature.values())
        {
            HashMap<Feature, Double> transitionRow = new HashMap<>();
            for (Feature jEndState : Feature.values())
            {
                transitionRow.put(jEndState, (double) 0);
            }
            transitionMatrix.put(jStartState, transitionRow);

            HashMap<AminoAcid, Double> emissionRow = new HashMap<>();
            for (AminoAcid jObservation : AminoAcid.values())
            {
                emissionRow.put(jObservation, (double) 0);
            }
            emissionMatrix.put(jStartState, emissionRow);
        }

        for (HMMDataStore<AminoAcid, Feature> bioSequence : sequencePairs)
        {
            for (int jEvent = 0; jEvent < bioSequence.hiddenSequence.size(); jEvent++)
            {
                Feature startType = bioSequence.hiddenSequence.get(jEvent);
                AminoAcid observation = bioSequence.observedSequence.get(jEvent);

                if (jEvent != bioSequence.hiddenSequence.size()-1)
                {
                    Feature endType = bioSequence.hiddenSequence.get(jEvent+1);
                    transitionMatrix.get(startType).merge(endType, 1.0, Double::sum);
                }
                emissionMatrix.get(startType).merge(observation, 1.0, Double::sum);
            }
        }

        for (Feature jStartState : Feature.values())
        {
            double transitionSum = transitionMatrix.get(jStartState).values().stream().reduce(0.0, Double::sum);
            double emissionSum = emissionMatrix.get(jStartState).values().stream().reduce(0.0, Double::sum);

            if (jStartState == Feature.END)
            {
                transitionSum = 1.0;
            }

            for (Feature jEndState : Feature.values())
            {
                transitionMatrix.get(jStartState).replace(jEndState, transitionMatrix.get(jStartState).get(jEndState)/transitionSum);
            }
            for (AminoAcid jObservation : AminoAcid.values())
            {
                emissionMatrix.get(jStartState).replace(jObservation, emissionMatrix.get(jStartState).get(jObservation)/emissionSum);
            }
        }

        return new HiddenMarkovModel<AminoAcid, Feature>(transitionMatrix, emissionMatrix);
    }

    @Override
    public List<Feature> viterbi(HiddenMarkovModel<AminoAcid, Feature> model, List<AminoAcid> observedSequence) {
        Map<Feature, Map<Feature, Double>> a = model.getTransitionMatrix();
        Map<Feature, Map<AminoAcid, Double>> b = model.getEmissionMatrix();

        // (psi) The most probable previous state for each possible current state.
        List<Map<Feature, Feature>> psi = new ArrayList<>();
        // (delta)
        List<Map<Feature, Double>> delta = new ArrayList<>();

        // Initialising step
        Map<Feature, Double> delta0 = new HashMap<>();
        for (Feature feature : Feature.values())
        {
            delta0.put(feature, log(b.get(feature).get(observedSequence.get(0))));
        }
        delta.add(delta0);
        psi.add(null);

        // Recursive step
        for (int t = 1; t < observedSequence.size(); t++)
        {
            Map<Feature, Double> delta_t = new HashMap<>();
            Map<Feature, Feature> psi_t = new HashMap<>();

            for (Feature currentType : Feature.values())
            {
                double maxDelta = log(0.0);
                Feature maxState = null;
                for (Feature prevType : Feature.values())
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
        Feature[] predictedSequence = new Feature[observedSequence.size()];
        predictedSequence[observedSequence.size()-1] = Feature.END;
        for (int jStep = observedSequence.size()-1; jStep >= 1; jStep--)
        {
            predictedSequence[jStep-1] = psi.get(jStep).get(predictedSequence[jStep]);
        }

        return Arrays.asList(predictedSequence);
    }

    @Override
    public Map<List<Feature>, List<Feature>> predictAll(HiddenMarkovModel<AminoAcid, Feature> model, List<HMMDataStore<AminoAcid, Feature>> testSequencePairs) throws IOException {
        Map<List<Feature>, List<Feature>> predictions = new HashMap<>();

        for (HMMDataStore<AminoAcid, Feature> dataStore : testSequencePairs)
        {
            List<Feature> x = viterbi(model, dataStore.observedSequence);

            predictions.put(dataStore.hiddenSequence, viterbi(model, dataStore.observedSequence));
        }

        return predictions;
    }

    @Override
    public double precision(Map<List<Feature>, List<Feature>> true2PredictedMap) {
        double nMPredicted = 0;
        double nMCorrect = 0;

        for (Map.Entry<List<Feature>, List<Feature>> entry : true2PredictedMap.entrySet())
        {
            for (int jEvent = 0; jEvent < entry.getValue().size(); jEvent++)
            {
                if (entry.getValue().get(jEvent) == Feature.MEMBRANE)
                {
                    nMPredicted++;
                    if (entry.getValue().get(jEvent) == entry.getKey().get(jEvent))
                    {
                        nMCorrect++;
                    }
                }
            }
        }

        return nMCorrect/nMPredicted;
    }

    @Override
    public double recall(Map<List<Feature>, List<Feature>> true2PredictedMap) {
        double nMTrue = 0;
        double nMCorrect = 0;

        for (Map.Entry<List<Feature>, List<Feature>> entry : true2PredictedMap.entrySet())
        {
            for (int jEvent = 0; jEvent < entry.getKey().size(); jEvent++)
            {
                if (entry.getKey().get(jEvent) == Feature.MEMBRANE)
                {
                    nMTrue++;
                    if (entry.getKey().get(jEvent) == entry.getValue().get(jEvent))
                    {
                        nMCorrect++;
                    }
                }
            }
        }

        return nMCorrect/nMTrue;
    }

    @Override
    public double fOneMeasure(Map<List<Feature>, List<Feature>> true2PredictedMap) {
        double p = precision(true2PredictedMap);
        double r = recall(true2PredictedMap);

        return 2*(p*r)/(p+r);
    }
}
