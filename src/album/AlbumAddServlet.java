package album;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;;

@WebServlet("/AlbumAddServlet")

public class AlbumAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DBConnection db=null;
			
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		db = new DBConnection();
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String nick = (String) session.getAttribute("nick");

		String name = request.getParameter("name");
		
		if(name.equals("")) {
			name = nick + "ÀÇ ¾Ù¹ü";
		}
		
		String query = "insert into Album (name, user_id) values('" + name + "', '" + nick + "');";
		
		try {
			db.noExcuteQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("account.jsp");
	}
}
