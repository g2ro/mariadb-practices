package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CategoryVo;
import bookmall.vo.UserVo;

public class CategoryDao {

	public Boolean insert(CategoryVo vo) {
		boolean result = false;
		
		ResultSet rs = null;
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt =conn.prepareStatement(
						"INSERT INTO category " +
						"VALUES (null, ?) ");
				
				PreparedStatement pstmt2 = conn.prepareStatement(
						"SELECT last_insert_id() " +
						"FROM dual ");
				){
			pstmt.setString(1, vo.getName());
			
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

	public List<CategoryVo> findAll() {
		List<CategoryVo> result = new ArrayList<>();
		
		ResultSet rs = null;
		try (
				Connection conn =getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT no, name " + 
						"FROM category ");
				){
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
								
				CategoryVo vo = new CategoryVo();
				vo.setNo(no);
				vo.setName(name);
				
				result.add(vo);
			}
		} catch(SQLException e) {
			System.out.println("드라이버 로딩 실패: " +e);
		} 
		return result;
	}


	public void deleteByNo(Long categoryno) {
		
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"DELETE FROM category " +
						"WHERE no = ? ");
				) {
			pstmt.setLong(1, categoryno);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" +e);
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
