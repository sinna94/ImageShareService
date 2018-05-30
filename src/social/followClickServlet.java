package social;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;

@WebServlet("/followClickServlet")

public class followClickServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		
		String othernickname = (String) request.getParameter("id");
		String nickname = (String) session.getAttribute("nick");
		
		String query = "insert into follow (user_id, follower_id) values('" + othernickname + "', '" + nickname + "');";
		DBConnection db =new DBConnection();
		
		try {
			db.noExcuteQuery(query);
			response.getWriter().write("1");
		} catch (SQLException e){
			e.printStackTrace();
			query = "delete from follow where user_id = '" + othernickname + "', '" + nickname + "';";
			try {
				db.noExcuteQuery(query);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			response.getWriter().write("0");
		}
	}
}
