package social;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConnection;

public class likeDAO {
	public int clickLike(String id, String nickname) {
		DBConnection db = new DBConnection();
		
		String query = "select * from IIS.like where image_id = '" + id + "' and user_id = '" + nickname + "';";
	
		
		try {
			ResultSet rs = db.getQueryResult(query); 
			//누른 상태
			if (rs.next()) {
				query = "delete from IIS.like where image_id = '" + id + "' and user_id = '" + nickname + "';";
				db.noExcuteQuery(query);
				return 0;
				
			} else {
				query = "insert into IIS.like (image_id, user_id) values ('" + id + "', '" + nickname + "');";
				db.noExcuteQuery(query);
				return 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
