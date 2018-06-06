package album;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

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

import util.DBConnection;;

@WebServlet("/AlbumUploadServlet")

public class AlbumUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String imageID=null;
	private static DBConnection db=null;
	private String nick;
	private int imageOrder = 0;
	private int albumId = 0;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		db = new DBConnection();
		
		HttpSession session = request.getSession();
		
		nick = (String) session.getAttribute("nick");
		
		insertImage(request);
		
		response.sendRedirect("account.jsp?id=" + nick);
	}
	
	private void insertImage(HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + "upload/" + nick + "/";
		File desti = new File(savePath);
		
		if(!desti.exists()) {
			desti.mkdirs();
		}		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		
		try {
		List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
		
		Iterator<FileItem> iter = items.iterator();
		while(iter.hasNext()) {
			FileItem fileItem = (FileItem) iter.next();
			String fileName = fileItem.getName();
			
			if(fileItem.isFormField()) {
				makeAlbum(fileItem.getString("utf-8"));
			}
			else {
				
				long currentTime = System.currentTimeMillis();
				SimpleDateFormat simDf = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = simDf.format(new Date(currentTime)) + fileName;
				
				
				File newFile = new File(savePath + newFileName);
				fileItem.write(newFile);
				fileItem.delete();
				
				String query = "insert into IIS.Image(user_id, date, path) values('" + nick + "', '"
						+ simDf.format(new Date(currentTime)) + "', '" 
						+ newFileName + "');";
								
				db.noExcuteQuery(query);
				
				// 이미지 정보가 저장됐는지 확인
				// 마지막으로 삽입된 이미지의 아이디 검색
				ResultSet rs = db.getQueryResult("SELECT LAST_INSERT_ID() as id");
				
				if(rs.next()) {
					imageID = rs.getString("id");
					//구글 클라우드 비젼 api
					cloudVision(savePath + newFileName);
					AddImageToAlbum(imageID);
				}
			
			}
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void AddImageToAlbum(String imageID) throws SQLException {
		String query = "insert into IIS.Album_image(album_id, image_id, Album_image.order) values (" + albumId + ", " + imageID + ", " + ++imageOrder + ");";
		db.noExcuteQuery(query);
	}

	private void makeAlbum(String name) {	
		if(name == null || name.equals("")) {
			name = nick + "의 앨범";
		}
		
		String query = "insert into Album (name, user_id) values('" + name + "', '" + nick + "');";
		
		try {
			db.noExcuteQuery(query);
			ResultSet rs = db.getQueryResult("SELECT LAST_INSERT_ID() as id");
			if(rs.next()) {
				albumId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
