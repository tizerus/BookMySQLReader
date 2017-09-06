package by.htp.db.domain.dao;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import by.htp.db.domain.dao.impl.BookFileDAO;
import by.htp.db.domain.dao.impl.BookMySqlDAO;
import by.htp.db.domain.entity.Book;

public class BookDaoTest {

	private static BookDao dao;
	private static BookDao fileDao;
	
	public static Boolean dateCompare(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}
	
	@BeforeClass
	public static void initDao() {
		dao = new BookMySqlDAO();
		fileDao = new BookFileDAO();
	}

	/*@Test
	public void testNullList() {
		List<Book> books = dao.fetchBooks();
		assertNotNull("The returned list is null", books);
	}

	@Test
	public void testEmpty() {
		List<Book> books = dao.fetchBooks();
		assertTrue("The returned list contains null books", books.size() > 0);
	}*/
	//-------------------homework--------------------------
	@Test
	public void testRemoveBookById() {
		List<Book> books = dao.fetchBooks();
		int count = books.size();
		dao.removeBookById(5);
		List<Book> newBooks = dao.fetchBooks();
		assertTrue("No book was deleted", count == newBooks.size() + 1);
	}
	@Test
	public void testCheckIdOfDeletedBook() {
		int removeId = 5;
		dao.removeBookById(removeId);
		List<Book> books = dao.fetchBooks();
		for(int i = 0; i < books.size(); i++){
			assertTrue("The book with id " + removeId + " was not removed", books.get(i).getId() != removeId);
		}
	}
	@Test
	public void testSearchByTitle() {
		String searchingTitle = "ук";
		List<Book> books = dao.searchBookByTitle(searchingTitle);
		assertTrue("Array is empty", books.size()>0);
		for(int i = 0; i < books.size(); i++){
			assertTrue("Wrong result of searching: " + books.get(i).getTitle() + " does not contain " + searchingTitle, books.get(i).getTitle().toLowerCase().contains(searchingTitle.toLowerCase()) == true);
		}
	}
	@Test
	public void testSearchByTitleNoResult() {
		String searchingTitle = "no such result";
		List<Book> books = dao.searchBookByTitle(searchingTitle);
		assertTrue("Array is not empty, there is no such title in sql base", books.size()==0);
		
	}
	// tests for method getBookById

	@Test
	public void testBookNotNullById() {
		Book book = dao.getBookById(2);
		assertNotNull("The returned book is null", book != null);
	}

	@Test
	public void testEmptyBookById() {
		Book book = dao.getBookById(2);
		assertTrue("The returned book contain zero or null", book.getId() != 0 && book.getTitle() != null && book.getPages() != 0);
	}

	@Test
	public void testGetBooksByDate() {
		Calendar calendar = Calendar.getInstance();
	    calendar.set(2008, 4, 25); 
		Date ourDate = calendar.getTime();
		
		List<Book> books = dao.fetchBooksByDate(ourDate);
		for(int i = 0; i< books.size(); i ++){
			assertTrue("the dates are different", books.get(i).getDate().before(ourDate));
		}
	}
	//-----------------------file test ------------------
	@Test
	public void testBooksFromFile() {
		List<Book> books = fileDao.fetchBooks();
		assertTrue("The returned list contains null book(s)", books != null); 
	}
	@Test
	public void testBooksFromFileByDate() {
		Calendar calendar = Calendar.getInstance();
	    calendar.set(2008, 9, 20); 
		Date ourDate = calendar.getTime();
		
		List<Book> books = fileDao.fetchBooksByDate(ourDate);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i < books.size(); i++){
			assertTrue("the dates are different", dateCompare(ourDate, books.get(i).getDate()) == true); 
		}
		
		
	}
}
