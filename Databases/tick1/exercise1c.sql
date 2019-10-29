select r1.role as role, p.name as name, m1.title as movie_title, m2.title as tv_movie_title
  from people as p
    join plays_role as r1 on r1.person_id = p.person_id
    join plays_role as r2 on r2.person_id = p.person_id
    join movies as m1 on m1.movie_id = r1.movie_id
    join movies as m2 on m2.movie_id = r2.movie_id
  where r1.role = r2.role
    and m1.type = 'movie'
    and m2.type = 'tvMovie'
  order by r1.role, p.name, m1.title, m2.title;
