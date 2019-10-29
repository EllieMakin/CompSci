select distinct p.name as name, m.year - p.deathYear as gap
  from has_position as hp
  join people as p on p.person_id = hp.person_id
  join movies as m on m.movie_id = hp.movie_id
  where m.year > p.deathYear
order by gap desc, p.name
limit 10;
