USE ResultTrackerCASSANDRA;
select real_name, game_race, tournament_name
FROM tournaments INNER JOIN
	(select real_name, game_race, winnersIDs.tournament_ID
	FROM Players INNER JOIN
		(select tournament_ID, winners.ID from
			(select tournament_ID, IF(playerA_score-playerB_score > 2, playerA_ID, NULL) AS ID 
            FROM matches
			union
			select tournament_ID, IF(playerB_score-playerA_score > 2, playerB_ID, NULL) AS ID 
            FROM matches) AS winners
		WHERE winners.ID IS NOT NULL) AS winnersIDs
	ON Players.player_ID = winnersIDs.ID) AS B
ON tournaments.tournament_ID = B.tournament_ID;


