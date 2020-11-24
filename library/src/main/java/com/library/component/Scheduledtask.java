package com.library.component;

import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduledtask {

    @Autowired
    BookService bookService;
    //每日把日点击量设置为0
    //每天12点整
    @Scheduled(cron="0 0 0 * * ?")
//    @Scheduled(cron="0 17 13 * * ?")
    public void updateDayClickCount(){
        System.out.println("调用日点击量清空方法");
        bookService.dayClickToZero();


    }
    //每月更新月点击量设置为0
    @Scheduled(cron="0 0 0 1 * ?")
//    @Scheduled(cron="0 18 13 24 * ?")
    public void updateMonthClickCount(){
        System.out.println("调用月点击量清空方法");
        bookService.monthClickToZero();
    }


}
