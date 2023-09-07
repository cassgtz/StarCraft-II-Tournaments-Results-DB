//package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;


public class FinalProjectQueryCASSANDRA {
	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/ResultTrackerCASSANDRA"; //REPLACE W YOUR DATABASE
	public static final String USER = "root";
	public static final String PASSWORD = "rootroot"; //REPLACE WITH YOUR PASSWORD
   	
   	public static void q1(String year, String month, Connection conn) {
		
        try {       
        	Statement queryStatement = conn.createStatement();
	        String query = "select real_name, tag, nationality, COUNT(playerID) AS wins FROM "
	        			+ "(select IF(playerA_score>playerB_score, playerA_ID, playerB_ID) AS playerID "
	        	    	+ "FROM matches) AS A "
	        	    	+ "INNER JOIN players ON A.playerID = players.player_id "
	        	    	+ "WHERE YEAR(birthday) = " + year + " AND MONTH(birthday) = " + month
	        	    	+ " GROUP BY real_name";
	        ResultSet resultSet = queryStatement.executeQuery(query);
	        int rows = 0;
	        
	        while(resultSet.next()) {
	        		if (rows == 0) {
	        			System.out.println("\nOUTPUT: \n");
	        		}
	        	   System.out.println(resultSet.getString("real_name") +
	        	      ", " + resultSet.getString("tag") +
	        	      ", " + resultSet.getString("nationality") + 
	        	      ", " + resultSet.getInt("wins"));
	        	   rows++;
	        }
	        
	        if (rows == 0) {
	        	  System.out.println("\nThere are no players born in that year/month.\n");
	        }
	        resultSet.close();
        }
         
        catch (SQLException e) {
            System.out.println("Cannot query: " + e.getMessage());
        }
         
      }
   	
   	public static boolean isAlreadyMember(String playerID, String teamID, Connection conn) {
   		
   		try {       
        	Statement queryStatement = conn.createStatement();
	        String alreadyMember = "select player_ID from members WHERE player_ID = " 
	        		+ playerID 
	        		+ " AND end_date IS NULL AND team_ID = "
	        		+ teamID;
	        ResultSet resultSet = queryStatement.executeQuery(alreadyMember);
	        int rows = 0;
	        if (resultSet.next()) {
	        	return true;
	        	
	        }
	        else {
	        	return false;
	        }
        }
         
        catch (SQLException e) {
            System.out.println("Cannot query: " + e.getMessage());
            return true;
        }
   		
   	}
   	
   	public static String isInDifferentTeam(String playerID, Connection conn) {
   		
   		try {       
        	Statement queryStatement = conn.createStatement();
	        String diffTeam = "select player_ID, team_ID from members WHERE player_ID = " 
	        		+ playerID 
	        		+ " AND end_date IS NULL";
	        ResultSet resultSet = queryStatement.executeQuery(diffTeam);
	        if (resultSet.next()) {
	        	return resultSet.getString("team_ID");
	        }
	        else {
	        	return null;
	        }
        }
         
        catch (SQLException e) {
            System.out.println("Cannot query: " + e.getMessage());
            return null;
        }
   		
   	}
   	
   	public static void terminateMembership(String playerID, String teamID, LocalDate date, Connection conn) {
   		
   		try {       
	        String terminate = "UPDATE members SET end_date = " 
	        		+ "\"" + date + "\"" 
	        		+ " WHERE player_ID = "
	        		+ playerID 
	        		+ " AND team_ID = "
	        		+ teamID;
	        PreparedStatement membersUpdate = conn.prepareStatement(terminate);
	        membersUpdate.executeUpdate();
	        membersUpdate.close();
        }
         
        catch (SQLException e) {
            System.out.println("Cannot execute: " + e.getMessage());
        }
   		
   	}
   	
   	public static void q2(String playerID, String teamID, LocalDate date, Connection conn) {
		
        try {       
        	String membersInsert = "INSERT INTO Members (player_ID, team_ID, start_date, end_date) "
	        		 + "VALUES (?, ?, ?, ?)";
        	
        	PreparedStatement preparedStatement = conn.prepareStatement(membersInsert);
        	preparedStatement.setInt(1, Integer.parseInt(playerID));
        	preparedStatement.setInt(2, Integer.parseInt(teamID));
        	preparedStatement.setString(3, date.toString());
        	preparedStatement.setNull(4, java.sql.Types.DATE);
        	
        	preparedStatement.executeUpdate();
        	preparedStatement.close();

			System.out.println("\nSuccessfully added player as member to team with team ID of " + teamID + "\n");
        	
        }
         
        catch (SQLException e) {
            System.out.println("Cannot Insert: " + e.getMessage());
        }
      }
 
   	private static Connection con;
   	public static void main(String[] args) {
   		String query = args[0];
	   	
	    try {
	    	con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
	    	if (query.equalsIgnoreCase("q1")) {
	   			String year = args[1];
	   			String month = args[2];
	   			q1(year, month, con);
	   			
	   		}
	    	else if (query.equalsIgnoreCase("q2")) {
	    		String playerID = args[1];
	    		String teamID = args[2];
	    		LocalDate date = java.time.LocalDate.now();
	    		// check if player is already a member of the selected team
	    		if (isAlreadyMember(playerID, teamID, con)) {
	    			System.out.println("\nCannot add: player is already a member of the team\n");
	    		}
	    		else {
	    			// check if they are a member of a different team
	    			if (isInDifferentTeam(playerID, con) != null) {
	    				String currentTeam = isInDifferentTeam(playerID, con);
	    				terminateMembership(playerID, currentTeam, date, con);
	    				System.out.println("\nEnded membership with current team with team ID of " + currentTeam);
	    				q2(playerID, teamID, date, con);
	    			}
	    			else{
	    				q2(playerID, teamID, date, con);
	    			}
	    			
	    		}
	    	}
			
	    }
	    catch(SQLException ex) {
	    	ex.printStackTrace();
	   } finally { 
		   //close connection
		   try { con.close(); } catch(SQLException se) { /*can't do anything */ }
	   }

   	}
}
