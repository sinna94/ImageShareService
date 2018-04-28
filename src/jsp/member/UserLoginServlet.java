package jsp.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserLoginServlet")

public class UserLoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		boolean result = new MemberDAO().login(email, password);
		
		PrintWriter out = response.getWriter();
		
		if(result) {
			HttpSession session = request.getSession();
			session.setAttribute("id", email);
			response.sendRedirect("account.jsp");
		}
		else {
			
			out.println("<script> alert('로그인 실패'); history.back(-1);</script>");
		}
	}

}
