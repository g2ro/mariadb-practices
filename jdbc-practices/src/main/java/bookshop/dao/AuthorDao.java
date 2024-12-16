package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookshop.vo.AuthorVo;

public class AuthorDao {

	public int insert(AuthorVo vo) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert into author values (null, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");

		) {
			pstmt1.setString(1, vo.getName());
			count = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setId(rs.next() ? rs.getLong(1) : null);
			rs.close();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return count;
	}

	public int	deleteById(Long id) {
		int count = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM author WHERE id = ?");
				) {
			pstmt.setLong(1, id);
			count = pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
	}
	
	public int deleteAll() {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM author");

		) {
			count = pstmt1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return count;

	}
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.64.3:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return conn;
	}

	

}
