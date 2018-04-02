package jsp.member;

import java.sql.ResultSet;
import java.sql.SQLException;

import jsp.util.DBConnection;

public class MemberDAO {
	public int emailCheck(String email){
		System.out.println(email);
		
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
}
