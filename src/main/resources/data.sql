INSERT INTO league (id, league_name) VALUES (1, 'Premier');
INSERT INTO team (id, team_name, league_id) VALUES (1, 'Manchaster', 1);
SELECT (wins, defeats, draws) FROM balance_of_matches INNER JOIN team ON team.balance_id = balance_of_matches.team_id;
INSERT INTO balance_of_matches (balance_id , wins, defeats, draws) VALUES (3 , 1, 2, 3);
INSERT INTO player (id, name, age, team_id) VALUES (1, 'ronaldo', 33, 1);