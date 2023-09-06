USE ResultTrackerCASSANDRA;
SELECT tag, real_name, nationality, team_name
FROM 
	(SELECT tag, real_name, nationality, team_id
	FROM Players 
    INNER JOIN Members
	ON Players.player_ID = Members.player_ID
	WHERE Players.game_race IN ('Z') AND Members.end_date IS NULL) AS Zplayers
INNER JOIN Teams
ON Zplayers.team_id = Teams.team_id
WHERE Teams.disbanded IS NULL;

