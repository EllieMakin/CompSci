# Databases

## Reading

- Lemahieu, W., Broucke, S. van den, and Baesens, B. Principles ofdatabase management. Cambridge University Press. (2018)

## Database Management System (DBMS)

A database management system (DBMS) is a software package designed to define, manipulate, retrieve and manage data in a database. A DBMS generally manipulates the data itself, the data format, field names, record structure and file structure. It also defines rules to validate and manipulate this data.

A DBMS relieves users of framing programs for data maintenance. Query languages, such as SQL, are used along with the DBMS package to interact with a database.

### CRUD operations

The CRUD operations are the four basic functions of persistent storage.

- **Create** - Insert new data items into the database.

- **Read** - Query the database.

- **Update** - Modify objects in the database.

- **Delete** - Remove data from the database.

### ACID transactions

ACID is a set of properties of database transactions intended to guarantee validity even in the event of errors, power failures, etc.

- **Atomicity** - Either all actions of a transaction are carried out, or none are.

- **Consistency** - Every transaction applied to a valid database leaves it in a valid state.

- **Isolation** - Transactions are isolated from the effects of other concurrently executed transactions.

- **Durability** - If a transaction completes successfully, then its effects persist.

### Data Models

This course will look at 3 data models:

- Relational Model - Data is stored in tables. SQL is the main query language.
- Graph-oriented Model - Data is stored as a graph (nodes and edges). Query languages tend to have “path-oriented” capabilities.
- Aggregate-oriented Model - Also called document-oriented database. Optimised for read-oriented databases.

The relational model has been the industry mainstay for the last 35 years, but there is no one system that nicely solves all data problems.

## Redundancy

Informally, data in a database is redundant if it can be deleted and then reconstructed from the data remaining in the database.

Data redundancy is problematic for some applications, but highly desirable for others. For example, if a database supports concurrent updates, then data redundancy leads to problems, since all data relevant to one query must be locked from all the others. However, precomputing the results of common queries can greatly reduce response time.

Types of anomalies that can arise due to data redundancy:

- Insertion anomalies - How can we tell if a newly inserted record is consistent with all other records? We may want to insert a person without knowing if they are a director, or insert a movie without knowing its director(s).

- Deletion anomalies - We will wipe out information about people when the last record containing them is deleted from this table.

- Update anomalies:  What if a director’s name is misspelled? We may update it correctly for one movie but not for another.

## Entity Relationship Diagrams (ER Diagrams)

Below is an example of an ER diagram.

![erDiagram1](notesImages/erDiagram1.png)

- **Entities** are represented by **rectangles**. Each entity should have an `id` attribute, and maybe a few others.

- **Attributes** are represented by **ovals**. The attributes which uniquely identify an entity are called *keys*, underlined in the diagram.

- **Relationships** are represented by **diamonds**. They define some relationship between two entities, which can be one-to-one, one-to-many, many-to-one etc.

One-to-`n` relationships are represented by an arrow directed towards `n` (this can be in both directions).

### Weak entities and sub-entities

A weak entity is an entity whose existence depends on the existence of another entity. For example, the entity `Movie Release` depends on the existence of a correponding `Movie`:

![weakEntities](notesImages/weakEntities.png)

Entities may also have *sub-entities*, which inherit the attributes and relationships of the parent entity, as well as having some of their own.

Below is an example of a much more developed ER diagram, based on the dynamics of a car repair shop.

![carRepairERDiagram](notesImages/carRepairERDiagram.png)

## Relational Algebra (RA)

Suppose that `S` and `T` are sets. The Cartesian product `S×T`, is the set `S×T = {(s,t)|s∈S,t∈T}`. A (binary) relation over `S×T` is any set `R` with `R ⊆ S×T`.

| S   | T   |
| --- | --- |
| u   | v   |
| w   | x   |
| ... | ... |
| y   | z   |

If we have `n` sets (domains) `S1, S2, ..., Sn`, then an `n`-ary relation `R` is a set `R ⊆ S1×S2×...×Sn = {(s1,s2, ...,sn)|si∈Si}`.

| S1  | S2  | ... | Sn  |
| --- | --- | --- | --- |
| o   | p   | ... | q   |
| r   | s   | ... | t   |
| ... | ... | ... | ... |
| u   | v   | ... | w   |

In a database, we associate a name, `Ai` (called an *attribute name*) with each domain `Si`. Instead of tuples, we use records — sets of pairs each associating an attribute name `Ai` with a value in domain `Si`.

A database relation `R` is a finite set `R ⊆ {{(A1,s1),(A2,s2), ...,(An,sn)}|si∈Si}` We specify `R`’s schema as `R(A1:S1,A2:S2,···An:Sn)`. For example, a relational schema:

`Students(name: string, sid: string, age: integer)`

can be implemented like this:

```
Students = {
    {(name,Fatima),(sid,fm21),(age,20)},
    {(name,Eva),(sid,ev77),(age,18)},
    {(name,James),(sid,jj25),(age,19)}
}
```

An equivalent tabular representation:

| name   | sid  | age |
| ------ | ---- | --- |
| Fatima | fm21 | 20  |
| Eva    | ev77 | 18  |
| James  | jj25 | 19  |

### Query Language

Let `Q(R1, R2, ... Rk)` be the result of some query `Q` on a collection of relational instances, i.e. A set of tables. `Q` should return a single relational instance `Rq`.

```
Q ::=
    | R         base relation
    | σ[p](Q)   selection
    | π[X](Q)   projection
    | Q × Q     product
    | Q - Q     difference
    | Q ∪ Q     union
    | Q ∩ Q     intersection
    | ρ[M](Q)   renaming
```
- `Q` is the result of some other query.

- `p` is a simple boolean predicate over attributes values.

- `X = {A1,A2, ...,Ak}` is a set of attributes.

- `M = {A1->B1, A2->B2, ..., Ak->Bk}` is a renaming map.

A query `Q` must be *well-formed*: all column names of result are distinct. So in `Q1 × Q2`, the two sub-queries cannot share any column names; in `Q1 ∪ Q2`, the two sub-queries must share all column names.

We can translate relational algebra directly into SQL, for example:

```
Selection
RA:     σ[A>12](R)
SQL:    SELECT DISTINCT * FROM R WHERE R.A > 12;

Projection
RA:     π[B,C](R)
SQL:    SELECT DISTINCT B, C FROM R;

Renaming
RA:     ρ{B->E,D->F}(R)
SQL:    SELECT A, B AS E, C, D AS F FROM R;

Union
RA:     R ∪ S
SQL:    (SELECT * FROM R) UNION (SELECT * FROM S);

Intersection
RA:     R ∩ S
SQL:    (SELECT * FROM R) INTERSECT (SELECT * FROM S);

Difference
RA:     R-S
SQL:    (SELECT * FROM R) EXCEPT (SELECT * FROM S);

Product
RA:     R × S
SQL:    SELECT A, B, C, D FROM R CROSS JOIN S;
```

### Natural Join

Given `R(A,B)` and `S(B,C)`, we define the natural join, denoted `R ⨝ S`, as a relation over attributes `A`, `B`, `C` defined as

`R ⨝ S ≡ { t | ∃ u∈R, v∈S, u.[B] = v.[B] ∧ t = u.[A] ∪ u.[B] ∪ v.[C] }`

In the relational algebra, this can be written:

`R ⨝ S = π[A,B,C](σ[B=B′](R × ρ[B->B′](S)))`

## Implementation

Both ER entities and ER relationships are implemented as tables. The positive of this approach is that we avoid redundancy and thus update anomalies by breaking tables into smaller tables. The negative is that we have to work hard to combine information in the tables to produce interesting results.

To ensure that one of our tables is genuinely representing a relationship, we use *keys* and *foreign keys*.

### Keys

Suppose `R(X)` is a relational schema with `Z⊆X`. If for any records `u` and `v` in any instance of `R` we have `u`. `[Z] = v.[Z] => u.[X] = v.[X]`, then `Z` is a superkey for `R`. If no proper subset of `Z` is a superkey, then `Z` is a key for `R`.

Suppose we have `R(Z,Y)`. Furthermore, let `S(W)` be a relational schema with `Z⊆W`. We say that `Z` represents a *foreign key in `S` for `R`* if for any instance we have `π[Z](S) ⊆ π[Z](R)`. Think of these as (logical) pointers!
