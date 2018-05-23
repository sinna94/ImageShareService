package image;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;;

@WebServlet("/ImageDeleteServlet")

public class ImageDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String image = request.getParameter("id");
		HttpSession session = request.getSession();
		String nickname = (String) session.getAttribute("nick");
		String root = request.getSession().getServletContext().getRealPath("/");
		
		new ImageDAO().deleteImage(root, nickname, image);
		
		response.sendRedirect("account.jsp");
	}
}
