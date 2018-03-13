INSERT INTO nationality (id, nationality) VALUES (1, 'England');
INSERT INTO nationality (id, nationality) VALUES (2, 'Polska');

INSERT INTO league (id, league_name, nationality_id, difficulty) VALUES (1, 'Premier', 1, 'HARD');
INSERT INTO balance_of_matches(id, wins, draws, defeats) VALUES (1, 2, 3 ,4);
INSERT INTO team (id, name, league_id, balance_id, nationality_id, power) VALUES (1, 'Manchaster', 1, 1, 1, 'SEMI_PRO');
INSERT INTO player (id, name, age, team_id) VALUES (1, 'ronaldo', 33, 1);
