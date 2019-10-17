select p.name as name, pr.role as role, count(*) as movie_count
  from plays_role as pr
  join people as p on p.person_id = pr.person_id
  join movies as m on m.movie_id = pr.movie_id
  where m.type = 'movie'
group by name, role
order by movie_count desc
limit 10;
