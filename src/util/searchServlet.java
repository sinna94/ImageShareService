package util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonArray;

@WebServlet("/searchServlet")

public class searchServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String keyword = (String) request.getParameter("keyword");
		
		response.getWriter().print(new searchResult().getResult(keyword));
	}
	
	private class searchResult{
		private String getResult(String keyword){
			JSONArray results = new JSONArray();
			
			DBConnection db = new DBConnection();
			
			String query = "select distinct hashtag.hashtag from hashtag where hashtag like '" + keyword + "%';";
			JSONObject object = null;

						
			try {
				ResultSet rs = db.getQueryResult(query);
				while(rs.next()) {
					String tag = rs.getString("hashtag");
					results.add(tag);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return results.toString();
		}
	}
}
