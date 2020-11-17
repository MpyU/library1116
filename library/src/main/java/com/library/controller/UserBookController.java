package com.library.controller;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.pojo.UserBook;
import com.library.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userBook")
@CrossOrigin
public class UserBookController {

    @Autowired
    private UserBookService userBookService;


    @PostMapping("/save")
    public Result<Integer> save(UserBook userBook){
        int row = userBookService.save(userBook);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"添加借阅表信息成功！",row);
        }
        return new Result(ResultCode.FAIL,"添加借阅表信息失败！");
    }

    @GetMapping("/get/{id}")
    public Result<UserBook> get(@PathVariable("id")Integer id){
        UserBook userBook = userBookService.get(new UserBook(id));
        if(userBook != null){
            return new Result(ResultCode.SUCCESS,"查询借阅表历史成功！",userBook);
        }
        return new Result(ResultCode.FAIL,"查询借阅表历史失败！");
    }

    @GetMapping("/getByUidOrBid/{pageSize}/{currentPage}")
    public Result<UserBook> getByUidOrBid(@PathVariable("pageSize")Integer pageSize,
                      @PathVariable("currentPage")Integer currentPage, UserBook userBook){
        if(pageSize == null){
            pageSize = 5;
        }
        if(currentPage == null){
            currentPage = 1;
        }
        PageInfo<UserBook> pageInfo = userBookService.getByUidOrBid(currentPage,pageSize,userBook);
        if(pageInfo.getList().size() > 0){
            return new Result(ResultCode.SUCCESS,"查询指定用户所有借阅历史信息成功！",pageInfo);
        }
        return new Result(ResultCode.FAIL,"查询指定用户所有借阅历史信息失败！");
    }

    @GetMapping("/select/{pageSize}/{currentPage}")
    public Result<PageInfo<UserBook>> select(@PathVariable("pageSize")Integer pageSize
                          ,@PathVariable("currentPage")Integer currentPage){
        if(pageSize == null){
            pageSize = 5;
        }
        if(currentPage == null){
            currentPage = 1;
        }
        PageInfo<UserBook> pageInfo = userBookService.selectAll(currentPage, pageSize);
        if(pageInfo.getList().size() > 0){
            return new Result(ResultCode.SUCCESS,"查询所有借阅历史成功！",pageInfo);
        }
        return new Result(ResultCode.FAIL,"查询所有借阅历史失败！");
    }

    @PutMapping("/update")
    public Result<Integer> update(UserBook userBook){
        int row = userBookService.update(userBook);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"修改借阅表信息成功！",row);
        }
        return new Result(ResultCode.FAIL,"修改借阅表信息失败！");
    }

    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id")Integer id){
        int row = userBookService.delete(id);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"删除借阅表信息成功！",row);
        }
        return new Result(ResultCode.FAIL,"删除借阅表信息失败！");
    }


}
