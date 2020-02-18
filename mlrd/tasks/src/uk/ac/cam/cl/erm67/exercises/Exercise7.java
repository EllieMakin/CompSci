package uk.ac.cam.cl.erm67.exercises;

import uk.ac.cam.cl.mlrd.exercises.markov_models.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise7 implements IExercise7
{

    @Override
    public HiddenMarkovModel<DiceRoll, DiceType> estimateHMM(Collection<Path> sequenceFiles) throws IOException
    {
        // probability of moving from state i to state j
        HashMap<DiceType, Map<DiceType, Double>> transitionMatrix = new HashMap<>();
        // probability of emitting observation j from state i
        HashMap<DiceType, Map<DiceRoll, Double>> emissionMatrix = new HashMap<>();
        for (DiceType jStartState : DiceType.values())
        {
            HashMap<DiceType, Double> transitionRow = new HashMap<>();
            for (DiceType jEndState : DiceType.values())
            {
                transitionRow.put(jEndState, (double) 0);
            }
            transitionMatrix.put(jStartState, transitionRow);

            HashMap<DiceRoll, Double> emissionRow = new HashMap<>();
            for (DiceRoll jObservation : DiceRoll.values())
            {
                emissionRow.put(jObservation, (double) 0);
            }
            emissionMatrix.put(jStartState, emissionRow);
        }

        List<HMMDataStore<DiceRoll, DiceType>> diceData = HMMDataStore.loadDiceFiles(sequenceFiles);

        for (HMMDataStore<DiceRoll, DiceType> rollSequence : diceData)
        {
            for (int jRoll = 0; jRoll < rollSequence.hiddenSequence.size(); jRoll++)
            {
                DiceType startType = rollSequence.hiddenSequence.get(jRoll);
                DiceRoll diceRoll = rollSequence.observedSequence.get(jRoll);

                if (jRoll != rollSequence.hiddenSequence.size()-1)
                {
                    DiceType endType = rollSequence.hiddenSequence.get(jRoll+1);
                    transitionMatrix.get(startType).merge(endType, 1.0, Double::sum);
                }
                emissionMatrix.get(startType).merge(diceRoll, 1.0, Double::sum);
            }
        }

        for (DiceType jStartState : DiceType.values())
        {
            double transitionSum = transitionMatrix.get(jStartState).values().stream().reduce(0.0, Double::sum);
            double emissionSum = emissionMatrix.get(jStartState).values().stream().reduce(0.0, Double::sum);

            if (jStartState == DiceType.END)
            {
                transitionSum = 1.0;
            }

            for (DiceType jEndState : DiceType.values())
            {
                transitionMatrix.get(jStartState).replace(jEndState, transitionMatrix.get(jStartState).get(jEndState)/transitionSum);
            }
            for (DiceRoll jObservation : DiceRoll.values())
            {
                emissionMatrix.get(jStartState).replace(jObservation, emissionMatrix.get(jStartState).get(jObservation)/emissionSum);
            }
        }

        return new HiddenMarkovModel<DiceRoll, DiceType>(transitionMatrix, emissionMatrix);
    }
}
