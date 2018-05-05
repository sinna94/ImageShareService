package image;

import java.sql.ResultSet;

import util.DBConnection;

public class ImageDAO {
	public void getImage(String nick) {
		
		DBConnection db = new DBConnection();
		
		ResultSet rs;
		String query = "Select path From Image Where user_id = '" +  nick + "';";
		
		try {
			rs = db.getQueryResult(query);
			while(rs.next()) {
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
