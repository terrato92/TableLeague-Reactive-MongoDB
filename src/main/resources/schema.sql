CREATE table league
(
  id INTEGER not NULL,
  league_name VARCHAR(255) NOT NULL,
  team_id INTEGER NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE team
(
  id INTEGER not NULL,
  team_name VARCHAR(255) NOT NULL,
  balance_of_matches_id INTEGER NOT NULL,
  league_id INTEGER NOT NULL,
  image INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE balance_of_matches
(
  id INTEGER NOT NULL,
  wins INTEGER,
  defeats INTEGER,
  draws INTEGER,

);

