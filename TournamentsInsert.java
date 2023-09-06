import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TournamentsInsert {
	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/ResultTrackerCASSANDRA"; //REPLACE W YOUR DATABASE
	public static final String USER = "root";
	public static final String PASSWORD = "rootroot"; //REPLACE WITH YOUR PASSWORD
   	
   	public static void insertTournaments(Connection conn, String file) {
		File dataFile = new File(file);
        try {       
	        String tournamentsInsert = "INSERT INTO Tournaments (tournament_ID, tournament_name, region, major) "
	        		 + "VALUES (?, ?, ?, ?)";
	         
	        PreparedStatement tournamentsPreparedStatement = conn.prepareStatement(tournamentsInsert);
	        
	        
	        try {
	        	Scanner scan = new Scanner(dataFile);
	        	while(scan.hasNextLine()){
					String line = scan.nextLine();
					String[] data = line.split(",");
					
					tournamentsPreparedStatement.setInt(1, Integer.parseInt(data[0]));
					tournamentsPreparedStatement.setString(2, data[1].substring(1, data[1].length() - 1));
					if (data[2] != null && data[2].length() == 4) {
						tournamentsPreparedStatement.setString(3, data[2].substring(1, data[2].length() - 1));
					}
					else {
						tournamentsPreparedStatement.setNull(3, java.sql.Types.VARCHAR);
					}
					tournamentsPreparedStatement.setString(4, data[3]);
					
			        
					tournamentsPreparedStatement.executeUpdate();
				}
	        	scan.close();
	        	tournamentsPreparedStatement.close();
	        	
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
			insertTournaments(con, file);
	    }
	    catch(SQLException ex) {
	    	ex.printStackTrace();
	   } finally { 
		   //close connection
		   try { con.close(); } catch(SQLException se) { /*can't do anything */ }
	   }

   	}
	
}
