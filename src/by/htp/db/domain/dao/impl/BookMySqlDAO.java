package by.htp.db.domain.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.htp.db.domain.dao.BookDao;
import by.htp.db.domain.entity.Book;

public class BookMySqlDAO implements BookDao {
	private static final String DB_URL = "jdbc:mysql://localhost/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

	private static final String SQL_SELECT_BOOKS = "select * from book";

	public static Connection connection() {
		Connection con = null;
		try {
			Class.forName(DB_DRIVER);
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	@Override
	public List<Book> fetchBooks() {
		List<Book> books = new ArrayList<Book>();

		Connection con = connection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(SQL_SELECT_BOOKS);
			Book book = null;
			while (rs.next()) {
				book = new Book();
				book.setId(rs.getInt("id"));
				// int id = rs.getInt("id");
				book.setTitle(rs.getString("title"));
				// String title = rs.getString("title");
				book.setPages(rs.getInt("pages"));
				// int pages = rs.getInt("pages");
				// System.out.println(id + " " + title + " " + pages);
				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}

	@Override
	public Book getBookById(int id) {

		final String SQL_SELECT_BOOKS_BY_ID = "select * from book where id = ?";
		Book book = null;

		Connection con = connection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(SQL_SELECT_BOOKS_BY_ID);
			ps.setInt(1, id); 
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setPages(rs.getInt("pages"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return book;
	}

	@Override
	public void removeBookById(int id) {

		final String SQL_REMOVE_BOOKS_BY_ID = "delete from book where id = ?";
		Connection con = connection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(SQL_REMOVE_BOOKS_BY_ID);
			ps.setInt(1, id); 
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Book> searchBookByTitle(String title) {

		final String SQL_SELECT_BOOKS_BY_ID = "SELECT * FROM book WHERE title like ?";
		String newTitle = "%" + title + "%";
		List<Book> books = new ArrayList<Book>();
		Book book = null;

		Connection con = connection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(SQL_SELECT_BOOKS_BY_ID);

			ps.setString(1, newTitle); 
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setPages(rs.getInt("pages"));
				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}

	@Override
	public List<Book> fetchBooksByDate(Date date) { // 2008-11-12
		final String SQL_SELECT_BOOKS_BY_DATE = "select * from book where date <= ?";
		Book book = null;
		List<Book> books = new ArrayList<Book>();
		Connection con = connection();

		try {
			PreparedStatement ps = con.prepareStatement(SQL_SELECT_BOOKS_BY_DATE);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			ps.setDate(1, sqlDate); 
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setPages(rs.getInt("pages"));
				book.setDate(rs.getDate("date"));
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}

}
