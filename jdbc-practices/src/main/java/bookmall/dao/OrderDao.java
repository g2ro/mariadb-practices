package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {

	public boolean insert(OrderVo vo) {
		boolean result = false;
		
		ResultSet rs = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO orders " + 
						"VALUES (null, ?, ?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"SELECT last_insert_id() " +
						"FROM dual");
				){
			pstmt.setLong(1, vo.getUserNo());
			pstmt.setString(2, vo.getNumber());
			pstmt.setInt(3, vo.getPayment());
			pstmt.setString(4, vo.getShipping());
			pstmt.setString(5, vo.getStatus());

			int count = pstmt.executeUpdate();

			rs = pstmt2.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				vo.setNo(id);
			}
			
			rs.close();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		return result;
	}

	public boolean insertBook(OrderBookVo vo) {
		boolean result = false;
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO orderbook " + 
						"VALUES(null, ?, ?, ?, ?, ?) ");
				
				PreparedStatement pstmt2 = conn.prepareStatement(
						"SELECT last_insert_id() " +
						"FROM dual");
				
				PreparedStatement pstmt3 = conn.prepareStatement(
						"SELECT title " + 
						"FROM book " + 
						"WHERE no = ? ");
				){
			pstmt3.setLong(1, vo.getBookNo());

			rs2 = pstmt3.executeQuery();

			if (rs2.next()) {
				String title = rs2.getString(1);
				vo.setBookTitle(title);
			}
			rs2.close();

			pstmt.setLong(1, vo.getOrderNo());
			pstmt.setLong(2, vo.getBookNo());
			pstmt.setString(3, vo.getBookTitle());
			pstmt.setInt(4, vo.getQuantity());
			pstmt.setInt(5, vo.getPrice());

			int count = pstmt.executeUpdate();

			rs = pstmt2.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				vo.setNo(id);
			}

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} 
		return result;
	}

	public OrderVo findByNoAndUserNo(long orderNo, Long user_no) {
		
		ResultSet rs = null;
		OrderVo result = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT no, user_no, payment, shipping, status, number " + 
						"FROM orders " + 
						"WHERE no = ? " + 
						"	AND user_no = ? ");
				){
			pstmt.setLong(1, orderNo);
			pstmt.setLong(2, user_no);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Long no = rs.getLong(1);
				Long userNo = rs.getLong(2);
				int payment = rs.getInt(3);
				String shipping = rs.getString(4);
				String status = rs.getString(5);
				String number = rs.getString(6);

				result = new OrderVo();

				result.setNo(no);
				result.setUserNo(userNo);
				result.setPayment(payment);
				result.setShipping(shipping);
				result.setStatus(status);
				result.setNumber(number);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		return result;
	}

	public List<OrderBookVo> findBooksByNoAndUserNo(Long order_no, Long user_no) {
		
		ResultSet rs = null;
		List<OrderBookVo> result = new ArrayList<>();
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
					"SELECT o.no, o.orders_no, o.book_no, o.booktitle, o.quantity, o.price " + 
					"FROM orderbook o " +
					"	JOIN book b ON (o.book_no = b.no) " + 
					"   JOIN cart c ON (c.book_no = o.book_no) " +
					"    JOIN user u ON (c.user_no = u.no) " + 
					"WHERE o.orders_no = ? " + 
					"	AND u.no = ? ");
				){
			pstmt.setLong(1, order_no);
			pstmt.setLong(2, user_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				Long orderNo = rs.getLong(2);
				Long bookNo = rs.getLong(3);
				String booktitle = rs.getString(4);
				int quantity = rs.getInt(5);
				int price = rs.getInt(6);

				OrderBookVo vo = new OrderBookVo();
				vo.setNo(no);
				vo.setOrderNo(orderNo);
				vo.setBookNo(bookNo);
				vo.setBookTitle(booktitle);
				vo.setQuantity(quantity);
				vo.setPrice(price);

				result.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		return result;
	}

	public void deleteBooksByNo(Long no) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"DELETE FROM orderbook " + 
						"WHERE orders_no = ? ");
				){
			pstmt.setLong(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public void deleteByNo(Long no) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"DELETE FROM orders " + 
						"WHERE no = ? ");
				){
			pstmt.setLong(1, no);
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