package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookmall.vo.BookVo;

public class BookDao {

	public boolean insert(BookVo vo) {
		boolean result = false;
		ResultSet rs = null;
		
		try ( 
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO book " + 
						"VALUES (null, ?,?,?)");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"SELECT last_insert_id() " +
						"FROM dual "
						);
				){
			pstmt.setLong(1, vo.getCategoryNo());
			pstmt.setString(2, vo.getTitle());
			pstmt.setInt(3, vo.getPrice());
			
			int count = pstmt.executeUpdate();

			
			rs = pstmt2.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				vo.setNo(id);
			}
			
			rs.close();
			result = count == 1;
		} catch(SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} 
		return result;		
	}
	
	public void deleteByNo(Long bookno) {
		
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"DELETE FROM book " +
						"WHERE no = ? ");
				) {
			pstmt.setLong(1, bookno);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.64.3:3306/bookmall";
			conn = DriverManager.getConnection(url, "bookmall", "bookmall");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		
		return conn;
	}
}
