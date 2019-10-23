# Databases

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

## Data Models

This course will look at 3 data models:

- Relational Model - Data is stored in tables. SQL is the main query language.
- Graph-oriented Model - Data is stored as a graph (nodes and edges). Query languages tend to have “path-oriented” capabilities.
- Aggregate-oriented Model - Also called document-oriented database. Optimised for read-oriented databases.

The relational model has been the industry mainstay for the last 35 years.
