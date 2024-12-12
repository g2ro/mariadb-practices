package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class insertEx01 {
	public static void main(String[] args)  {
		insert("기획1팀");
		insert("기획2팀");
	}
	public static boolean insert(String departmentName){
		boolean result = false;
		Connection conn = null;
		Statement stmt = null;
		try {
			// 1. JDBC Driver 로
			Class.forName("org.mariadb.jdbc.Driver"); // 마리아DB 드라이버 호출
			
			//2. 연결하
			String url = "jdbc:mariadb://192.168.64.3:3306/webdb"; // 마리아DB 를 쓰겠다 DB 서버 
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			//3. Statement 생성하기
			stmt = conn.createStatement();
			
			// 4. SQL 실행
			String sql = "insert into department values(null, '" + departmentName + "')";
			int count = stmt.executeUpdate(sql);
			result = count == 1;
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}
}
