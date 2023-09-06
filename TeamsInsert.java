import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TeamsInsert {

	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/ResultTrackerCASSANDRA"; //REPLACE W YOUR DATABASE
	public static final String USER = "root";
	public static final String PASSWORD = "rootroot"; //REPLACE WITH YOUR PASSWORD
   	
   	public static void insertTeams(Connection conn, String file) {
		File dataFile = new File(file);
		
        try {       
	        String teamsInsert = "INSERT INTO Teams (team_ID, team_name, founded, disbanded) "
	        		 + "VALUES (?, ?, ?, ?)";
	         
	        PreparedStatement teamsPreparedStatement = conn.prepareStatement(teamsInsert);
	        
	        
	        try {
	        	Scanner scan = new Scanner(dataFile);
	        	while(scan.hasNextLine()){
					String line = scan.nextLine();
					String[] data = line.split(",");
					
					teamsPreparedStatement.setInt(1, Integer.parseInt(data[0]));
					teamsPreparedStatement.setString(2, data[1].substring(1, data[1].length() - 1));
					teamsPreparedStatement.setString(3, data[2].substring(1, data[2].length() - 1));
					if (data.length == 4 && data[3] != "") {
						teamsPreparedStatement.setString(4, data[3].substring(1, data[3].length() - 1));
					}
					else {
						teamsPreparedStatement.setNull(4, java.sql.Types.DATE);
					}
			        
					teamsPreparedStatement.executeUpdate();
				}
	        	scan.close();
	        	teamsPreparedStatement.close();
	        	
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
			insertTeams(con, file);
	    }
	    catch(SQLException ex) {
	    	ex.printStackTrace();
	   } finally { 
		   //close connection
		   try { con.close(); } catch(SQLException se) { /*can't do anything */ }
	   }

   	}

}
