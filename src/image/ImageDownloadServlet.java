package image;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;;

@WebServlet("/ImageDownloadServlet")

public class ImageDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DBConnection db=null;
			
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		db = new DBConnection();
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
				
		String imageId = (String) request.getParameter("id");
		String root = request.getSession().getServletContext().getRealPath("/");
		
		String query = "select * from Image where id ='" + imageId + "';"; 
		
		ResultSet rs;
		String nick = null;
		String path = null;
		try {
			rs = db.getQueryResult(query);
			if(rs.next()) {
				nick = rs.getString("user_id");
				path = rs.getString("path");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
            out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
		}
	
		String savePath = root + "upload/" + nick + "/";
		
		InputStream is =null;
		OutputStream os = null;
		File file = null;
		
		try {
			file = new File(savePath + path);
			byte b[] = new byte[4096];
			
			response.reset();
			response.setContentType("application/octet-stream");
			
			String Encoding = new String(path.getBytes("utf-8"), "8859_1");
			
			response.setHeader("Content-Disposition", "attatchment;filename="+Encoding);
			response.setHeader("Content-Length", String.valueOf((int)file.length()));
						
			is = new FileInputStream(file);
			ServletOutputStream sos = response.getOutputStream();
			
			int numRead;
			while((numRead=is.read(b,0,b.length))!=-1) {
				sos.write(b,0,numRead);
			}
			
			sos.flush();
			sos.close();
			is.close();
		}catch (FileNotFoundException e) {
			e.getStackTrace();
		}
		
		
	}
	
	
}
