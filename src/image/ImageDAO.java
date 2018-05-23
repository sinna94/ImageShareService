package image;

import java.io.File;
import java.sql.ResultSet;

import util.DBConnection;

public class ImageDAO {
	public ImageDTO getImage(String id) {
		ImageDTO image = new ImageDTO();
		
		DBConnection db = new DBConnection();
		ResultSet rs;
		
		String query = "select * from Image where id =" + id + ";";
		
		try{
			rs = db.getQueryResult(query);
			
			if(rs.next()){
				image.setId(rs.getInt("id"));
				image.setUser_id(rs.getString("user_id"));
				image.setDate(rs.getString("date"));
				image.setLike(rs.getInt("like_cnt"));
				image.setPath(rs.getString("path"));
				image.setContent(rs.getString("content"));
			} 
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return image;
	}

	public void deleteImage(String root, String nickname, String image) {
		try {
			DBConnection db = new DBConnection();
			
			String query = "select path from IIS.Image where id =" + image + ";";
			ResultSet rs = db.getQueryResult(query);
			
			if(rs.next()) {
				String path = rs.getString("path");
				
				query = "delete from IIS.Image where id = " + image + ";";
				
				db.noExcuteQuery(query);
				
				File file = new File(root + "upload/" + nickname + "/" + path);
				file.delete();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
