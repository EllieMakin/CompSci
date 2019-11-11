# Databases Supervision work 2

## Question 1

*a) In the database context, what do we mean by redundant data? [1 mark]*

Data which can be deleted, and reconstructed from the remaining data.

*b) Why might it be a good idea to have redundant data in a database? [2 marks]*

To speed up read times, so values do not have to recomputed or tables joined on each request.

*c) Why might it be a bad idea to have redundant data in a database? [2 marks]*

It slows down update times, since all data related to the update must be edited. Also, if the database supports concurrent operations, all the data related to the update must be locked, which is time-expensive.

*d) Suppose a database has tables `R(A, B)` and `S(B, C)`. Explain how using an index could improve performance when joining `R` and `S`. Is there a downside to using an index? [4 marks]*

To join `R` and `S` without an index, each row in `R` must be checked with each row in `S` to see if there is a match, despite the fact that in many cases, only a small number of rows in `S` will match. This has complexity `size(R) * size(S)`. Using an index, we can reduce the number of items in `S` that we have to check for each row in `R`, which reduces the complexity of the join. The downside to using an index, is that it takes time to generate initially, and also takes up space to store it.

*e) In SQL, what could be returned when evaluating the following expression? `NOT (a OR (NOT a))` [2 marks]*

`False`, if `a = TRUE` or `a = FALSE`, or `NULL` if `a = NULL`:

```
Let a = NULL

NOT (a OR (NOT a)) = NOT (NULL OR (NOT NULL))
    = NOT(NULL OR NULL)
    = NOT(NULL)
    = NULL
```

*f) Suppose `R(start, end)` is a table in a relational database representing arcs in a directed graph. That is, each record `(x, y)∈R` represents an arc from node `x` to node `y`.*

- *(i) Write an SQL query that returns the start and end of all 3-hop paths in the directed graph represented by `R`. Your query should return columns named `start`, `end`. Each row `(x, y)` in the result of your query should indicate that there exists a path in `R`, `x -> z -> u -> y` for some nodes `z` and `u`. [4 marks]*

```
select r1.start, r3.end
    from R as r1
    join R as r2 on r1.end = r2.start
    join R as r3 on r2.end = r3.start
;
```

- *(ii) What is the transitive closure of `R`?  Why is this difficult to compute in SQL if we ignore recursive query constructs? [5 marks]*

The transitive closure of `R` is the smallest binary relation on `S` such that `R⊆R+` and `R+` is *transitive*: `(x,y)∈R+ ∧ (y,z)∈R+ => (x,z)∈R+`. This is hard to compute in SQL, since the paths could be of any length, so there is no upper bound on the number of joins that we would have to do.

## Question 2

Suppose that we have a relational database with the following tables.

| Table                      | Primary Key      |
| -------------------------- | ---------------- |
| `Movies(mid, title, year)` | `mid`            |
| `People(pid, name)`        | `pid`            |
| `Genres(gid, genre)`       | `gid`            |
| `ActsIn(pid, mid, role)`   | `pid, mid, role` |
| `HasGenre(gid, mid)`       | `gid, mid`       |

In table `ActsIn`, `pid` is a foreign key into `People` and `mid` is a foreign key into `Movies`. In table `HasGenre`, `mid` is a foreign key into `Movies` and `gid` is a foreign key into `Genres`. Note that this database is similar to, but not the same as, the examples used in lectures and the database used for practicals.

*a) Suppose that the attribute `role` was not considered part of the key for table `ActsIn`. How would this change your interpretation of the database? [2 marks]*

It would mean that a person could only play on role in any film, since the row including that person and that movie would only occur once, i.e. with only one role.

*b) Suppose we replaced the tables `Genres(gid, genre)` and `HasGenre(gid, mid)` with a single table `MovieGenres(mid, genre)`. Would this change what data can be captured in the database? Explain your answer. [2 marks]*

This would not change the data that could be captured in the database, since each `gid` can simply be replaced by a `genre`.

*c) Write an SQL query that returns `title, mid`, for those movies that are not associated with any genre. (Use the schema at the top of the page, not the possible modifications discussed in (a) or (b).) [4 marks]*

```
select distinct m.title, m.mid
    from Movies as m
    join HasGenre as hg on hg.mid = m.mid
where hg.gid is null
;
```

*d) Write an SQL query that returns `name, pid`, for those people that act in at least one movie associated with the genre 'Drama'. [5 marks]*

```
select distinct p.name, p.pid
    from People as p
    join ActsIn as ai on p.pid = ai.pid
    join HasGenre as hg on hg.mid = ai.mid
where hg.genre = 'Drama'
;
```

*e) Write an SQL query that returns `title, mid, genre`, for those movies that have `genre` as their only genre. That is, if the query returns the row `| 'The Big Hoot' | 947837 | 'Comedy' |` it means that this movie is associated only  with the genre 'Comedy' and no other genre. [7 marks]*

```
select m.title, m.mid, hg.genre
    from (
        select mid, genre, count(*) as nGenres
            from HasGenre
            group by mid
        ) as hg
    join Movies as m on m.mid = hg.mid
where hg.nGenres = 1
;
```
