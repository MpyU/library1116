package com.library.dao;

import java.util.List;

public interface BaseDao<T> {
    T get(T t);
    List<T> selectAll();
    int save(T t);
    int update(T t);
    int delete(Integer id);
}
