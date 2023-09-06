CREATE DATABASE ResultTrackerCASSANDRA;
USE ResultTrackerCASSANDRA;
CREATE TABLE Players (
	player_ID BIGINT NOT NULL PRIMARY KEY,
    tag VARCHAR(20) NOT NULL,
    game_race CHAR(1) CHECK (game_race IN ('P', 'T', 'Z')) NOT NULL,
    nationality VARCHAR(2) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL);
    
CREATE TABLE Teams(
	team_ID INT NOT NULL PRIMARY KEY,
    team_name VARCHAR(50) NOT NULL,
    founded DATE NOT NULL,
    disbanded DATE);
    
CREATE TABLE Members(
	player_ID BIGINT NOT NULL REFERENCES Players(player_ID) ON DELETE CASCADE ON UPDATE CASCADE,
    team_ID INT NOT NULL REFERENCES Teams(team_ID) ON DELETE CASCADE ON UPDATE CASCADE,
    start_date DATE NOT NULL,
    end_date DATE,
    PRIMARY KEY (player_ID, team_ID, start_date));
    
CREATE TABLE Tournaments(
	tournament_ID BIGINT NOT NULL PRIMARY KEY,
    tournament_name VARCHAR(200) NOT NULL,
    region VARCHAR(2),
    major VARCHAR(6) CHECK (major IN ('false', 'true')) NOT NULL);
    
CREATE TABLE Matches(
	match_ID INT NOT NULL PRIMARY KEY,
    tournament_ID BIGINT NOT NULL REFERENCES Tournaments(tournament_ID) ON UPDATE CASCADE,
    playerA_ID BIGINT NOT NULL REFERENCES Players(player_ID) ON UPDATE CASCADE,
    playerB_ID BIGINT NOT NULL REFERENCES Players(player_ID) ON UPDATE CASCADE,
    playerA_score TINYINT NOT NULL,
    playerB_score TINYINT NOT NULL,
    match_date DATE NOT NULL,
    is_offline VARCHAR(6) CHECK (is_offline IN ('false', 'true')) NOT NULL);
    
CREATE TABLE Earnings(
	player_ID BIGINT NOT NULL REFERENCES Players(player_ID) ON UPDATE CASCADE,
    tournament_ID BIGINT NOT NULL REFERENCES Tournaments(tournament_ID) ON UPDATE CASCADE,
    position INT NOT NULL,
    prize_money INT NOT NULL,
    PRIMARY KEY (player_ID, tournament_ID));
    

    
