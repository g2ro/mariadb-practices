package driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDriverTest {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("driver.MyDriver"); // 마리아DB 드라이버 호출
			String url = "jdbc:mydb://127.0.0.1:12345/webdb"; // 마리아DB 를 쓰겠다 DB 서버 주
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
