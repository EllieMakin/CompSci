type expression =
    | Real of float
    | Var of string
    | Sub of expression
    | Add of expression * expression
    | Mult of expression * expression
;;

let rec evaluate someExpression =
    match someExpression with
    | Real (value) -> value
    | Var (name) -> raise (Failure name)
    | Sub (a) -> 0.0 -. (evaluate a)
    | Add (a, b) -> (evaluate a) +. (evaluate b)
    | Mult (a, b) -> (evaluate a) *. (evaluate b)
;;

let testExpression =
Mult(
    Add(
        Real(2.0),
        Sub(
            Real(3.0)
        )
    ),
    Real(4.0)
)
;;
