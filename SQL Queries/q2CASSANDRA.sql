USE ResultTrackerCASSANDRA;
SELECT tournament_name, region, Summed.total_prize
FROM
	(SELECT tournament_name, region, SUM(prize_money) AS total_prize
	FROM tournaments INNER JOIN earnings
	ON tournaments.tournament_ID = earnings.tournament_ID
	GROUP BY earnings.tournament_ID) AS Summed
WHERE Summed.total_prize >= 10000
ORDER BY Summed.total_prize DESC;

