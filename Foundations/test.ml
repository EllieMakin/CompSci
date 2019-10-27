type 'a sequence =
    | Nil
    | Cons of 'a * (unit -> 'a sequence)
;;

let rec get n s =
  if n = 0 then
    []
  else
    match s with
    | Nil -> []
    | Cons (x, xf) -> x :: get (n-1) (xf ())
;;

let rec zeroOnes x =
    let rec next someList =
        match someList with
        | [] -> [0]
        | [0] -> [1]

        | [1] -> []

        | 0 :: rest ->

        | 1 :: rest ->
    in
    Cons (x, fun () -> zeroOnes (next x))
