package com.library.controller;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import org.springframework.web.bind.annotation.*;

//http://10.10.102.163:8001/admin/message/pageSize/currentPage
@RestController
@RequestMapping("/admin/message")
public class AdminMessageController {
    @GetMapping("/message/{pageSize}/{currentPage}")
    public Result<PageInfo<Notice>> messgae(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage")Integer currentPage){
        return new Result<>(ResultCode.SUCCESS,"查询成功");
    }
    @GetMapping("/detail/{bookId}")
    public Result<Notice> messageDetail(@PathVariable("bookId")Integer bookId){
        return new Result<>(ResultCode.SUCCESS,"查询成功");
    }

    @PostMapping("/add")
    public Result messageAdd(Notice notice){
        return new Result<>(ResultCode.SUCCESS,"发布消息成功");
    }


}
