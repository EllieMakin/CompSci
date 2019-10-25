let rec r1_encode someList =
    match someList with
    | [] -> []
    | head :: tail -> (
        match r1_encode tail with
        | [] -> [(1, head)]
        | (count, previous) :: rest ->
            if head = previous then
                (count + 1, previous) :: rest
            else
                (1, head) :: (count, previous) :: rest
    )
;;

let rec r2_encode someList =
    let rec addToEncoded element encodedList =
        match encodedList with
        | [] -> [(1, element)]
        | (count, value) :: tail ->
            if element = value then
                (count + 1, value) :: tail
            else
                (count, value) :: addToEncoded element tail
    in
    match someList with
    | [] -> []
    | head :: tail -> (
        addToEncoded head (r2_encode tail)
    )
;;
