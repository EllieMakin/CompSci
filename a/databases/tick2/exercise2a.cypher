match
    (p: Person)
    -[:ACTED_IN]->
    (:Movie)
return p.name as name, count(*) as total
order by total desc, name
limit 10;
