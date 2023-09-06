import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MatchesInsert {
	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/ResultTrackerCASSANDRA"; //REPLACE W YOUR DATABASE
	public static final String USER = "root";
	public static final String PASSWORD = "rootroot"; //REPLACE WITH YOUR PASSWORD
   	
   	public static void insertMatches(Connection conn, String file) {
		File dataFile = new File(file);
		
        try {       
	        String matchesInsert = "INSERT INTO Matches (match_ID, match_date, tournament_ID, playerA_ID, playerB_ID, playerA_score, playerB_score, is_offline) "
	        		 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	         
	        PreparedStatement matchesPreparedStatement = conn.prepareStatement(matchesInsert);
	        
	        
	        try {
	        	Scanner scan = new Scanner(dataFile);
	        	while(scan.hasNextLine()){
					String line = scan.nextLine();
					String[] data = line.split(",");
					
					matchesPreparedStatement.setInt(1, Integer.parseInt(data[0]));
					matchesPreparedStatement.setString(2, data[1].substring(1, data[1].length() - 1));
					matchesPreparedStatement.setInt(3, Integer.parseInt(data[2]));
					matchesPreparedStatement.setInt(4, Integer.parseInt(data[3]));
					matchesPreparedStatement.setInt(5, Integer.parseInt(data[4]));
					matchesPreparedStatement.setInt(6, Integer.parseInt(data[5]));
					matchesPreparedStatement.setInt(7, Integer.parseInt(data[6]));
					matchesPreparedStatement.setString(8, data[7]);
			        
					matchesPreparedStatement.executeUpdate();
				}
	        	scan.close();
	        	matchesPreparedStatement.close();
	        	
	        }
	        catch (FileNotFoundException e) {
				System.out.println("file not found");
			}
        }
         
        catch (SQLException e) {
            System.out.println("Cannot insert row:" + e.getMessage());
        }
         
      }
 
   	private static Connection con;
   	public static void main(String[] args) {
   		String file = args[0];
	   	
	    try {
	    	con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			insertMatches(con, file);
	    }
	    catch(SQLException ex) {
	    	ex.printStackTrace();
	   } finally { 
		   //close connection
		   try { con.close(); } catch(SQLException se) { /*can't do anything */ }
	   }

   	}

}
