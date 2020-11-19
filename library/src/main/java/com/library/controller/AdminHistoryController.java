package com.library.controller;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.pojo.UserBook;
import com.library.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminHistoryController {
    @Autowired
    UserBookService userBookService;
//    借阅历史 http://10.10.102.163:8001/history/select/pageSize/currentPage
//    参数：
//    pageSize：每页显示大小
//    currentPage：当前页
    @RequestMapping("/history/select/{pageSize}/{currentPag}")
   public Result<PageInfo<UserBook>> history(@PathVariable("pageSize")Integer pageSize, @PathVariable("currentPag")Integer currentPag){
        PageInfo<UserBook> pageInfo= userBookService.selectAll(currentPag,pageSize);
        if(pageInfo.getList().size()>0){
            return new Result<>(ResultCode.SUCCESS,"查询成功",pageInfo);
        }
        return new Result<>(ResultCode.FAIL,"查询失败");
    }

//
//            查询单个用户借阅历史
//    http://10.10.102.163:8001/admin/history/userId/pageSize/currentPage
//    参数：
//    userId：查询的用户ID
//    pageSize：每页显示大小
//    currentPage：当前页
@GetMapping("/admin/history/{userId}/{pageSize}/{currentPage}")
public Result<PageInfo<UserBook>> historyByUserId(@PathVariable("userId") Integer userId,@PathVariable("pageSize")Integer pageSize, @PathVariable("currentPage")Integer currentPage){
    PageInfo<UserBook> pageInfo= userBookService.selectAllByUserId(userId,currentPage,pageSize);
    if(pageInfo.getList().size()>0){
        return new Result<>(ResultCode.SUCCESS,"查询成功",pageInfo);
    }
    return new Result<>(ResultCode.FAIL,"查询失败");
}



//    按书名模糊查询借阅状态
//    http://10.10.102.163:8001/admin/book/search
//    参数：表单提交

    @GetMapping("/admin/history/search/{pageSize}/{currentPage}")
    public Result<PageInfo<UserBook>> historyByUserBookName(@RequestParam("bookName") String bookName, @PathVariable("pageSize")Integer pageSize, @PathVariable("currentPage")Integer currentPage){
        PageInfo<UserBook> pageInfo= userBookService.selectAllByBookName(bookName,currentPage,pageSize);
        if(pageInfo.getList().size()>0){
            return new Result<>(ResultCode.SUCCESS,"查询成功",pageInfo);
        }
        return new Result<>(ResultCode.FAIL,"查询失败");
    }
}
