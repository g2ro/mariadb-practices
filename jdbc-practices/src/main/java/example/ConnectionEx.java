package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionEx {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 마리아DB 드라이버 호출
			String url = "jdbc:mariadb://192.168.64.3:3306/webdb"; // 마리아DB 를 쓰겠다 DB 서버 주
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			System.out.println("연결 성공!!");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}