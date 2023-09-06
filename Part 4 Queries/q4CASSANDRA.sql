USE ResultTrackerCASSANDRA;
select tag, game_race 
FROM (select DISTINCT player_ID FROM
	(select DISTINCT player_ID, A.region
	FROM earnings 
	INNER JOIN (select tournament_ID, region FROM tournaments WHERE major IN ('true')) AS A 
	ON earnings.tournament_ID = A.tournament_ID
WHERE earnings.position = 1) AS B
GROUP BY B.player_ID HAVING COUNT(B.player_ID) > 2) AS C
INNER JOIN players 
ON C.player_ID = players.player_ID;


