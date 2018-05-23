package util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class DBConnection {
	Connection con = null;
	
	public DBConnection(){
		con = makeConnection();
	}
	
	public Connection makeConnection(){
		String url = "jdbc:mysql://52.78.129.251:3306/IIS";
		
		String id ="chung";
		String password = "dhaygks0229";
		
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, id, password);
		}catch (ClassNotFoundException e){
			System.out.println("Driver not found.");
		} catch (SQLException e){
			System.out.println("Connection failed.");
		}
		return con;
	}
	
	public ResultSet getQueryResult(String query) throws SQLException
    {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
        return rs;
    }

    public int noExcuteQuery(String query) throws SQLException
    {
		Statement stmt = con.createStatement();
		int result = stmt.executeUpdate(query);

		return result;
    }
}
