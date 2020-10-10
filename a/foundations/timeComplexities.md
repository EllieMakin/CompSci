# Time Complexity Practice

Show with your own words the complexity of the following recurrence relations.

1. `T(n) = T(n/2) + 1`

```
Assume T(1) = 1

T(n) = T(n/2) + 1
    = (T(n/4) + 1) + 1
    = ((T(n/8) + 1) + 1) + 1
    = T(n/2^k) + k

let 2^k = n

T(n) = T(n/n) + log_2(n)
    = T(1) + log_2(n)
    = log_2(n) + 1
    = O(log(n))
```

2. `T(n) = 2T(n/2) + 1`

```
Assume T(1) = 1

T(n) = 2T(n/2) + 1
    = 2(2T(n/4) + 1) + 1
    = 2(2(2T(n/8) + 1) + 1) + 1
    = (2^k)T(n/2^k) + 2^k - 1

let 2^k = n

T(n) = n*T(n/n) + n - 1
    = n*T(1) + n - 1
    = 2n - 1
    = O(n)
```

3. `T(n) = T(n/2) + n`

```
Assume T(1) = 1

T(n) = T(n/2) + n
    = (T(n/4) + n/2) + n
    = ((T(n/8) + n/4) + n/2) + n
    = T(n/2^k) + 2n - 2n/2^k

let 2^k = n

T(n) = T(n/n) + 2n - 2n/n
    = T(1) + 2n - 2
    = 2n - 1
    = O(n)
```

4. `T(n) = 2T(n/2) + n`

```
Assume T(1) = 1

T(n) = 2T(n/2) + n
    = 2(2T(n/4) + n/2) + n
    = 2(2(2T(n/8) + n/4) + n/2) + n
    = (2^k)(T(n/2^k)) + k*n

let 2^k = n

T(n) = n*T(n/n) + log_2(n)*n
    = n*T(1) + n*log_2(n)
    = n*log_2(n) + n
    = O(n*log(n))
```

5. `T(n) = 2T(n/2 + 2) + n`

```
Assume T(n <= 5) = 1

T(n) = 2T(n/2 + 2) + n
    = 2(2T(n/4 + 1 + 2) + n/2 + 2) + n
    = 2(2(2T(n/8 + 1/2 + 1 + 2) + n/4 + 1 + 2) + n/2 + 2) + n
    = (2^k)T(n/2^k + 4 - 4/2^k) + k*n + 4(2^k - k - 1)

let n/2^k + 4 - 4/2^k = 5
    (n-4)/2^k = 1
    2^k = n-4

T(n) = (n-4)*T(5) + n*log_2(n-4) + 4n - 16 - log_2(n-4) - 4
    = (n-4) + n*log_2(n-4) + 4n - log_2(n-4) - 20
    = n*log_2(n-4) + 5n - log_2(n-4) - 24
    = O(n*log(n))
```

6. `T(n) = T(n/3) + T(2n/3) + n`

```
Assume T(1) = 1

T(n) = T(n/3) + T(2n/3) + n
    = (T(n/9) + T(2n/9) + n/3) + (T(2n/9) + T(4n/9) + 2n/3) + n
    = T(n/9) + 2T(2n/9) + T(4n/9) + 2n
    = sum[r=0->k](kCr T(2^r*n/3^k)) + something

Gonna guess this one is similar to (4), since it consists of double logarithms, and say

T(n) = O(n*log(n))
```

7. `T(n+1) = T(n)`

```
Assume T(1) = 1

T(n) = T(n-1)
    = T(n-2)
    = T(n-3)
    = T(n-k)

let k = n

T(n) = T(1)
    = 1
    = O(1)
```

8. `T(n) = 2T(√n) + log_2(n)` (there is a trick to get this one)

```
Assume T(n <= 2) = 1

T(n) = 2T(√n) + log_2(n)
    = 2(2T(n^(1/4)) + log_2(n^(1/2))) + log_2(n)
    = 2(2(2T(n^(1/8)) + log_2(n^(1/4))) + log_2(n^(1/2))) + log_2(n)
    = (2^k)T(n^(1/2^k)) + k*log_2(n)

let n^(1/2^k) = 2
    log_2(n)/2^k = 1
    2^k = log_2(n)

T(n) = log_2(n)*T(2) + log_2(log_2(n))*log_2(n)
    = log_2(n) * log_2(log_2(n))*log_2(n)
    = O(log(log(n))*log(n))
```
