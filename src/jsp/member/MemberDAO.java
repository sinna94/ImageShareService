package jsp.member;

import java.sql.ResultSet;
import java.sql.SQLException;

import jsp.util.DBConnection;

public class MemberDAO {
	public int emailCheck(String email){		
		DBConnection db = new DBConnection();
		
		String query = "SELECT count(*) FROM Users WHERE email = '" + email + "';";
		ResultSet rs = null;
		
		try{
		rs = db.getQueryResult(query);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			if(rs.next()){
				if(rs.getInt("count(*)") == 0) {
					return 1;
				}				
				else{
					return 0;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int nicknameCheck(String nickname) {
		DBConnection db = new DBConnection();
		
		String query = "SELECT count(*) FROM Users WHERE nickname = '" + nickname + "';";
		ResultSet rs = null;
		
		try{
		rs = db.getQueryResult(query);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			if(rs.next()){
				if(rs.getInt("count(*)") == 0) {
					return 1;
				}				
				else{
					return 0;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean login (String email, String password) {
		DBConnection db = new DBConnection();
		String query = "select count(*) from Users where email = '" 
		+ email + "' and password = '" 
		+ password + "';";
		
		ResultSet rs = null;
		
		try{
			rs = db.getQueryResult(query);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			if(rs.next()){
				switch (rs.getInt("count(*)")){
				case 0:
					return false;
				case 1:
					return true;
				default:
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
