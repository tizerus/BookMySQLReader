package by.htp.db.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import by.htp.db.domain.service.CatalogService;
import by.htp.db.domain.service.impl.DefaultCatalogImpl;
import by.htp.db.domain.vo.Catalog;

public class CatalogServiceTest {
	
	private static CatalogService service;
	@BeforeClass
	public static void initCatalogServiceTest(){
		service = new DefaultCatalogImpl();
	}

	@Test
	public void testCatalogNull() {
		Catalog catalog = service.getCatalog();
		assertNotNull("Catalog was not created", catalog);
		
	}
	@Test
	public void testCatalogNotEmpty() {
		Catalog catalog = service.getCatalog();
		assertNotNull("Title is null", catalog.getTitle());
		assertNotNull("DateCreation is null", catalog.getDateCreation());
		assertNotNull("Book is null", catalog.getBooks());
		
	}
	@Test
	public void testCatalogEmptyBooks() {
		Catalog catalog = service.getCatalog();
		assertNotNull("Books is null", catalog.getBooks());
		assertTrue("There are 0 books in catalog", catalog.getBooks().size()>0);
		
	}
}
