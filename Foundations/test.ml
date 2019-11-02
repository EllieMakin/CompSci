let swapRefs xr yr =
    let zr = ref !xr
    in
        xr := !yr;
        yr := !zr
;;

let identityM n =
    Array.init n (fun row ->
        Array.init n (fun col ->
            if col = row then 1 else 0
        )
    )
;;

let transpose m =
    let tempR = ref (Array.get (Array.get m 0) 0)
    and jRow = ref 0
    and jCol = ref 1
    in
        while !jRow < (Array.length m) do
            jCol := !jRow + 1;
            while !jCol < Array.length (Array.get m 0) do
                tempR := Array.get (Array.get m !jRow) !jCol;
                Array.set (Array.get m !jRow) !jCol (
                    Array.get (Array.get m !jCol) !jRow
                );
                Array.set (Array.get m !jCol) !jRow (
                    !tempR
                );
                jCol := !jCol + 1;
            done;
            jRow := !jRow + 1;
        done;
;;
