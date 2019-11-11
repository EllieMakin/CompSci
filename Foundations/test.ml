type peano =
    | Z
    | S of peano
;;

type binary =
    bool list
;;

type 'a church =
    | X of 'a
    | F of (unit -> 'a church)
;;

let rec intFromPeano p =
    match p with
    | Z -> 0
    | S (pn) -> 1 + intFromPeano pn
;;

let rec intFromBinary b =
    match b with
    | [] -> 0
    | d::ds ->
        (if d then 1 else 0) + 2 * (intFromBinary ds)
;;

let rec intFromChurch c =
    match c with
    | X (_) -> 0
    | F (f) -> 1 + (intFromChurch (f ()))
;;

let rec addPeano m n =
    match m with
    | Z -> n
    | S(m1) -> addPeano m1 (S (n))
;;

let rec addBinary m n =
    let rec subAddBinary x y cIn =
        let a = if x = [] then [false] else x
        and b = if y = [] then [false] else y
        in
        if x = [] && y = [] then
            [cIn]
        else
            match a, b with
            | x0::xs, y0::ys ->
                let sum = x0 <> y0 <> cIn
                and cOut = (x0 && y0) || (x0 && cIn) || (y0 && cIn)
                in
                sum :: (subAddBinary xs ys cOut)
            | _, _ -> []
    in
    subAddBinary m n false
;;

let rec addChurch m n =
    match m with
    | X(_) -> n
    | F(f) -> addChurch (f ()) (F(fun () -> n))
;;
