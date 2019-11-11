# Foundations of Computer Science

## Exercise 1

1. *One solution to the year 2000 bug involves storing years as two digits, but interpreting them such that 50 means 1950 and 49 means 2049. Comment on the merits and demerits of this approach.*

- Merits:

    - Computer systems do not have to be radically updated in order to store a new date format - the old format still works

- Demerits:

    - Years outside of the range 1950-2049 will be interpreted incorrectly.

    - The new system will have to be updated again in 50 years.

2. *Using the date representation of the previous exercise, code OCaml functions to:*

    *(a) compare two years*
    ```
    let compareYears year1 year2 =
        ((year2 + 50) mod 100) - ((year1 + 50) mod 100)
    ;;
    ```
    *(b) add/subtract some given number of years from another year.*
    ```
    let addYears startYear nYears =
        (startYear + nYears) mod 100
    ;;
    ```

3. *Why would no experienced programmer write an expression of the form `if … then true else false`? What about expressions of the form `if … then false else true`?*

The expression itself will return `true` or `false`, so there is no need to explicitly return `true` or `false`. In the second case, the complement of the expression can be returned instead.

4. *Functions `npower` and `power` both return a `float`. The definition of `npower` returns the float value `1.0` in its base case. The definition of `power` does not, so how does the OCaml type checker know that `power` returns a `float`?*

In the last return statement, the result is computed using the `*.` operator, which returns a `float`.

5. *Because computer arithmetic is based on binary numbers, simple decimals such as 0.1 often cannot be represented exactly. Write a function that performs the computation
`x + x + ... + x`
where `x` has type float. (It is essential to use repeated addition rather than multiplication!)*

```
let rec addFloats x n =
    if n <= 0 then 0
    else x +. addFloats x (n-1)
;;
```

6. *Another example of the inaccuracy of floating-point arithmetic takes the golden ratio `𝜙 ≈ 1.618…` as its starting point: `𝛾[0] = (1 + sqrt(5))/2` and `𝛾[n+1] = 1/(𝛾[n] − 1)`.
In theory, it is easy to prove that `𝛾[n] = ⋯ = 𝛾[1] = 𝛾[0]` for all `n > 0`. Code this computation in OCaml and report the value of 𝛾[50].*

```
let rec goldenFloat n =
    if n > 0 then 1.0 /. (goldenFloat (n-1) -. 1.0)
    else (1.0 +. (sqrt 5.0)) /. 2.0
;;
```

## Exercise 2

1. *Code an iterative version of the function `power`*.

```
let rec iterativePower base index product =
    if index = 0 then
        product
    else if even index then
        iterativePower (base *. base) (index / 2) product
    else
        iterativePower (base *. base) (index / 2) (product *. base)
;;
```

2. *Add a column to the table of complexities from The Design and Analysis of Computer Algorithms with the heading 60 hours:*

| complexity | 1 second | 1 minute | 1 hour    | 60 hours    |
| ---------- | -------- | -------- | --------- | ----------- |
| `n`        | 1000     | 60000    | 3 600 000 | 216 000 000 |
| `n*log(n)` | 140      | 4895     | 204 095   | 9 327 260   |
| `n^2`      | 31       | 244      | 1897      | 14 696      |
| `n^3`      | 10       | 39       | 153       | 600         |
| `2^n`      | 9        | 15       | 21        | 27          |

3. *Let `g[1], ... g[k]` be functions such that `g[i](n) >= 0` for `i = 1, ... k` and all sufficiently large n. Show that if `f(n) = O(a[1]g[1](n)+ ... +a[k]g[k](n))`
then `f(n) = O(g[1](n)+⋯+g[k](n))`.*

Let `A = max(a[1], ... a[n])`. Then
```
f(n) = O(A*g[1](n)+ ... + A*g[k](n))
    = A*O(g[1](n)+ ... + g[k](n))
    = O(g[1](n)+ ... + g[k](n))
```

4. *Find an upper bound for the recurrence given by `T(1) = 1` and `T(n) = 2T(n/2) + 1`. You should be able to find a tighter bound than `O(nlog(n))`.*

`O(log(n))`

## Exercise 3

1. *Code a recursive function to compute the sum of a list’s elements. Then code an iterative version and comment on the improvement in efficiency.*

Recursive:
```
let sumListRec someList =
    match someList with
    | [] -> 0
    | first :: rest -> first + sumListRec rest
;;
```

Iterative:
```
let sumListIter someList total =
    match someList with
    | [] -> total
    | first :: rest -> sumListIter rest (total + first)
;;
```

The iterative version is more efficient, because it does not have to store each of the items in `someList` on the stack - instead they are added as it goes.

2. *Code a function to return the last element of a non-empty list. How efficiently can this be done?*

```
let lastItem someList =
    match someList with
    | [] -> raise (Failure "There is no last item in an empty list")
    | only :: [] -> only
    | first :: rest -> lastItem rest
;;
```
The best this can be done is `O(n)`, because the list must be traversed in order to access the last item.

3. *Code a function to return the list consisting of the even-numbered elements of the list given as its argument. For example, given `[a; b; c; d]` it should return `[b; d]`.*

```
let rec getEvens someList =
    match someList with
    | [] -> []
    | only :: [] -> []
    | first :: second :: rest -> second :: getEvens rest
;;
```

4. *Consider the polymorphic types in these two function declarations:*

    ```
    let id x = x`

    val id : 'a -> 'a = <fun>
    ```
    ```
    let rec loop x = loop x

    val loop : 'a -> 'b = <fun>
    ```
    *Explain why these types make logical sense, preventing run time type errors, even for expressions like `id [id [id 0]]` or `loop true / loop 3`.*

For `id`, the function simply returns the input, so the output type must be the same type as the input, hence `'a -> 'a`.

For `loop`, the output type is not known, because it is not generated in any expression which would imply its type. This means that the output type of the function is not necessarily the same as the input type, so we deduce `'a -> 'b`. This function will not terminate at runtime, however it will not raise any errors, because the polymorphic return type can be used by the `/` operator, since it may be an int.

5. *Code a function `tails` to return the list of the tails of its argument. For example, given `[1; 2; 3]` it should return `[[1; 2; 3]; [2; 3]; [3]; []]`.*

```
let tails someList =
    match someList with
    | [] -> [[]]
    | first :: rest -> (first :: rest) :: (tails rest)
;;
```

## Exercise 4

1. *Code a function to implement set union, by analogy with inter above. It should avoid introducing repetitions, for example the union of the lists `[4; 7; 1]` and `[6; 4; 7]` should be `[1; 6; 4; 7]` (though the order does not matter).*

```
let rec union list1 list2 =
    let rec isMember someItem someList =
        match someList with
        | [] -> false
        | first :: rest ->
            if first = someItem then
                true
            else
                isMember someItem rest
    in
        match list2 with
        | [] -> list1
        | first :: rest ->
            if isMember first list1 then
                union list1 rest
            else
                union (first :: list1) rest
;;
```

2. *Code a function that takes a list of integers and returns two lists, the first consisting of all non-negative numbers found in the input and the second consisting of all the negative numbers.*

```
let rec separateNegatives someList =
    match someList with
    | [] -> ([], [])
    | first :: rest ->
        match separateNegatives rest with
        | (negatives, nonNegatives) ->
            if first < 0 then
                (first :: negatives, nonNegatives)
            else
                (negatives, first :: nonNegatives)
;;
```

3. *How does this version of zip differ from the one above?*

    ```
    let rec zip xs ys =
        match xs, ys with
        | (x::xs, y::ys) -> (x, y) :: zip xs ys
        | ([], [])   -> []
    ;;
    ```

The base case in the 1st version uses a wildcard pattern, which implicitly will match `([], [])`, where the 2nd version explicitly matches this pattern.

4. *What assumptions do the ‘making change’ functions make about the variables `till` and `amt`? Describe what could happen if these assumptions were violated.*

It is assumed that `till` contains the possible coins in ascending value. If this is not the case, then the result will not necessarily return coins in descending order.

It is assumed that `amt >= 0`. If this is not the case, then the function will run forever.

5. *Show that the number of ways of making change for `n` (ignoring order) is `O(n)` if there are two legal coin values. What if there are three, four, ... coin values?*

Let `c[0], ... c[n]` be the available coin values, and `N(a)` be the number of ways for making change for amount `a`, ignoring order.

6. *We know nothing about the functions `f` and `g` other than their polymorphic types: `val f : 'a * 'b -> 'b * 'a` and `val g : 'a -> 'a list`. Suppose that `f (1, true)` and `g 0` are evaluated and return their results. State, with reasons, what you think the resulting values will be.*

In order for the types `'a` and `'b` to be polymorphic, the functions `f` and `g` cannot perform any type-specific operations on them. The functions can only move the variables around, so `f (1, true)` can only return `(true, 1)`, having performed no operations on the items in the tuple. Similarly, `g 0` can only return a list of `0`s, of any length.

## Exercise 5

1. *Another sorting algorithm (selection sort) consists of looking at the elements to be sorted, identifying and removing a minimal element, which is placed at the head of the result. The tail is obtained by recursively sorting the remaining elements. State, with justification, the time complexity of this approach.*

The complexity of finding the minimal element in an unsorted list is `O(n)`, since each element must be compared at least once to find if it is the smallest. This process must be carried out `n` times, with decreasing numbers of elements to compare each time, meaning the complexity of the algorithm is `O(n(n+1)/2) = O(n^2)`.

2. Implement selection sort (see previous exercise) using OCaml.

```
let rec selectionSort someList =
    (* Brings the smallest element to the front of the list *)
    let rec selectMinimum checked toCheck =
        match toCheck with
        | [] -> checked
        | first :: rest -> (
            match checked with
            | [] -> selectMinimum (first :: []) rest
            | smallest :: notSmallest ->
                if first <= smallest then
                    selectMinimum (first :: checked) rest
                else
                    selectMinimum (smallest :: first :: notSmallest) rest
        )
    in
        match someList with
        | [] -> []
        | _ -> (
            match selectMinimum [] someList with
            | [] -> []
            | smallest :: rest -> smallest :: (selectionSort rest)
        )
;;
```

3. *Another sorting algorithm (bubble sort) consists of looking at adjacent pairs of elements, exchanging them if they are out of order and repeating this process until no more exchanges are possible. State, with justification, the time complexity of this approach.*

The worst case for bubble sort would be if the list was reversed. This would require `n-1` swaps in order to place the first element correctly, `n-2` for the second, and so on, which has complexity `O(n^2)`.

4. *Implement bubble sort (see previous exercise) using OCaml.*

```
let rec bubbleSort someList =
    let rec bubblePass otherList =
        match otherList with
        | first :: second :: rest ->
            if first > second then
                second :: bubblePass (first :: rest)
            else
                first :: bubblePass (second :: rest)
        | _ -> otherList
    in
        let passedList = bubblePass someList
        in
            if passedList = someList then
                passedList
            else
                bubbleSort passedList
;;
```

## Exercise 6

1. *Give the declaration of an OCaml type for the days of the week. Comment on the practicality of such a type in a calendar application.*

```
type weekDay =
    | Monday
    | Tuesday
    | Wednesday
    | Thursday
    | Friday
    | Saturday
    | Sunday
;;
```

This type may be fairly practical in a calendar application, since the type definition will save space compared to string storage of days.

2. *Write an OCaml function taking a binary tree labelled with integers and returning their sum.*

```
let rec sumTree someTree =
    match someTree with
    | Lf -> 0
    | Br (vertex, tree1, tree2) ->
        vertex + (sumTree tree1) + (sumTree tree2)
;;
```    

3. *Using the definition of 'a tree from before: `type 'a tree = Lf | Br of 'a * 'a tree * 'a tree`, examine the following function declaration. What does `ftree (1, n)` accomplish?*

    ```
    let rec ftree k n =
        if n = 0 then
            Lf
        else
            Br (k, ftree (2 * k) (n - 1), ftree (2 * k + 1) (n - 1))
    ;;
    ```

`ftree (1, n)` constructs a binary tree with `n` levels, which represents all binary numbers with `n` digits.

4. *Give the declaration of an OCaml type for arithmetic expressions that have the following possibilities: real numbers, variables (represented by strings), or expressions of the form `−E, E+E, E×E`.*

```
type expression =
    | Real of float
    | Var of string
    | Sub of expression
    | Add of expression * expression
    | Mult of expression * expression
```

5. *Continuing the previous exercise, write a function that evaluates an expression. If the expression contains any variables, your function should raise an exception indicating the variable name.*
```
let rec evaluate someExpression =
    match someExpression with
    | Real (value) -> value
    | Var (name) -> raise (Failure name)
    | Sub (a) -> 0.0 -. (evaluate a)
    | Add (a, b) -> (evaluate a) +. (evaluate b)
    | Mult (a, b) -> (evaluate a) *. (evaluate b)
;;
```    

## Excercise 7

1. *Draw the binary search tree that arises from successively inserting the following pairs into the empty tree: `("Alice", 6)`, `("Tobias", 2)`, `("Gerald", 8)`, `("Lucy", 9)`. Then repeat this task using the order `("Gerald", 8)`, `("Alice", 6)`, `("Lucy", 9)`, `("Tobias", 2)`. Why are results different?*

```
1.
("Alice", 6) -- ("Tobias", 2)
                    |
                ("Gerald", 8) -- ("Lucy", 9)

2.
("Gerald", 8) -- ("Lucy", 9) -- ("Tobias", 2)
    |
("Alice", 6)
```
The results are different because the tree fills from the top down, so if an element is added later it is likely to be lower in the tree than before.

2. *Code an insertion function for binary search trees. It should resemble the existing `update` function except that it should raise the exception `Collision` if the item to be inserted is already present.*

```
exception Collision
let rec insertToTree k v someTree =
    match someTree with
    | Lf -> Br ((k, v), Lf, Lf)
    | Br ((a, x), t1, t2) ->
        if k < a then
            Br ((a, x), insertToTree k v t1, t2)
        else if a < k then
            Br ((a, x), t1, insertToTree k v t2)
        else (* if a = k *)
            raise Collision
;;
```

3. *Continuing the previous exercise, it would be natural for exceptional `Collision` to return the value previously stored in the dictionary. Why is that goal difficult to achieve?*

`type 'a tree` is polymorphic, so the type of the value in the dictionary is hard to retrieve.

4. *Describe an algorithm for deleting an entry from a binary search tree. Comment on the suitability of your approach.*

Recursively pass the requested entry down the correct branches until the item is reached, or the end of the tree is reached. In the first case, replace the item with the left branch, and insert each of the items in the right branch into it. In the latter case, just return Lf.

5. *Code the delete function outlined in the previous exercise.*

```
(* This relies on the insertToTree function from before *)
let deleteFromTree entry someTree =
    let merge tree1 tree2 =
        match tree1 with
        | Lf -> tree2
        | Br ((key, value), lowers, highers) ->
            insertToTree key value (merge lowers (merge highers tree2))
    in
    match someTree with
    | Lf -> Lf
    | Br ((key, value), lowers, highers) ->
        if entry < key then
            Br((key, value), deleteFromTree entry lowers, highers)
        else if entry > key then
            Br((key, value), lowers, deleteFromTree entry highers)
        else
            merge lowers highers
;;
```

6. *Show that the functions `preorder`, `inorder` and `postorder` all require `O(n^2)` time in the worst case, where `n` is the size of the tree.*

Each of the three functions must be run on every item in the tree while traversing it, which means it runs `n` times. In addition to this, at each stage, the `@` operator is used, which runs in `O(n)` time, so the total complexity in each case is `O(n^2)`.

7. *Show that the functions `preord`, `inord` and `postord` all take linear time in the size of the tree.*

The new functions no longer use the `@` operator, instead constructing the list with the `::` operator for each element, which runs in `O(1)` time. This simplifies the total complexity to `O(n)`.

8. *Write a function to remove the first element from a functional array. All the other elements are to have their subscripts reduced by one. The cost of this operation should be linear in the size of the array.*

```
let rec removeFirst someTree =
    let rec merge tree1 tree2 =
        match tree1 with
        | Lf -> tree2
        | Br ((key, value), lowers, highers) ->
            insertToTree key value (merge lowers (merge highers tree2))
    in
    let rec decrementBranch otherTree =
        match otherTree with
        | Lf -> Lf
        | Br((key, value), lowers, highers) ->
            Br((key, value-1), decrementBranch lowers, decrementBranch highers)
    in
    match someTree with
    | Lf -> Lf
    | Br ((key, value), lowers, highers) ->
        if 1 < key then
            Br((key, value-1), removeFirst lowers, decrementBranch highers)
        else if 1 > key then
            Br((key, value-1), decrementBranch lowers, removeFirst highers)
        else
            merge (decrementBranch lowers) (decrementBranch highers)
;;
```

## Exercise 8

1. *What does the following function do, and what are its uses? `let sw f x y = f y x`*

Applies the function `f` to variables `x` and `y` but with their order swapped. This could be partially applied and used with the map function in order to map over the first argument, rather than the second.

2. *There are many ways of combining orderings. The lexicographic ordering uses two keys for comparisons. It is specified by `(x',y′) < (x,y) <=> x′ < x OR (x′ = x AND y′ < y)`. Write an OCaml function to lexicographically combine two orderings, supplied as functions. Explain how it allows function insort to sort a list of pairs.*

```
let isLessDouble isLess1 isLess2 double1 double2 =
    match double1 with
    | (x', y') ->
        match double2 with
        | (x, y) ->
            isLess1 x' x || (x' = x && isLess2 y' y)
;;
```

3. *Without using map write a function map2 such that map2 f is equivalent to map (map f). The obvious solution requires declaring two recursive functions. Try to get away with only one by exploiting nested pattern-matching.*

```
let rec map2 f = function
    | [] -> []
    | [] :: restLists ->
        [] :: map2 f restLists
    | (firstItem :: firstList) :: restLists -> (
        match map2 f (firstList::restLists) with
        | [] -> [[f firstItem]]
        | firstMap::restMap -> (f firstItem :: firstMap) :: restMap
    )
;;
```

4. *The type `'a option`, declared below, can be viewed as a type of lists having at most one element. (It is typically used as an alternative to exceptions.) Declare an analogue of the function `map` for type `'a option.`* `type 'a option = None | Some of 'a`

```
let optionMap f = function
    | None -> None
    | Some (element) -> Some(f element)
;;
```

5. *Recall the making change function of Lecture 4; Function `allc` applies the function `cons a c` to every element of a list. Eliminate it by declaring a curried cons function and applying `map`.*

```
let rec change till amt =
    if amt = 0 then
        [ [] ]
    else
        match till with
        | [] -> []
        | c::till ->
            if amt < c then
                change till amt
            else
                map (fun x -> c::x) (change (c::till) (amt - c)) @ change till amt
;;
```

## Exercise 9

1. *Code an analogue of map for sequences.*
```
let mapSequence f seq =
    match seq with
    | Nil -> Nil
    | Cons (x, xf) ->
        Cons(f x, fun () -> mapSequence f (xf ()))
;;
```

2. *Consider the list function concat, which concatenates a list of lists to form a single list. Can it be generalised to concatenate a sequence of sequences? What can go wrong?*

If any of the sequences are infinite, then any sequences that come after it will be lost. Also, if the sequences are finite, the generating function of the resulting sequence must encode the length of the sub-sequences somehow, which seems difficult.

3. *Code a function to make change using lazy lists, delivering the sequence of all possible ways of making change. Using sequences allows us to compute solutions one at a time when there exists an astronomical number. Represent lists of coins using ordinary lists. (Hint: to benefit from laziness you may need to pass around the sequence of alternative solutions as a function of type unit -> (int list) seq.)*

I don't understand this question.

4. *A lazy binary tree is either empty or is a branch containing a label and two lazy binary trees, possibly to infinite depth. Present an OCaml datatype to represent lazy binary trees, along with a function that accepts a lazy binary tree and produces a lazy list that contains all of the tree’s labels. (Taken from the exam question 2008 Paper 1 Question 5.)*

```
let rec treeToList someTree =
    let rec appendq xq yq =
        match xq with
        | Nil -> yq
        | Cons (x, xf) ->
            Cons(x, fun () -> appendq (xf ()) yq)
    in
    match someTree with
    | Leaf -> Nil
    | Branch (label, branch1, branch2) ->
        appendq (appendq (treeToList (branch1 ())) (Cons (label, fun () -> Nil))) (treeToList (branch2 ()))
;;
```

5. *Code the lazy list whose elements are all ordinary lists of zeroes and ones, namely `[]; [0]; [1]; [0; 0]; [0; 1]; [1; 0]; [1; 1]; [0; 0; 0];...`. (Taken from the exam question 2003 Paper 1 Question 5.)*

```
let rec interleave xq yq =
    match xq with
    | Nil -> yq
    | Cons (x, xf) ->
        Cons (x, fun () -> interleave yq (xf ()))
;;

let rec lazyBinary prev =
    Cons(prev, fun () -> interleave (lazyBinary (0::prev)) (lazyBinary (1::prev)))
;;
```

6. *(Continuing the previous exercise.) A palindrome is a list that equals its own reverse. Code the lazy list whose elements are all palindromes of 0s and 1s, namely `[]; [0]; [1]; [0; 0]; [0; 0; 0]; [0; 1; 0]; [1; 1]; [1; 0; 1]; [1; 1; 1]; [0; 0; 0; 0]; ...`. You may take the reversal function `List.rev` as given.*

```
let rec lazyPalindromes binaryList =
    match binaryList with
    | Nil -> Nil
    | Cons(x0, fx) ->
        Cons(x0 @ (List.rev x0),
            fun () -> Cons(x0 @ (0::(List.rev x0)),
                fun () -> Cons(x0 @ (1::(List.rev x0)),
                    fun () -> lazyPalindromes (fx ())
                )
            )
        )
;;
```

## Exercise 10

1. *Suppose that we have an implementation of queues, based on binary trees, such that each operation takes logarithmic time in the worst case. Outline the advantages and drawbacks of such an implementation compared with one presented above.*

The binary tree approach is better in the worst case, since it has `O(log(n))` complexity in the worst case, as opposed to `O(n)` for the above method. However, in most cases, the binary tree approach will be worse, since it has `O(log(n))` complexity compared to `O(1)` for the above method.

2. *The traditional way to implement queues uses a fixed-length array. Two indices into the array indicate the start and end of the queue, which wraps around from the end of the array to the start. How appropriate is such a data structure for implementing breadth-first search?*

Not very appropriate, since breadth first search uses a queue which has no fixed length. The upper bound on the length could be precomputed beforehand if more information was known about the tree, however this is not likely to be the case.

3. *Write a version of the function `breadth` using a nested `let` construction rather than `match`.*

I don't know what this means.

4. *Iterative deepening is inappropriate if 𝑏≈1, where 𝑏 is the branching factor. What search strategy is appropriate in this case?*

Depth first search

5. *Consider the following OCaml function.*

    `let next n = [2 * n; 2 * n + 1]`

    *If we regard it as representing a tree, where the subtrees are computed from the current label, what tree does `next 1` represent?*

The binary tree with each node labelled in breadth first order.

## Exercise 11

1. *Comment, with examples, on the differences between an `int ref list` and an `int list ref`.*

`int ref list` is a list of references to integers, e.g. `[{content=1};{content=2};{content=3}]`.

`int list ref` is a reference to a list of integers, e.g. `{content=[1;2;3]}`.

2. *Write a version of function `power` (Lecture 1) using while instead of recursion.*

```
let power x n =
    let jN = ref 0
    and product = ref 1
    in
        while not !jN = n do
            product := !product * x;
            jN := !jN + 1
        done;
        !product
;;
```

3. *What is the effect of `while C1; B do C2 done`?*

If `B` is true, keep doing `C2` until `B` is false.

4. *Write a function to exchange the values of two references, `xr` and `yr`.*

```
let swapRefs xr yr =
    let zr = ref !xr
    in
        xr := !yr;
        yr := !zr
;;
```

5. *Arrays of multiple dimensions are represented in OCaml by arrays of arrays. Write functions to (a) create an `n×n` identity matrix, given `n`, and (b) to transpose an `m×n` matrix.*

```
let identityM n =
    Array.init n (fun row ->
        Array.init n (fun col ->
            if col = row then 1 else 0
        )
    )
;;

let transpose m =
    let tempR = ref (Array.get (Array.get m 0) 0)
    and jRow = ref 0
    and jCol = ref 1
    in
        while !jRow < (Array.length m) do
            jCol := !jRow + 1;
            while !jCol < Array.length (Array.get m 0) do
                tempR := Array.get (Array.get m !jRow) !jCol;
                Array.set (Array.get m !jRow) !jCol (
                    Array.get (Array.get m !jCol) !jRow
                );
                Array.set (Array.get m !jCol) !jRow (
                    !tempR
                );
                jCol := !jCol + 1;
            done;
            jRow := !jRow + 1;
        done;
;;
```
