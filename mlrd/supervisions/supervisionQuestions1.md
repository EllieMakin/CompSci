# MLRD Supervision Questions

## Set 1

### Sentiment Lexicon

1. **To be done with other members of your supervision group) Each find a short piece of text (100 words or less) expressing an opinion about something, without showing it to the others. This could be a review, but don't use a movie review or similar. Tokenize and then sort the words so they are in alphabical order, removing duplicates. Now swap lists. Mark each word in the list you've been given as positive or negative sentiment and say whether you think the piece the words have come from is overall positive or negative. Compare your answer with the original text. Did you get the overall sentiment right? Were the words used in the way you thought they might be?**

| word      | sentiment |
| --------- | --------- |
| burnt     | negative  |
| byee      | negative  |
| chain     | negative  |
| cheese    | -         |
| chips     | -         |
| downhill  | negative  |
| express   | negative  |
| few       | negative  |
| friend    | positive  |
| gone      | negative  |
| hardly    | negative  |
| hole      | negative  |
| hut       | negative  |
| last      | negative  |
| little    | negative  |
| middle    | -         |
| over      | negative  |
| pizza     | -         |
| polenta   | -         |
| road      | -         |
| smallest  | negative  |
| soft      | negative  |
| soggy     | negative  |
| stingiest | negative  |
| time      | -         |
| topping   | -         |
| years     | negative  |

I think the review is probably mostly negative based on these words...

I was correct. The words were all used how I thought they would be used.


2. **Does your Task 1 system get this right? What about your Task 2 system?**

The task 1 system correctly chooses negative, but the task 2 system guesses that the text is positive when it should be negative.

3. **What sort of words change the polarity of the sentiment words? not is an obvious example: can you think of 10 others? Are there any examples in the text you looked at in 1? Which words in a sentence can have their sentiment flipped if there's a not in the sentence?**

| polarity changers |
| ----------------- |
| hardly            |
| failed to         |
| never             |
| wasn't            |
| didn't            |
| couldn't          |
| isn't             |
| tried to be       |
| nor               |
| no longer         |
| could be more     |

About half of the examples above are phrases I found in the text from task 1.

Adjectives and verbs can have their sentiment flipped by a 'not'.

4. **Try looking at some social media posts and work out whether you could find words which indicated different types of sentiment: e.g., could you use a lexicon to classify posts according to how emotionally involved someone was feeling?**

I thought you might be able to classify posts into serious/comedy categories, but this actually seems really difficult given how much sarcasm is used in social media posts. Instead, I think political/apolitical would be fairly easy to classify.

5. **In a test set with 412 examples, 328 are correctly classified. What is the accuracy?**

$A = \frac{328}{412} \approx 0.79611650485$

6. **Why is accuracy not necessarily a good measure of success if the classes have very different probabilities?**

If the classifier simply chooses the most probable class every time, then it will get a majority correct, even though it hasn't learned anything about the text.

### Naive Bayes

1. **Suppose that you are using Naive Bayes on a task where you have 100 documents in a training set, which is equally divided between class A and class B. There are three features F1, F2 and F3: each may occur at most once in a document. (Note that the set up here is a little different from the way we used NB in Task 2.) The distribution for the three features among documents is as follows:**

|    | A | B  |
| -- | - | -- |
| F1 | 5 | 5  |
| F2 | 0 | 10 |
| F3 | 3 | 27 |

a. **Show the estimated conditional probabilities for each class, given each feature.**

$$
P(A|F1) = 0.5 \\
P(B|F1) = 0.5 \\
P(A|F2) = 0.0 \\
P(B|F2) = 1.0 \\
P(A|F3) = 0.1 \\
P(B|F3) = 0.9 \\
$$

b. **Assume that you are trying to classify a document which contains only the features F1 and F3: how would you estimate the relative probability of A and B (without add-one smoothing)?**

$$
P_{relative}(A|F1,F3)
= P(F1, F3|A) \cdot P(A)
\approx P(F1|A) \cdot P(F3|A) \cdot P(A) \\
$$

Likewise for B.

c. **What difference would it make if there were 25 documents in class A in the training set and 75 in class B?**

The conditional probabilities for class B would be much higher than those for class A. As a result, the relative probability for B would also be higher than that for A.

d. **Which of the features F1, F2 and F3 would be more useful for classification in general? Explain your answer.**

F2 would be the most useful, since if it appears, then the class is almost definitely B.

e. **Given reasonable amounts of training and test data and a feature set with 10 features, how could you establish which features were most useful?**

Try classifying by only using one feature at a time, and see which of the features gives the highest accuracy.

2. **(Difficult) The approach we asked you to take for NB incorporated probabilities for all the positions in the document (i.e., all the tokens). An alternative approach, often used for document classification, is to count words only once no matter how many times they appear in a document. This model is clearly less informative than the approach we used, but it usually works better. Why?**

Since each word is counted once, so they are given equal weighting, the rarer, but more strongly indicative words have more effect on the classification.

### Statistical properties of language

1. **Given that we will always see new words given a sufficiently large corpus, how is it that most people would confidently say that the following are not English words: pferd, abtruce, Kx'a. Are they right?**

Most would say that these are not English words, because they have never seen or heard them before in the context of English language. However, that is not to say that these words will *never* be used in the English language. In fact, this is to be expected, since the above rule says there are always words that one hasn't seen before. Certainly 'abtruce' sounds similar to many typical English words. In my opinion, the concept of a language as some finite set of words is a vague one, since words come in and out of use all the time; I don't think such a thing as a language exists. Rather, all imaginable words form a sort of universal language, within which each person understands meaning in a subset.
