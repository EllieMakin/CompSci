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

## Task 3:

```sh
javac -d out/ -cp src/stanford-postagger.jar:src/javafx/javafx.base.jar:src/javafx/javafx.controls.jar:src/javafx/javafx.base.jar src/uk/ac/cam/cl/erm67/exercises/*.java src/uk/ac/cam/cl/mlrd/exercises/sentiment_detection/*.java src/uk/ac/cam/cl/mlrd/testing/*.java src/uk/ac/cam/cl/mlrd/utils/*.java
```
