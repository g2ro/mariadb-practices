package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;

public class CartDao {

	public Boolean insert(CartVo vo) {
		boolean result = false;
		ResultSet rs2 = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt =conn.prepareStatement(
						"INSERT INTO cart " +
						"VALUES (?,?,?,?,?) ");
				
				PreparedStatement pstmt3 = conn.prepareStatement(
						"SELECT title, price " +
						"FROM book " +
						"WHERE no = ? ");
				){
			pstmt3.setLong(1, vo.getBookNo());
			
			rs2 = pstmt3.executeQuery();
			
			if(rs2.next()) {
				String title = rs2.getString(1);
				int price = rs2.getInt(2);
				vo.setBookTitle(title);
				vo.setPrice(vo.getQuantity() * price);
			}
			rs2.close();
			
			pstmt.setLong(1, vo.getUserNo());
			pstmt.setLong(2, vo.getBookNo());
			pstmt.setString(3, vo.getBookTitle());
			pstmt.setInt(4, vo.getQuantity());
			pstmt.setInt(5, vo.getPrice());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
			
		} catch(SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		return result;
	}
	
	public List<CartVo> findByUserNo(Long user_no) { //return 값 수정 필
		List<CartVo> result = new ArrayList<>();
		
		ResultSet rs = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT c.user_no, c.book_no, c.quantity, b.title " +
						"FROM cart c " +
						"	JOIN book b ON(c.book_no = b.no) " +
						"WHERE c.user_no = ? ");
				){
			pstmt.setLong(1, user_no);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long userNo = rs.getLong(1);
				Long bookNo = rs.getLong(2);
				int quantity = rs.getInt(3);
				String bookTitle = rs.getString(4);
				
		
				CartVo vo = new CartVo();
				vo.setUserNo(userNo);
				vo.setBookNo(bookNo);
				vo.setBookTitle(bookTitle);
				vo.setQuantity(quantity);

				result.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void deleteByUserNoAndBookNo(Long userNo, Long bookNo) {
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"DELETE FROM cart "+
						"WHERE user_no = ? " +
						"	AND book_no = ? ");
				){
			pstmt.setLong(1, userNo);
			pstmt.setLong(2, bookNo);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
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
