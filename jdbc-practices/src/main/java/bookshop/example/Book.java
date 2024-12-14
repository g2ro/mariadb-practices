package bookshop.example;

public class Book {
	private int no;
	private String title;
	private String author;
	private int stateCode;
	
	// 생성자
	public Book(int no, String title, String author) {
		this.no = no;
		this.title = title;
		this.author = author;
		this.stateCode = 1;
	}
	

	// getter setter
	public int getNo() {
		return no;
	}



	public void setNo(int no) {
		this.no = no;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public int getStateCode() {
		return stateCode;
	}



	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}


	// rent함수
	public void rent() {
		this.stateCode = 0;
		
		System.out.println(this.title + "이(가)대여 됐습니다.");
	}
	// 틀린 코드
//	public void rent(book b) {
//		b.stateCode = 0;
//		
//		System.out.println(b.title + "이(가)대여 됐습니다.");
//	}
}
