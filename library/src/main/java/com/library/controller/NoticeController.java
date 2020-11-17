package com.library.controller;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Notice;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.NoticeService;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/get/{id}")
    public Result<Notice> get(@PathVariable("id")Integer id){
        Notice n = noticeService.get(id);
        if(n != null){
            return new Result<>(ResultCode.SUCCESS,"查询消息成功！",n);
        }
        return new Result(ResultCode.FAIL,"查询消息失败！");
    }

    @GetMapping("/getByUserId/{id}/{pageSize}/{currentPage}")
    public Result<PageInfo<Notice>> getByUserId(@PathVariable("id")Integer id
             ,@PathVariable("pageSize")Integer pageSize,@PathVariable("currentPage")Integer currentPage){
        PageInfo<Notice> pageInfo = noticeService.getByUserId(id,currentPage,pageSize);
        if(pageInfo.getList().size() > 0){
            return new Result<>(ResultCode.SUCCESS,"查询消息成功！",pageInfo);
        }
        return new Result(ResultCode.FAIL,"查询消息失败！");
    }

    @GetMapping("/select/{pageSize}/{currentPage}")
    public Result<PageInfo<Notice>> select(@PathVariable("pageSize")Integer pageSize
            ,@PathVariable("currentPage")Integer currentPage){

        if(pageSize == null){
            pageSize = 5;
        }
        if(currentPage == null){
            currentPage = 1;
        }

        PageInfo<Notice> pageInfo = noticeService.selectAll(currentPage, pageSize);
        if(pageInfo.getList().size() > 0){
            return new Result(ResultCode.SUCCESS,"查询所有消息成功！",pageInfo);
        }
        return new Result(ResultCode.FAIL,"查询所有消息失败！");
    }

    @PostMapping("/save")
    public Result<Integer> save(Notice notice){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        notice.setPublishDate(date);
        int row = noticeService.save(notice);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"添加消息成功！",row);
        }
        return new Result(ResultCode.FAIL,"添加消息失败！");
    }

    @PutMapping("/update")
    public Result<Integer> update(Notice notice){
        int row = noticeService.update(notice);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"修改消息成功！",row);
        }
        return new Result(ResultCode.FAIL,"修改消息失败！");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id")Integer id){
        int row = noticeService.delete(id);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"删除消息成功！",row);
        }
        return new Result(ResultCode.FAIL,"删除消息失败！");
    }

}
