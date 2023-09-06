# StarCraft-II-Tournaments-Results-DB
This is a database designed for tracking various StarCraft II tournament results. The goal of the project was to design and build a database with a given dataset from scratch, then write a series of complex SQL statements to retrieve data.

## Project instructions:

### Overview
You will realize a practical database design and implementation based on a given dataset. Within the MySQL database management system, you will write and run queries on it, and access it programmatically using Java Database Connectivity (JDBC), OR a connector of your choice.


The tasks that you will accomplish in this project are as follows: 
\begin{itemize}
\item {design a database based on a given dataset}
\item {convert design into schemas}
\item {create a database using DDL statements in the mysql command line tool}
\item {populate the database row-by-row (in loops) from provided raw data using JDBC}
\item {write SQL queries to the database to be executed from the mysql command line tool}
\item {query and manipulate the database programmatically using JDBC}
\item {completely remove all traces of your database from the DBMS}
\end{itemize}

In short, StarCraft II is a popular video game that allows top players to make tons of money through tournaments. Your job is to implement a database backend for a tournament results tracker using MySQL.


Obviously, such a system will need to keep track of various players; each player has a tag, or nickname that they use in the game, along with their real name. As neither is guaranteed to be unique, an artificial, unique player ID is introduced here. Also recorded are the date of birth of the player, their nationality (using a two-letter country code), and the “race” that they typically play as within the game, indicated using a single letter representing Protoss, Terran or Zerg. The system also stores information about professional gaming teams, both past and present: the team name and date of founding; for former teams, the date of disbandment is also stored. Players’ membership in teams are recorded with the start date, and where applicable, the end date (if the player is currently still on the team, there is no end date). The application keeps track of the name of each tournament and the region that it was played in (represented by a two-letter code). Some tournaments are flagged as a “major” event (generally characterized by large amounts of prize money and participation of strong players). The most important part of the system, of course, records the results of each match: the date and tournament at which it was played and the two players in the match. A match consists of one or more games; the score of each player represents the number of games that they each won in that match, and the player who wins more games is the match winner. A flag indicates if the match was “offline”, i.e. it was played in-person at a physical event (as opposed to “online” over the Internet). Finally, we keep track of the amount of prize money (in USD) that players win at tournaments. 

### Part 1: Design database
I will provide you with a small sample of the dataset. Design the database schema. Start with an E/R diagram and convert it to a relational schema. Identify any constraints that hold in your application domain, and code them as database constraints. It is important to go over the samples of real data to validate your design. Do not forget to apply database design theory and check for redundancies. 

### Part 2: Creating the database tables in MySQL
Once your schema has been validated, prepare and submit a createdbYOURNAME.sql file containing SQL statements that will create a database ResultTrackerYOURNAME containing the tables listed in your schema. Primary keys and foreign keys must be implemented. Choose the most appropriate data type for each field by referring to the domain description and consulting the sample data first.

### Part 3: Inserting data to the database through JDBC
You will be given the following files, which contain data for the respective tables you created.
\begin{itemize}
\item{players.csv}
\item{teams.csv}
\item{members.csv}
\item{tournaments.csv}
\item{matches.csv}
\item{earnings.csv}
\end{itemize}
These are standard comma-separated value files, which you may open and examine using any standard text editor. Observe that each row represents one record, and fields are comma-separated. You need to examine these data to make sure that you define the constraints of certain attributes - such as not null, on default.
You are required to implement a set of Java programs that accept table name and csv file names as command line arguments, open and parse each file and insert the data contained within them into your database. For each table and each csv file, create a separate .java file to parse each file and insert into its corresponding table. In this case, you will need to create 6 java files and name them as follows:
\item PlayersInsert.java
\item TeamsInsert.java
\item TournamentsInsert.java
\item MatchesInsert.java
\item EarningsInsert.java
\item MembersInsert.java

You should use the JDBC PreparedStatement for greater efficiency as compared to the more generic Statement class and to reduce the risk of SQL injection.

### Part 4: SQL queries on the database

Write the following queries in SQL and run them on your MySQL database via the mysql command line tool. Prepare and submit them as separate files q1YOURNAME.sql to q6YOURNAME.sql. If two or more SQL statements are needed for a single question, they should be written after each other in one file. 

Q1. List the real name and birthday of each non-Korean (“KR”) player who was born in 1985. 

Q2. Give the tag, real name, and nationality of all Zerg (Z) players that are currently on a team, along with the name of their current team. 

Q3. List the tournaments that give out USD 10,000 or more of total prize money. For each tournament, give the name, region, and the total prize. Sort your results with the tournament with the most prize money first. 

Q4. Give the real name, game race and tournament name of all players that have over scored their opponent by at least 3 points in any matches. To clarify, you should return the player who won the match, not the one that lost by at least 3 points.

Q5. A “triple crown” is the accomplishment of having won a major championship (i.e. came in first position in a major tournament) in each of the three main regions, namely: Europe (EU), America (AM) and Korea (KR). List the tag and game race of each player who has managed to attain a triple crown at least once. 

Q6. List all teams founded before 2011 that are still active (not yet disbanded). For each such team, give the team name, date founded, and the number of current team members who play Protoss, Terran and Zerg, respectively. Sort the teams alphabetically by name. 

### Part 5: Querying the database using JDBC 
You are required to implement a Java program FinalProjectQueryYOURNAME.java that provides the capability to run queries on the system and display results in the console.
Q1. Given a year and month, provide the real name, tag, nationality and the number of wins of players who were born in that month and that year. 
For example, the following run command should find players who were born in May 1990 and print the results to the screen in the given format. 
java FinalProjectQuery q1 1990 05
Output:
	
	Young Jin Kim, SuperNova, KR, 7
	...
Q2. Given a player id and a team id, add that player as a member of the specified team, with the start date set according to the current system time. If the player is currently a member of another team, the database should also be updated to reflect their departure from the “old” team, with the end date set as above. If the player was already a current member of the given “new” team, no changes are necessary. 
For example, the following run statement should add player 1660 as a member of team 35, if they were not already a member of that team. If player 1660 is presently a member of a different team, that membership record must be updated as well. 
	
	java FinalProjectQuery q2 1660 35
Your program should display informative messages about the changes being made to the database (or lack thereof) based on the logic described above. It should also provide confirmation on whether each operation succeeded or not, and reasons for failure where applicable. 








