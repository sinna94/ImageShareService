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
import javax.servlet.http.HttpSession;

import util.DBConnection;

@WebServlet("/getFollowServlet")

public class getFollowServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		String otherNickname = (String) request.getParameter("id");
		String nickname = (String) session.getAttribute("nick");
		
		DBConnection db = new DBConnection();
		
		String query = "select * from follow where user_id = '" + otherNickname + "' and follower_id = '" + nickname + "';";
		
		try {
			ResultSet rs = db.getQueryResult(query);
			
			//누른 상태
			if (rs.next()) {
				out.write(" <span id='follow' class='glyphicon glyphicon-plus' aria-hidden='true' style='color:#d45a40'></span>");	
			} else {
				out.write(" <span id='follow' class='glyphicon glyphicon-plus' aria-hidden='true' style='color:#212020'></span>");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
