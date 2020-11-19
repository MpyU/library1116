package com.library.service.impl;

import com.library.dao.SummaryDao;
import com.library.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;

public class SummaryServiceImpl implements SummaryService {
    @Autowired
    SummaryDao summaryDao;
    @Override
    public int summary(String startDay, String endDay, int status) {
        return summaryDao.summary(startDay,endDay,status);
    }
}
