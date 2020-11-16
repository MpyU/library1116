package com.library.service;

import com.library.pojo.Fine;

import java.util.List;

public interface FineService {
    Fine get(Fine t);
    List<Fine> selectAll();
    int save(Fine t);
    int update(Fine t);
    int delete(Integer id);
}
