package social;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;

@WebServlet("/commentInputServlet")

public class commentInputServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String nickname = (String) session.getAttribute("nick");
		String comment = (String) request.getParameter("comment");
		String image_id = (String) request.getParameter("id");
		
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat simDf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		String date = simDf.format(new Date(currentTime));
	
		
		DBConnection db = new DBConnection();
		
		
		
		String query = "insert into Comment (nickname, comment, date, image_id) values('" + nickname + "', '" + comment + "', '" + date + "', " + image_id + ");";
		try {
			
			db.noExcuteQuery(query);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("picture.jsp?id=" + image_id);
	}

}
