package com.library.service.impl;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Fine;
import com.library.service.FineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FineServiceImpl implements FineService {
    @Override
    public Fine get(Fine fine) {
        return null;
    }

    @Override
    public PageInfo<Fine> selectAll(Integer currentPage,Integer pageSize) {
        return null;
    }

    @Override
    public PageInfo<Fine> selectAllByCondition(Integer currentPage,Integer pageSize,Fine fine) {
        return null;
    }

    @Override
    public int save(Fine fine) {
        return 0;
    }

    @Override
    public int update(Fine fine) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }
}
