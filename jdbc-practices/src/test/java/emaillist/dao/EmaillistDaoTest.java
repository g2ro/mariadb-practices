package emaillist.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import emaillist.dao.EmaillistDao;
import emaillist.vo.EmaillistVo;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmaillistDaoTest {
	private static Long count =0L;
	
	@BeforeAll // 먼저 실행
	public static void setup() {
		count = new EmaillistDao().count();	
	}
	@Test
	@Order(1)
	public void insertTest(){
		EmaillistVo vo = new EmaillistVo();
		vo.setFirstName("둘");
		vo.setLastName("리");
		vo.setEmail("dooly@gmail.com");
		Boolean result = new EmaillistDao().insert(vo);
		assertTrue(result);
	}
	@Test
	@Order(2)
	public void findAllTest() {
		List<EmaillistVo> list = new EmaillistDao().findAll();
		assertEquals(count + 1, list.size()); // count + 1인 이유는 기존에D에 있는 data고려하기 위
	}
	@Test
	@Order(3)
	public void deleteByEmailTest() {
		Boolean result = new EmaillistDao().deleteByEmail("dooly@gmail.com");
		assertTrue(result);
	}
	
	@AfterAll
	public static void cleanup() {
		
	}
}
