package com.library.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.BookDao;
import com.library.dao.CategoryDao;
import com.library.dao.UserBookDao;
import com.library.dao.UserDao;
import com.library.pojo.Book;
import com.library.pojo.Category;
import com.library.pojo.User;
import com.library.pojo.UserBook;
import com.library.service.UserBookService;

@Service
public class UserBookServiceImpl implements UserBookService {

	@Autowired
	private UserBookDao userBookDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BookDao bookDao;
	@Autowired
	CategoryDao categoryDao;

	@Override
	public UserBook get(UserBook userBook) {
		UserBook ub = userBookDao.get(userBook);
		if (ub.getUid() != null) {
			User user = userDao.get(new User(ub.getUid()));
			ub.setUser(user);
		}
		if (ub.getBid() != null) {
			Book book = bookDao.get(new Book(ub.getBid()));
			ub.setBook(book);
		}
		return ub;
	}

	@Override
	public PageInfo<UserBook> selectAll(Integer currentPage, Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<UserBook> userBooks = userBookDao.selectAll();
		for (UserBook userBook : userBooks) {
			if (userBook.getUid() != null) {
				User user = userDao.get(new User(userBook.getUid()));
				userBook.setUser(user);
			}
			if (userBook.getBid() != null) {
				Book book = bookDao.get(new Book(userBook.getBid()));
				userBook.setBook(book);
			}

		}
		PageInfo<UserBook> pageInfo = new PageInfo<>(userBooks);
		return pageInfo;
	}

	@Override
	public int save(UserBook userBook) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");
		String date = simpleDateFormat.format(new Date());
		userBook.setLendDate(date);
		return userBookDao.save(userBook);
	}

	@Override
	public int update(UserBook userBook) {
		return userBookDao.update(userBook);
	}

	@Override
	public int delete(Integer id) {
		return userBookDao.delete(id);
	}

	@Override
	public PageInfo<UserBook> getByUidOrBid(Integer currentPage, Integer pageSize, UserBook userBook) {
		PageHelper.startPage(currentPage, pageSize);
		List<UserBook> userBooks = userBookDao.getByUidOrBid(userBook);
		for (UserBook ub : userBooks) {
			if (ub.getBid() != null) {
				Book book = bookDao.get(new Book(ub.getBid()));
				ub.setBook(book);
			}
			if (ub.getUid() != null) {
				User user = userDao.get(new User(ub.getUid()));
				ub.setUser(user);
			}

		}
		PageInfo<UserBook> pageInfo = new PageInfo<>(userBooks);
		return pageInfo;
	}

	// 查询还在借的书，看是否已经借够三本
	public Boolean canLendBook(Integer userId) {
		List<UserBook> list = userBookDao.hasLendBooks(userId);
		if (list.size() < 3) {
			return true;
		}
		return false;
	}

	public int lendBook(Map<String, Object> map) {
		return userBookDao.lendBook(map);

	}

	@Override
	public int returnBook(Integer userId, Integer bookId) {
		String returnDate = null;
		LocalDateTime localDate = LocalDateTime.now();
		returnDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
		return userBookDao.returnBook(userId, bookId, returnDate);
	}

	@Override
	public PageInfo<UserBook> selectAllByUserId(Integer userId, Integer currentPage, Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<UserBook> list = userBookDao.selectAllByUserId(userId);
		for (UserBook userBook : list) {
			Integer bookId = userBook.getBid();

			Book book = bookDao.get(new Book(bookId));
			Integer categoryId = book.getCid();
			if (categoryId != null) {
				Category category = categoryDao.get(new Category(categoryId));
				System.err.println("category:" + category);
				book.setCategory(category);
			}
			userBook.setBook(book);
			User user = userDao.get(new User(userId));
			userBook.setUser(user);

		}
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<UserBook> selectAllByBookName(String bookName, Integer currentPage, Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);

		bookName = "%" + bookName + "%";
		System.out.println("bookName:" + bookName);
		List<UserBook> list = userBookDao.selectAllByBookName(bookName);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<UserBook> selectAllNoReturn(Integer currentPage, Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<UserBook> userBooks = userBookDao.selectAllNoReturn();
		for (UserBook userBook : userBooks) {
			User user = userDao.get(new User(userBook.getUid()));
			Book book = bookDao.get(new Book(userBook.getBid()));
			userBook.setUser(user);
			userBook.setBook(book);
		}
		PageInfo<UserBook> pageInfo = new PageInfo<>(userBooks);
		return pageInfo;
	}
}
