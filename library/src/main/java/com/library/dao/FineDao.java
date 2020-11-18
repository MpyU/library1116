package com.library.dao;

import com.library.pojo.Fine;

import java.util.List;

public interface FineDao extends BaseDao<Fine> {
    public List<Fine> selectAllByCondition(Fine fine) ;

}
