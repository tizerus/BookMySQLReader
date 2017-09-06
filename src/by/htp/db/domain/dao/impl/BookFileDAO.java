package by.htp.db.domain.dao.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.htp.db.domain.dao.BookDao;
import by.htp.db.domain.entity.Book;

public class BookFileDAO implements BookDao {

	@Override
	public List<Book> fetchBooks() {
		Book book = null;
		List<Book> books = new ArrayList<Book>();
		String[] arr;
		try (BufferedReader bf = new BufferedReader(new FileReader("D:\\Work\\eclipse\\workspace\\books.txt"))) {
			while (bf.ready() == true) {
				String s = bf.readLine();
				arr = s.split("[\\s+]");
				book = new Book();
				book.setId(Integer.parseInt(arr[0]));
				book.setTitle(arr[1]);
				book.setPages(Integer.parseInt(arr[2]));
				Date date;
				try {
					SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
					date = fr.parse(arr[3]);
					book.setDate(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				books.add(book);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public Book getBookById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeBookById(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> searchBookByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> fetchBooksByDate(Date date) {
		Book book = null;
		List<Book> books = new ArrayList<Book>();
		String[] arr;
		
		try (BufferedReader bf = new BufferedReader(new FileReader("D:\\Work\\eclipse\\workspace\\books.txt"))) {
			while (bf.ready() == true) {
				String s = bf.readLine();
				arr = s.split("[\\s+]");
				SimpleDateFormat strToDate = new SimpleDateFormat("yyyy-MM-dd");
				Date bookFileDate = strToDate.parse(arr[3]);
				if (bookFileDate.getDay()==date.getDay() && bookFileDate.getMonth()==date.getMonth() && bookFileDate.getYear()==date.getYear()) {
					book = new Book();
					book.setId(Integer.parseInt(arr[0]));
					book.setTitle(arr[1]);
					book.setPages(Integer.parseInt(arr[2]));
					Date newDate;
					try {
						SimpleDateFormat fr = new SimpleDateFormat("yyyy-MM-dd");
						newDate = fr.parse(arr[3]);
						book.setDate(newDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					books.add(book);
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return books;
	}

}
