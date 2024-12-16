package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;
import bookmall.vo.UserVo;

public class UserDao {

	public Boolean insert(UserVo vo) {
		boolean result = false;
		
		ResultSet rs = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO user " +
						"VALUES (null, ?, ?, ?, ?)");
				
				PreparedStatement pstmt2 = conn.prepareStatement(
						"SELECT last_insert_id() " +
						"FROM dual");
				){
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getPhoneNumber());
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

	public List<UserVo> findAll() {
		List<UserVo> result = new ArrayList<>();
		
		ResultSet rs = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT no, name, email, password, phonenumber " + 
						"FROM user;");
				){
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String password = rs.getString(4);
				String phoneNumber = rs.getString(5);

				UserVo vo = new UserVo();
				vo.setNo(no);
				vo.setEmail(email);
				vo.setPassword(password);
				vo.setPhoneNumber(phoneNumber);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} 
		return result;
	}

	public void deleteByNo(Long user_no) {
		

		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"DELETE FROM user " + 
						"WHERE no = ? ");
				) {
			pstmt.setLong(1, user_no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
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
