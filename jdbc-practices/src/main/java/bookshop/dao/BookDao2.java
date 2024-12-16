package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.BookVo2;

public class BookDao2 {
	public int insert(BookVo2 vo) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert into book(title, author_id) values(?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");

		) {
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setLong(2, vo.getAuthorId());
			count = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setId(rs.next() ? rs.getLong(1) : null);
			rs.close();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return count;
	}

	public int deleteAll() {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM book");

		) {
			count = pstmt1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return count;

	}
	
	public int update(Long id, String status) {
		int count = 0;

		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("update book set status = ? where id= ?");

		) {
			pstmt1.setString(1, status);
			pstmt1.setLong(2, id);
			count = pstmt1.executeUpdate();


		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return count;
		
	}
	
	public List<BookVo2> findAll() {
		List<BookVo2> result = new ArrayList<>();
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT a.id, a.title, b.name, a.status "
						+ "	FROM book a JOIN author b ON a.author_id = b.id");
				){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String authorName = rs.getString(3);
				String status = rs.getString(4);
				
				BookVo2 vo = new BookVo2();
				vo.setId(id);
				vo.setTitle(title);
				vo.setAuthorName(authorName);
				vo.setStatus(status);
				
				result.add(vo);
			}
			rs.close();
		} catch(SQLException e) {
			System.out.println("error :" + e);
		}
		
		return result;
				
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
