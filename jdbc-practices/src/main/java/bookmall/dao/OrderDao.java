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
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "INSERT INTO orders " + "VALUES (null, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUserNo());
			pstmt.setString(2, vo.getNumber());
			pstmt.setInt(3, vo.getPayment());
			pstmt.setString(4, vo.getShipping());
			pstmt.setString(5, vo.getStatus());

			int count = pstmt.executeUpdate();

			String setIdSql = "select last_insert_id() from dual";
			pstmt2 = conn.prepareStatement(setIdSql);

			rs = pstmt2.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				vo.setNo(id);
			}

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return result;

	}

	public boolean insertBook(OrderBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			conn = getConnection();

			String titleSql = "SELECT title " + "FROM book " + "WHERE no = ? ";
			pstmt3 = conn.prepareStatement(titleSql);
			pstmt3.setLong(1, vo.getBookNo());

			rs2 = pstmt3.executeQuery();

			if (rs2.next()) {
				String title = rs2.getString(1);
				vo.setBookTitle(title);
			}

			String sql = "INSERT INTO orderbook " + "VALUES(null, ?, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getOrderNo());
			pstmt.setLong(2, vo.getBookNo());
			pstmt.setString(3, vo.getBookTitle());
			pstmt.setInt(4, vo.getQuantity());
			pstmt.setInt(5, vo.getPrice());

			int count = pstmt.executeUpdate();

			String setIdSql = "select last_insert_id() from dual";
			pstmt2 = conn.prepareStatement(setIdSql);

			rs = pstmt2.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				vo.setNo(id);
			}

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return result;
	}

	public OrderVo findByNoAndUserNo(long orderNo, Long user_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVo result = null;
		try {
			conn = getConnection();
			String sql = "SELECT no, user_no, payment, shipping, status, number " + "FROM orders " + "WHERE no = ? "
					+ "	AND user_no = ? ";
			pstmt = conn.prepareStatement(sql);
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
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return result;
	}

	public List<OrderBookVo> findBooksByNoAndUserNo(Long order_no, Long user_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderBookVo> result = new ArrayList<>();
		try {
			conn = getConnection();
			String sql = 
					"SELECT o.no, o.orders_no, o.book_no, o.booktitle, o.quantity, o.price " + 
					"FROM orderbook o " +
					"	JOIN book b ON (o.book_no = b.no) " + 
					"   JOIN cart c ON (c.book_no = o.book_no) " +
					"    JOIN user u ON (c.user_no = u.no) " + 
					"WHERE o.orders_no = ? " + 
					"	AND u.no = ? ";
			pstmt = conn.prepareStatement(sql);
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

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		return result;
	}

	public void deleteBooksByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "DELETE FROM orderbook " + "WHERE orders_no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

	}

	public void deleteByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql = "DELETE FROM orders " + "WHERE no = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
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
