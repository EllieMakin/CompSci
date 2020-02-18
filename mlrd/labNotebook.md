# Machine Learning and Real World Data (Lab Notebook)

## Task 1: Simple Classifier

### Part 1: Manual Classification

| Review | Sentiment |
| ------ | --------- |
| 1      | Positive  |
| 2      | Negative  |
| 3      | Negative  |
| 4      | Positive  |

### Part 2: Sentiment Lexicon Database

The entries for *excellent* and *boring* in the given lexicon show that the exntries are structured with a polarity of either positive or negative, and with an intensity value as well.

Words I think will indicate sentiment:

| Word          | Sentiment |
| ------------- | --------- |
| excellent     | positive  |
| dry           | negative  |
| annoying      | negative  |
| spoiled       | negative  |
| unique        | positive  |
| uncomfortable | negative  |
| mistaken      | negative  |
| profanity     | negative  |
| popcorn       | positive  |
| better        | positive  |

A lot of the words I've chosen are listed with the same sentiment in the given lexicon, but some are not listed. I mostly agree with the sentiments given in the lexicon.

### Part 3: Simple Classifier Virtual programming lab

#### Setup
I've named the package `uk.ac.cam.cl.erm67.exercises` for this task. In order to compile my code, I run the following command from my parent `tasks` folder:

```sh
javac -d out/ -cp src/stanford-postagger.jar src/uk/ac/cam/cl/erm67/exercises/*.java src/uk/ac/cam/cl/mlrd/exercises/sentiment_detection/*.java src/uk/ac/cam/cl/mlrd/testing/Exercise1Tester.java
```

Then I can run the tests with

```sh
java -cp src/stanford-postagger.jar:out uk.ac.cam.cl.mlrd.testing.Exercise1Tester
```

#### Classification

I used a `HashMap` to map each token onto their respective increments (positive or negative) when rating each review. The sum of the increments is calculated for each review; if it is positive, then the review is positive, and vice versa. I initially tried creating a list of objects that stored `word, intensity, polarity` for each word, but this was slow given the length of the lexicon.

#### Evaluation

The evaluation of this initial approach gives accuracy $A = 0.6349999904632568$, which is not amazing, but not bad either.

#### Improvements

I initially added some code that would allow me to use weights based on the `intensity` value in the lexicon, so stronger tokens would produce larger changes to the rating. Specifically, `weak` tokens remained at a change of 1, and `strong` tokens gave a change of $k$, which I found gave the best accuracy at about $k = 10$. This improved the accuracy to $A = 0.6672222018241882$.

I then tried changing the boundary between positive and negative reviews, so where a rating $\ge b \implies positive$. Previously, $b$ had just been zero. I found that the best value for for this boundary was at about $b = 3$, giving an accuracy of $A = 0.6711111068725586$.

Even with these improvements, the accuracy is not very impressive.

## Task 2: Naive Bayes Classifier

### Step 0: Data preparation

Compile the code with

```sh
javac -d out/ -cp src/stanford-postagger.jar src/uk/ac/cam/cl/erm67/exercises/*.java src/uk/ac/cam/cl/mlrd/exercises/sentiment_detection/*.java src/uk/ac/cam/cl/mlrd/testing/*.java src/uk/ac/cam/cl/mlrd/utils/*.java
```

Then run the tests with

```sh
java -cp src/stanford-postagger.jar:out uk.ac.cam.cl.mlrd.testing.Exercise2Tester
```

### Step 1: Parameter estimation

Effectively just translated the pseudocode from Jurafasky and Martin.

### Step 2: Classification

The simple classifier from task 1 gives an accuracy of $A = 0.5899999737739563$ for the data used in the tester.

The unsmoothed classification gave an accuracy of $A = 0.5149999856948853$. This is only just better than random, so something is definitely wrong. It is also much worse than the accuracy of the simple classifier used in task 1.

### Step 3: Smoothing

The issue with the unsmoothed classifier, is that some of the log probabilities calculated for words are $0$, so when these words show up in a text, they overshadow whatever other words are in the text, and the final probability for that class will just be zero.

After applying smoothing, the accuracy is now $A = 0.7749999761581421$. This is much better than the unsmoothed value, but also much better than the simple classifier used for task 1.

## Task 3: Statistical laws of language

Switched to using IntelliJ instead of running commands from the terminal.

### Step 1: Zipf's law

Frequency vs. rank for the 10,000 highest-ranked words:

![frequency](notesImages/frequency.png)

The words I chose in task 1 had the following rankings:

| word          | rank | frequency |
| ------------- | ---- | --------- |
| better        | 140  | 10925     |
| excellent     | 283  | 4723      |
| unique        | 857  | 1353      |
| annoying      | 1040 | 1089      |
| popcorn       | 2886 | 326       |
| dry           | 2941 | 320       |
| uncomfortable | 3114 | 298       |
| spoiled       | 3678 | 241       |
| profanity     | 4737 | 170       |
| mistaken      | 5672 | 134       |

The frequencies of these words on the ranking graph:

![frequency2](notesImages/frequency2.png)

They mostly appear in the lower frequency section, but without being ranked *too* low. About average really.

The frequencies plotted on a log-log scale:

![logFrequency](notesImages/logFrequency.png)

Now with a line of best fit, weighted by frequency:

![bestFitLine](notesImages/bestFitLine.png)

The parameters for this line of best fit $y = mx + c$ are $m = -1.04, c = 14.3$. So,

$$
\ln{y} = m \ln{x} + c \\
y = e^{m\ln{x} + c} \\
y = e^c x^m \\
y = e^{14.3} x^{-1.04} \\
y = \frac{1.56 \times 10^6}{x^{1.04}} \\
\therefore f(r) = \frac{1.56 \times 10^6}{r^{1.04}}
$$

Using this formula with the words from task 1:

| word          | rank | predicted frequency | actual frequency |
| ------------- | ---- | ------------------- | ---------------- |
| better        | 140  | 9290                | 10925            |
| excellent     | 283  | 4474                | 4723             |
| unique        | 857  | 1417                | 1353             |
| annoying      | 1040 | 1158                | 1089             |
| popcorn       | 2886 | 402                 | 326              |
| dry           | 2941 | 394                 | 320              |
| uncomfortable | 3114 | 371                 | 298              |
| spoiled       | 3678 | 312                 | 241              |
| profanity     | 4737 | 240                 | 170              |
| mistaken      | 5672 | 199                 | 134              |

This is pretty good actually.

### Step 2: Heaps' law

Plotting the number of unique words in a given number of tokens on a log-log scale gives this graph:

![uniqueWords](notesImages/uniqueWords.png)

The line is ascending with a slight downward curve, implying a fairly high value for $\beta$, maybe $0.9$ or something.

## Task 4: Statistical Testing

### Step 1: Magnitude classifier

Reused the weighting function I made for task 1, only fixing the weighting at 2.

### Step 2: Sign test

Rounding up the number of `Null`s added to our `Plus` and `Minus` counts gives us a stricter test, since there are more data points than if we rounded down.

Using the `BigInteger` and `BigDecimal` classes made the code fairly unreadable, so I made several helper functions to encapsulate some tasks.

### Step 3: Test your classifiers

The significance of the difference between the results for the Magnitude and the Simple classifiers is $p =0.6722499772048186$, which is not statistically significant at the $5\%$ level, however between the Magnitude and Naive Bayes classifiers, the significance was $p=0.04003719161339948$, which is statistically significant at this level.

### Additional questions

Reducing the number of samples increases the p-value, since a broader range of scores is more likely on a smaller test set.

## Task 5: Cross-validation and test sets

### Step 1: Data split

I made sure that each of the folds was the same size by checking if it was the correct size, and if it was then I tried assigning it to the next fold instead.

### Step 2: Cross-validation

### Step 3: Evaluation on held-out data

### Step 4: The Wayne Rooney effect

### Step 5 (not part of tester)
