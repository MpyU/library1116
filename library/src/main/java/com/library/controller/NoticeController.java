package com.library.controller;

import com.library.pojo.Notice;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.NoticeService;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/get")
    public Result<Notice> get(Notice notice){
        Notice n = noticeService.get(notice);
        if(n != null){
            return new Result<>(ResultCode.SUCCESS,"查询消息成功！",notice);
        }
        return new Result(ResultCode.FAIL,"查询消息失败！");
    }
}
