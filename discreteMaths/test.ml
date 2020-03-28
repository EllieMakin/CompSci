let divalg m n =
    let rec diviter q r =
        if r < n then
            (q, r)
        else
            diviter (q+1) (r-n)
    in
        diviter 0 m
;;

let rec gcd m n =
    let (q, r) = divalg m n
    in
        if r == 0 then
            n
        else
            gcd n r
;;

let egcd m n =
    let rec egcditer ((s1, t1), r1) ((s2, t2), r2) =
        (* r = r1 - q*r2 *)
        let (q, r) = divalg r1 r2
        in
            if r = 0 then
                ((s2, t2), r2)
            else
                egcditer ((s2, t2), r2) ((s1 - q*s2, t1 - q*t2), r)
    in
        egcditer ((1, 0), m) ((0, 1), n)
;;
