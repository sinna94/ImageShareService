package social;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;

@WebServlet("/getTagServlet")

public class getTagServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String id = (String) request.getParameter("id");
		
		DBConnection db = new DBConnection();
		
		String query = "select hashtag from IIS.hashtag where image_id = '" + id + "';";
		
		try {
			ResultSet rs = db.getQueryResult(query);
			
			if(rs.next()) {
				do{
					String tag = rs.getString("hashtag");
					String url = "'search.jsp?keyword=" + tag + "'";
					//out.write("<div class='tag'>");
					out.write("<a href=" + url + ">#"+ tag + " " +"</a>");
					//out.write("</div>");
				} while (rs.next());
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
