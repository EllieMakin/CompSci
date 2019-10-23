let rec bubbleSort someList =
    let rec bubblePass checked toCheck =
        match toCheck with
        | [] -> 
