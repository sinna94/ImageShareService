package jsp.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserSignupServlet")

public class UserSignupServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		
		int result = new MemberDAO().signup(email, password, nickname);
		
		PrintWriter out = response.getWriter();
		
		if(result != 0 ) {
			out.println("<script> alert('ȸ������ ���� �α��� ���ּ���.'); location.replace('./index.html'); </script>");
		}
		else {
			out.println("<script> alert('ȸ������ ����');</script>");
			
		}
	}

}
