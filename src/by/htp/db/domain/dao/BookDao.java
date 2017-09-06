package by.htp.db.domain.dao;

import java.util.Date;
import java.util.List;

import by.htp.db.domain.entity.Book;

public interface BookDao {
	List<Book> fetchBooks();
	Book getBookById(int id);
	void removeBookById(int id);
	public List<Book> searchBookByTitle(String title);
	List<Book> fetchBooksByDate(Date date);
	
}
