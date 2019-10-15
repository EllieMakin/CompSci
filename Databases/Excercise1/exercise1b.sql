select p.name as name, pr.role as role, count(*) as movie_count
  from plays_role as pr
  join people as p on p.person_id = pr.person_id
group by name, role
order by movie_count desc
limit 10;
