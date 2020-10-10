type nested_list =
    | Atom of int
    | Nest of nested_list list
;;

let n = Nest([
    Nest([
        Atom 3;
        Atom 4
    ]);
    Atom 5;
    Nest([
        Atom 6;
        Nest([
            Atom 7
        ]);
        Atom 8
    ]);
    Nest([])
])
;;

let rec flatten nl =
    match nl with
    | Atom (i) -> [i]
    | Nest ([]) -> []
    | Nest (n::ns) ->
        (flatten n) @ (flatten (Nest(ns)))
;;

let rec nested_map f n =
    match n with
    | Atom(i) -> Atom (f i)
    | Nest([]) -> Nest([])
    | Nest(n0::ns) ->
        Nest((nested_map f n0) :: (List.map (nested_map f) ns))
;;

let rec pack_as x y =
    let rec subPack r l nl =
        match nl with
        | Atom(i) ->
            r := !r + 1;
            Atom(List.nth l !r)
        | Nest([]) -> Nest([])
        | Nest(n0::ns) ->
            let packed0 = subPack r l n0
            in
            Nest(packed0 :: (List.map (subPack r l) ns))
    and j = ref (~-1)
    in
    subPack j x y
;;

type nested_zlist = ZAtom of int
    | ZNest of (unit -> nested_zlist list)
;;

let rec zlistToNlist zl =
    match zl with
    | ZAtom(i) -> Atom(i)
    | ZNest(f) -> Nest(List.map zlistToNlist (f ()))
;;
