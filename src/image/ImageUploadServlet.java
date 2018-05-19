package image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import util.DBConnection;;

@WebServlet("/ImageUploadServlet")

public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String account = (String) session.getAttribute("id");
		String nick = (String) session.getAttribute("nick");

		String root = request.getSession().getServletContext().getRealPath("/");
		
		String savePath = root + "upload/" + nick + "/";
		File desti = new File(savePath);
		
		if(!desti.exists()) {
			desti.mkdirs();
		}
		
		String uploadFile = "";

		String newFileName = "";

		int max = 1024 * 1024 * 10;
		String enc = "utf-8";
		DefaultFileRenamePolicy dp = new DefaultFileRenamePolicy();

		int read = 0;
		byte[] buf = new byte[1024];
		FileInputStream fin = null;
		FileOutputStream fout = null;
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat simDf = new SimpleDateFormat("yyyyMMddHHmmss");

		try {

			MultipartRequest multi = new MultipartRequest(request, savePath, max, enc, dp);

			uploadFile = multi.getFilesystemName("filename");

			newFileName = simDf.format(new Date(currentTime)) + "."
					+ uploadFile.substring(uploadFile.lastIndexOf(".") + 1);

			File oldFile = new File(savePath + uploadFile);
			File newFile = new File(savePath + newFileName);

			if (!oldFile.renameTo(newFile)) {

				// rename이 되지 않을경우 강제로 파일을 복사하고 기존파일은 삭제

				buf = new byte[1024];
				fin = new FileInputStream(oldFile);
				fout = new FileOutputStream(newFile);
				read = 0;
				while ((read = fin.read(buf, 0, buf.length)) != -1) {
					fout.write(buf, 0, read);
				}

				fin.close();
				fout.close();
				oldFile.delete();

			}
			DBConnection db = new DBConnection();

			String query = "insert into IIS.Image(user_id, date, path, content) values('" + nick + "', '"
					+ simDf.format(new Date(currentTime)) + "', '" 
					+ newFileName + "','" + multi.getParameter("content") +"');";
						
			db.noExcuteQuery(query);
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script> alert('업로드 실패');</script>");
		} finally {
			response.sendRedirect("account.jsp?id=" + nick);
		}
	}
}
