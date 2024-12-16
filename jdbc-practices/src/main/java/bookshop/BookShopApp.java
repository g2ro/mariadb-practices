package bookshop;

import java.util.List;
import java.util.Scanner;

import bookshop.dao.BookDao2;
import bookshop.vo.BookVo2;
import bookshop.dao.AuthorDao;
import bookshop.vo.AuthorVo;

public class BookShopApp {

	public static void main(String[] args) {
		installDB();

		displayBookInfo();

		Scanner scanner = new Scanner(System.in);
		System.out.print("대여하고 싶은 책의 번호를 입력하세요:");
		Long no = scanner.nextLong();
		scanner.close();

		new BookDao2().update(no, "대여중");

		displayBookInfo();
	}

	private static void displayBookInfo() {
		System.out.println("*****도서 정보 출력*****");

		List<BookVo2> list = new BookDao2().findAll();
		for (BookVo2 vo : list) {
			String info = String.format("[%d] 제목: %s, 작가: %s, 대여유무: %s", vo.getId(), vo.getTitle(), vo.getAuthorId(),
					vo.getStatus());
			System.out.println(info);
		}
	}

	private static void installDB() {
		AuthorDao authorDao = new AuthorDao();
		BookDao2 BookDao2 = new BookDao2();

		BookDao2.deleteAll();
		authorDao.deleteAll();

		AuthorVo authorVo = null;
		BookVo2 BookVo2 = null;

		//
		authorVo = new AuthorVo();
		authorVo.setName("스테파니메이어");
		authorDao.insert(authorVo);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("트와일라잇");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("뉴문");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("이클립스");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("브레이킹던");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		//
		authorVo = new AuthorVo();
		authorVo.setName("조정래");
		authorDao.insert(authorVo);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("아리랑");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		//
		authorVo = new AuthorVo();
		authorVo.setName("김동인");
		authorDao.insert(authorVo);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("젊은그들");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		//
		authorVo = new AuthorVo();
		authorVo.setName("김난도");
		authorDao.insert(authorVo);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("아프니깐 청춘이다");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		//
		authorVo = new AuthorVo();
		authorVo.setName("천상병");
		authorDao.insert(authorVo);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("귀천");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		//
		authorVo = new AuthorVo();
		authorVo.setName("조정래");
		authorDao.insert(authorVo);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("태백산맥");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);

		//
		authorVo = new AuthorVo();
		authorVo.setName("원수연");
		authorDao.insert(authorVo);

		BookVo2 = new BookVo2();
		BookVo2.setTitle("풀하우스");
		BookVo2.setAuthorId(authorVo.getId());
		BookDao2.insert(BookVo2);
	}

}
