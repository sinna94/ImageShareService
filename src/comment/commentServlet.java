package comment;

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

@WebServlet("/commentServlet")

public class commentServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String id = (String) request.getParameter("id");
		
		DBConnection db = new DBConnection();
		
		String query = "select nickname, comment, date from Comment where image_id = '" + id + "' order by date;";
		
		try {
			ResultSet rs = db.getQueryResult(query);
			
			if(rs.next()) {
				do{
					out.write("<div>");
					out.write("<span>" + rs.getString("nickname") + "</span>");
					out.write("<span>" + rs.getString("comment") + "</span>");
					out.write("<span>" + rs.getString("date") + "</span>");
					out.write("</div>");
				} while (rs.next());
			} else {
				out.write("<div>");
				out.write("<span>댓글이 없습니다.</span>");
				out.write("</div>");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
