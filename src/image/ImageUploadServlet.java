package image;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import util.DBConnection;;

@WebServlet("/ImageUploadServlet")

public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String imageID=null;
	private static DBConnection db=null;
			
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		db = new DBConnection();
		
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

			String query = "insert into IIS.Image(user_id, date, path, content) values('" + nick + "', '"
					+ simDf.format(new Date(currentTime)) + "', '" 
					+ newFileName + "','" + multi.getParameter("content") +"');";
									
			db.noExcuteQuery(query);
			
			// 이미지 정보가 저장됐는지 확인
			// 마지막으로 삽입된 이미지의 아이디 검색
			ResultSet rs = db.getQueryResult("SELECT LAST_INSERT_ID() as id");
			
			if(rs.next()) {
				imageID = rs.getString("id");
				String tag = multi.getParameter("tag");
				String [] tags = tag.split("\\#");
				
				for(int i=1;i<tags.length;i++) {
					addTag(tags[i]);
				}
				
				//구글 클라우드 비젼 api
				cloudVision(savePath + newFileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script> alert('업로드 실패');</script>");
		} 
		
		response.sendRedirect("account.jsp?id=" + nick);
	}
	
	private static void addTag(String tag) {
		
		try {
			String query = "insert into IIS.hashtag(hashtag, image_id) values('" + tag + "', '" + imageID + "');";
			db.noExcuteQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void cloudVision(String path) throws Exception, IOException {
		  List<AnnotateImageRequest> requests = new ArrayList<>();

		  ByteString imgBytes = ByteString.readFrom(new FileInputStream(path));
		  Image img = Image.newBuilder().setContent(imgBytes).build();
		  Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
		  AnnotateImageRequest request =
		      AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		  requests.add(request);

		  try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
		    BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
		    List<AnnotateImageResponse> responses = response.getResponsesList();

		    for (AnnotateImageResponse res : responses) {
		      if (res.hasError()) {
		    	System.out.printf("Error: %s\n", res.getError().getMessage());
		        return;
		      }

		      // For full list of available annotations, see http://g.co/cloud/vision/docs
 
		      for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
		          annotation.getAllFields().forEach((k, v) -> 
		          checkDescription(k, v));
		        }
		    }
		  }
		}

	private static void checkDescription(FieldDescriptor k, Object v) {
		if(k.getName().equals("description")) {
			addTag(v.toString());
		}
	}	
}
