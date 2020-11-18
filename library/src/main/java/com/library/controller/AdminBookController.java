package com.library.controller;

import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/book")
public class AdminBookController {
    @Autowired
    BookService bookService;


    //图书上架
    @PutMapping("/up/{bookId}")
    public Result bookUp(@PathVariable("bookId")Integer bookId){
        bookService.bookUp(bookId);
        return new Result<>(ResultCode.SUCCESS,"上架成功");
    }
    @PutMapping("/down/{bookId}")
    public Result bookDown(@PathVariable("bookId")Integer bookId){
        bookService.bookDown(bookId);
        return new Result<>(ResultCode.SUCCESS,"下架成功");
    }
//http://10.10.102.163:8001/admin/book/list/pageSize/currentPage
    @GetMapping("list/{pageSize}/{currentPage}")
    public String list(@PathVariable("pageSize") Integer pageSize,@PathVariable("currentPage") Integer currentPage){
        return "redirect:/book/select/"+pageSize+"/"+currentPage;
    }

}
