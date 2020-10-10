match
    (:Movie {title: "The Matrix Reloaded"})
     <-[r1: ACTED_IN]-
    (p: Person)
    -[r2: ACTED_IN]->
    (:Movie {title:"John Wick"})
return p.name as name, r1.roles as roles1, r2.roles as roles2
order by name, roles1, roles2;
