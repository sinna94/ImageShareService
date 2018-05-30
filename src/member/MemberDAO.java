package member;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.websocket.Session;

import util.DBConnection;

public class MemberDAO {
	public int emailCheck(String email) {
		DBConnection db = new DBConnection();

		String query = "SELECT count(*) FROM Users WHERE email = '" + email + "';";
		ResultSet rs = null;

		try {
			rs = db.getQueryResult(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (rs.next()) {
				if (rs.getInt("count(*)") == 0) {
					return 1;
				} else {
					return 0;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int nicknameCheck(String nickname) {
		DBConnection db = new DBConnection();

		String query = "SELECT count(*) FROM Users WHERE nickname = '" + nickname + "';";
		ResultSet rs = null;

		try {
			rs = db.getQueryResult(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (rs.next()) {
				if (rs.getInt("count(*)") == 0) {
					return 1;
				} else {
					return 0;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String login(String email, String password) {
		DBConnection db = new DBConnection();
		String query = "select nickname from Users where email = '" + email + "' and password = '" + password + "';";

		ResultSet rs = null;

		try {
			rs = db.getQueryResult(query);
		
			if (rs.next()) {
				return rs.getString("nickname");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}

	public int signup(String email, String password, String nickname) {
		DBConnection db = new DBConnection();
		String query = "INSERT INTO Users (email, password, nickname) values ('" + email + "', '" + password + "', '" + nickname + "');";
		System.out.println(query);
		int result = 0;

		try {
			result = db.noExcuteQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public MemberBean getMember(String id, int n) {
		DBConnection db = new DBConnection();

		// n == 0 -> email
		// n == 1 -> nickname
		String query = "select email, nickname, profile_image, profile " + "from Users ";
		if (n == 0) {
			query += "where email = " + "'" + id + "';";
		} else if (n == 1) {
			query += "where nickname = " + "'" + id + "';";
		}

		MemberBean member = new MemberBean();

		try {
			ResultSet rs;
			rs = db.getQueryResult(query);
			if (rs.next()) {
				member.setEmail(rs.getString("email"));
				member.setNickname(rs.getString("nickname"));
				member.setImage(rs.getString("profile_image"));
				member.setIntro(rs.getString("profile"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return member;
	}
	
	public void setProfileImage(String image, String nickname) {
		DBConnection db = new DBConnection();
		
		String query = "UPDATE `IIS`.`Users` SET `profile_image`='" + image + "' WHERE `Nickname` = '" + nickname + "';";
		
		try {
			db.noExcuteQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getUserInfo(String nickname, PrintWriter pw) {
		DBConnection db = new DBConnection();
		
		String query = "select nickname, Email from Users where nickname = '" + nickname + "';";
	
		try {
			ResultSet rs = db.getQueryResult(query);
		
			if(rs.next()) {
				pw.println("<input type='email' id='email' name='email' oninput='checkEmail();' class='form-control' value='" + rs.getString("Email") + "' required autofocus>");
				pw.println("<input type='password' id='password' name='password' class='form-control' required>");
				pw.println("<input type='password' id='passwordc' name='passwordCheck' oninput='checkPassword();' class='form-control' placeholder='비밀번호 확인' required>");
				pw.println("<input type='text' id='nickname' name='nickname' class='form-control' oninput='checkNickname();' value='" + rs.getString("nickname") + "' required>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
