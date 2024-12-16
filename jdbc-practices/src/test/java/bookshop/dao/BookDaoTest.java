package bookshop.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bookshop.vo.AuthorVo;
import bookshop.vo.BookVo2;

public class BookDaoTest {
	private static AuthorDao authorDao = new AuthorDao();
	private static BookDao2 bookDao = new BookDao2();
	private static AuthorVo mockAuthorVo = new AuthorVo();
	
	
	@BeforeAll
	public static void setup() {
		mockAuthorVo.setName("칼세이건");
		authorDao.insert(mockAuthorVo);
	}
	@Test
	public void insertTest() {
		BookVo2 bookVo = new BookVo2();
		bookVo.setTitle("코스모");
		bookVo.setAuthorId(mockAuthorVo.getId());
		
		bookDao.insert(bookVo);
	}
	
	@AfterAll
	public static void cleanup() {
		bookDao.deleteAll();
		authorDao.deleteById(mockAuthorVo.getId());
	}
}
