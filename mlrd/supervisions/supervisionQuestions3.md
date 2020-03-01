##### Ellie Makin

# Supervision questions: set3.

## Markov assumption

**State the two Markov assumptions, and explain why they are important in the definition of Hidden Markov Models.**

1. The transition probabilities depend only on the current state, not any of the previous or future states.

2. The output probabilities depend only on the current state, not any of the previous or future states, or observations.

These assumptions are important because they simplify the model so that we can look at each timestep individually.

## HMM Artificial data

**The data you were given with task 7 (parallel sequence of observations and states created by the “dice” HMM) was artificially created using an HMM (remember that we called HMMs and Naive Bayes generative models). In this exercise, you will explore how this was done.**

1. **What is the information you need in order to be able to design an algorithm for generating artifical data using an HMM?**

The transition and emission probabilities of the HMM.

2. **Describe an algorithm for creating artificial data.**

Create a variable for the current state, and at each time step, use this state and the emission probabilities to generate an observation, and then use the transition probabilities to generate the next state. Record these states and observations as the data.

3. **Transition probabilities into the final state are expressed as an extra parameter for an HMM. In some models these final transition probabilities are irrelevant. Under what circumstances would the prediction result be affected by transitions into the final state? Can you think of some examples of real world situations where this might happen?**

If a certain state was guaranteed to transition into the final state. This would occur in any sequence where the sequence always ends in the same way, maybe with data in a file, where the end-of-file is always present.

## Smoothing in HMMs

**We did not smooth the Dice HMM in task 7 nor did you smooth the protein HMM in task 9.**

1. **In which situations can smoothing be counterproductive, and why?**

If one of the probabilities is actually zero, it would be unhelpful to use smoothing, since this is a useful indicator in the model.

2. **In the case of the protein model, which of the two types of probability are better candidates for smoothing and why?**

The emission probabilities are a better candidate than the transition probabilities, since some of the transition probabilities are actually meant to be zero. It might be that some of the possible emissions didn't occur in the training data, so smoothing would be useful.

## Viterbi and Forward algorithm

**Study the Forward algorithm in the Jurafsky and Martin textbook. This is the algorithm for estimating the likelihood of an observation. It is another instance of the dynamic programming paradigm.**

1. **Give and explain the recursive formula for this dynamic programming algorithm in terms of $a_{ij}$ and $b_i(o_t)$.**

2. **Explain why there is a summation over the paths.**

## Parts of Speech tagging with HMM.

**Hidden Markov Models (HMM) can be used for Part of Speech Tagging. This is the task of assigning parts of speech, such as verb, noun, pronoun, determiner to words in a text sample.**

**The observation sequence O
is the following: O = We can fish**

1. Consider the two state sequences Xa=s0,s3,s4,s1,sf

and Xb=s0,s3,s1,s2,sf

Which interpretations of the above observation sequence do they represent?

2. Give the probabilities P(Xa)
, P(Xb), P(Xa,O) and P(Xb,O)

, and state which of these probabilities are used in the HMM.

3. Demonstrate the use of the Viterbi algorithm for deriving the most probable sequence of parts of speech given O

above. Explain your notation and intermediate results.

4. Does the model arrive at the correct disambiguation? If so, how does it achieve this? If not, what could you change so that it does?

5. If a labelled sample of text is available, then the emission probability matrix B

can be estimated from a labelled sample of text. Describe one way how this can be done.

6. The statistical laws of language imply that there is a potential problem when training emission probabilities for words. This problem manifests itself in the probability of the word can in the state sequence Xb

    from (a) above. What is the problem, and how could it be fixed?

## Viterbi with higher order HMMs.

**Viterbi is a clever algorithm that allows you to process the input in time that is linear to the observation sequence. With a first order HMM, we keep N (number of states) maximum probabilities per observation at each step.**

1. How many states do we need to keep for an N

    order HMM?

2. What are the implications for the asymptotic complexity of Viterbi?
