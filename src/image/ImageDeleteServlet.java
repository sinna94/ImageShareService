package image;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBConnection;;

@WebServlet("/ImageDeleteServlet")

public class ImageDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String image = request.getParameter("id");

		try {
			DBConnection db = new DBConnection();

			String query = "delete from IIS.Image where id = " + image + ";";
						
			System.out.println(query);
			db.noExcuteQuery(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.sendRedirect("account.jsp");
		}
	}
}
