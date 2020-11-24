package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.BookDao;
import com.library.dao.CategoryDao;
import com.library.pojo.Book;
import com.library.pojo.Category;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

	@Autowired(required = false)
	private BookDao bookDao;

	@Autowired(required = false)
	private CategoryDao categoryDao;

	@Override
	public Book get(Book book) {
		Book book1 = bookDao.get(book);
		if(book1!=null && book.getCid()!=null){
			Category category = categoryDao.get(new Category(book1.getCid()));
			book1.setCategory(category);
		}

		return book1;
	}

	@Override
	public PageInfo<Book> selectAllByCondition(Integer currentPage, Integer pageSize, Book book) {

		if (book.getBookName() != null) {
			book.setBookName("%" + book.getBookName() + "%");
		}

		PageHelper.startPage(currentPage, pageSize);
		List<Book> books = bookDao.selectAllByCondition(book);
		for (Book b : books) {
			if (b.getCid() != null) {
				Category category = categoryDao.get(new Category(b.getCid()));
				b.setCategory(category);
			}

		}
		PageInfo<Book> pageInfo = new PageInfo<>(books);
		return pageInfo;
	}

	@Override
	public PageInfo<Book> selectAll(Integer currentPage, Integer pageSize) {

		System.out.println(currentPage + "--" + pageSize);
		PageHelper.startPage(currentPage, pageSize);
		List<Book> books = bookDao.selectAll();
		for (Book b : books) {

			if (b.getCid() != null) {
				Category category = categoryDao.get(new Category(b.getCid()));
				b.setCategory(category);
			}

		}

		PageInfo<Book> pageInfo = new PageInfo<>(books);
		return pageInfo;
	}

	@Override
	public int save(Book book) {
		int cid = Integer.MAX_VALUE;
		if (book.getCid() != null) {
			cid = book.getCid();
			Category category = categoryDao.get(new Category(cid));
			if(category!=null&&category.getFloor()!=null){
				book.setBookFloor(category.getFloor());
			}

		}
		int result = bookDao.save(book);
		// int i = 10 / 0;
		return result;
	}

	@Override
	public int update(Book book) {
		return bookDao.update(book);
	}

	@Override
	public int delete(Integer id) {
		return bookDao.delete(id);
	}

	public Book bookNum(Integer bookId) {
		Book book = bookDao.bookNum(bookId);
		return book;
	}

	// 减库存
	public int subBook(int bookId, int num) {
		return bookDao.subBook(bookId, num);
	}

	@Override
	public int bookUp(Integer bookId) {
		return bookDao.bookUp(bookId);
	}

	@Override
	public int bookDown(Integer bookId) {
		return bookDao.bookDown(bookId);
	}

	@Override
	public int hitsAdd(Integer id) {
		return bookDao.hitsAdd(id);
	}

	@Override
	public int monthClickToZero() {
		return bookDao.monthClickToZero();
	}

	@Override
	public int dayClickToZero() {
		return bookDao.dayClickToZero();
	}

}
