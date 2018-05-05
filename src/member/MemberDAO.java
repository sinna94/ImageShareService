package member;

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
		String query = "INSERT INTO Users values ('" + email + "', '" + password + "', '" + nickname + "');";

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
				member.setImage(rs.getBlob("image"));
				member.setIntro(rs.getString("profile"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return member;
	}
}
