USE ResultTrackerCASSANDRA;

SELECT teams.team_name, teams.founded, A.Zerg, A.Protoss, A.Terran FROM
(SELECT team_name, COUNT(game_race IN ('Z')) AS Zerg, COUNT(game_race IN ('P')) AS Protoss, COUNT(game_race IN ('T')) AS Terran
FROM players JOIN members USING (player_ID) JOIN teams USING (team_id)
WHERE teams.disbanded IS NULL AND YEAR(teams.founded) < 2011
GROUP BY team_name) AS A
JOIN teams USING (team_name)
ORDER BY team_name;