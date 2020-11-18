package com.library.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.dao.FineDao;
import com.library.pojo.Fine;
import com.library.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FineServiceImpl implements FineService {
    @Autowired(required = false)
    FineDao fineDao;

    @Override
    public Fine get(Fine fine) {
        setFineDesc(fine);
        return fineDao.get(fine);
    }

    @Override
    public PageInfo<Fine> selectAll(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Fine> list = fineDao.selectAll();
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Fine> selectAllByCondition(Integer currentPage, Integer pageSize, Fine fine) {
        setFineDesc(fine);
        PageHelper.startPage(currentPage,pageSize);
        List<Fine> list=fineDao.selectAllByCondition(fine);
        PageInfo<Fine> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(Fine fine) {
        return fineDao.save(fine);
    }

    @Override
    public int update(Fine fine) {

        return fineDao.update(fine);
    }
    private void  setFineDesc(Fine fine){
        if(fine.getFineDesc()!=null){
            fine.setFineDesc("%"+fine.getFineDesc()+"%");
        }
    }
    @Override
    public int delete(Integer id) {
        return fineDao.delete(id);
    }
}
