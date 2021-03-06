package com.library.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.library.pojo.UserBook;

public interface UserBookDao extends BaseDao<UserBook> {

	List<UserBook> getByUidOrBid(UserBook userBook);

	public List<UserBook> hasLendBooks(Integer userId);

	public Integer lendBook(Map<String, Object> map);

	public int returnBook(@Param("userId") Integer userId, @Param("bookId") Integer bookId,
			@Param("returnDate") String returnDate);

	List<UserBook> selectAllByUserId(Integer userId);

	List<UserBook> selectAllByBookName(String bookName);

	List<UserBook> selectAllNoReturn();
}
