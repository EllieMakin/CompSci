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
    ```
    *(b) add/subtract some given number of years from another year.*
    ```
    let addYears startYear nYears =
        (startYear + nYears) mod 100
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
```

6. *Another example of the inaccuracy of floating-point arithmetic takes the golden ratio `𝜙 ≈ 1.618…` as its starting point: `𝛾[0] = (1 + sqrt(5))/2` and `𝛾[n+1] = 1/(𝛾[n] − 1)`.
In theory, it is easy to prove that `𝛾[n] = ⋯ = 𝛾[1] = 𝛾[0]` for all `n > 0`. Code this computation in OCaml and report the value of 𝛾[50].*

```
let rec goldenFloat n =
    if n > 0 then 1.0 /. (goldenFloat (n-1) -. 1.0)
    else (1.0 +. (sqrt 5.0)) /. 2.0
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
```

Iterative:
```
let sumListIter someList total =
    match someList with
    | [] -> total
    | first :: rest -> sumListIter rest (total + first)
```

The iterative version is more efficient, because it does not have to store each of the items in `someList` on the stack - instead they are added as it goes.

2. *Code a function to return the last element of a non-empty list. How efficiently can this be done?*

```
let lastItem someList =
    match someList with
    | [] -> raise (Failure "There is no last item in an empty list")
    | only :: [] -> only
    | first :: rest -> lastItem rest
```
The best this can be done is `O(n)`, because the list must be traversed in order to access the last item.

3. *Code a function to return the list consisting of the even-numbered elements of the list given as its argument. For example, given `[a; b; c; d]` it should return `[b; d]`.*

```
let rec getEvens someList =
    match someList with
    | [] -> []
    | only :: [] -> []
    | first :: second :: rest -> second :: getEvens rest
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
```

3. *How does this version of zip differ from the one above?*

    ```
    let rec zip xs ys =
        match xs, ys with
        | (x::xs, y::ys) -> (x, y) :: zip xs ys
        | ([], [])   -> []
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
```

This type may be fairly practical in a calendar application, since the type definition will save space compared to string storage of days.

2. *Write an OCaml function taking a binary tree labelled with integers and returning their sum.*

```
let rec sumTree someTree =
    match someTree with
    | Lf -> 0
    | Br (vertex, tree1, tree2) ->
        vertex + (sumTree tree1) + (sumTree tree2)
```    

3. *Using the definition of 'a tree from before: `type 'a tree = Lf | Br of 'a * 'a tree * 'a tree`, examine the following function declaration. What does `ftree (1, n)` accomplish?*

    ```
    let rec ftree k n =
        if n = 0 then
            Lf
        else
            Br (k, ftree (2 * k) (n - 1), ftree (2 * k + 1) (n - 1))
    ```

`ftree (1, n)` constructs a binary tree with `n` levels, which represents all binary nubers with `n` digits.

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
```    
