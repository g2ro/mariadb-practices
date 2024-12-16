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
		Connection conn = null;
		PreparedStatement pstmt =null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = 
					"INSERT INTO category " +
					"VALUES (null, ?) ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			
			int count = pstmt.executeUpdate();
			
			String setIdSql = 
					"SELECT last_insert_id() " +
					"FROM dual ";
			pstmt2 = conn.prepareStatement(setIdSql);
			
			rs = pstmt2.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				vo.setNo(id);
			}

			result = count == 1;
		} catch(SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(pstmt2 != null) {
					pstmt2.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return result;
	}

	public List<CategoryVo> findAll() {
		List<CategoryVo> result = new ArrayList<>();
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = 
					"SELECT no, name " + 
					"FROM category ";
			pstmt = conn.prepareStatement(sql);
			
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
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return result;
	}


	public void deleteByNo(Long categoryno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			String sql =
					"DELETE FROM category " +
					"WHERE no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, categoryno);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
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
