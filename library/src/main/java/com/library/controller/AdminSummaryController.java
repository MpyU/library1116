package com.library.controller;

import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

//http://10.10.102.163:8001/admin/summary/pageSize/currentPage?status=0 &date=2020-11-1
//统计
@RestController
@RequestMapping("/admin/")
public class AdminSummaryController {
    @Autowired(required = false)
    SummaryService summaryService;
    //status
    // 0:按日查询
    //1:按月查询
    //2：按年查询
    @GetMapping("summary")
    public Result summary(int status,String pdate) throws Exception {
        Date date=new SimpleDateFormat("yyyy-MM-dd").parse(pdate);
        int day=0,month=0,year=0;
        LocalDateTime startDate=null;
        LocalDateTime endDate=null;
        int lastDay=1;

        Date myDate=new Date();
        switch(status){
            case 0:

                startDate=LocalDateTime.of(myDate.getYear(),myDate.getMonth(),date.getDay(),0,0,0);
                endDate=LocalDateTime.of(myDate.getYear(),myDate.getMonth(),date.getDay(),23,59,59);
                break;
            case 1:

                startDate=LocalDateTime.of(myDate.getYear(),date.getMonth(),1,0,0,0);
                lastDay=endDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                endDate=LocalDateTime.of(myDate.getYear(),date.getMonth(),lastDay,23,59,59);
                break;
            case 2:

                startDate=LocalDateTime.of(date.getYear(),1,1,0,0,0);

                endDate=LocalDateTime.of(date.getYear(),12,1,23,59,59);
                lastDay=endDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
                endDate=LocalDateTime.of(date.getYear(),12,lastDay,23,59,59);

                break;
        }
       // summaryService.summary(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),status);
        return new Result(ResultCode.FAIL,"统计失败");
    }
}
