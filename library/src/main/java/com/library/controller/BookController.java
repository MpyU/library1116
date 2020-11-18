package com.library.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.pojo.Book;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "图书管理接口")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;


    @ApiOperation("根据书的相关信息查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "书的ID"),
            @ApiImplicitParam(name = "bookName", value = "书名"),
            @ApiImplicitParam(name = "searchId", value = "检索号"),
            @ApiImplicitParam(name = "cardId", value = "书的编号"),
            @ApiImplicitParam(name = "author", value = "书的作者")
    })
    @GetMapping("/get")
    public Result<Book> get(Book b){
        Book book = bookService.get(b);
        if(book != null){
            return new Result(ResultCode.SUCCESS,"查询图书信息成功！",book);
        }
        return new Result(ResultCode.FAIL,"查询图书信息失败！");
    }

    /**
     * 查询所有图书信息
     * @param pageSize
     * @param currentPage
     * @return
     */
    @ApiOperation("分页查询所有书籍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量",required=true),
            @ApiImplicitParam(name = "currentPage", value = "当前页",required=true)
    })
    @GetMapping("/select/{pageSize}/{currentPage}")
    public Result<PageInfo<Book>> select(@PathVariable("pageSize")Integer pageSize,
           @PathVariable("pageSize")Integer currentPage){
          if(pageSize==null){
              pageSize=5;
          }
          if(currentPage==null){
              currentPage=1;
          }
        PageInfo<Book> pageInfo = bookService.selectAll(currentPage, pageSize);
        if(pageInfo.getList().size() > 0){
            return new Result(ResultCode.SUCCESS,"查询所有图书信息成功！",pageInfo);
        }
        return new Result(ResultCode.FAIL,"查询所有图书信息失败！");
    }
    /**
     * 按条件查询所有图书信息
     * @param pageSize
     * @param currentPage
     * @param book
     * @return
     */
    @ApiOperation("根据条件分页查询所有书籍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量",required=true),
            @ApiImplicitParam(name = "currentPage", value = "当前页",required=true),
            @ApiImplicitParam(name = "id", value = "书的ID"),
            @ApiImplicitParam(name = "bookName", value = "书名"),
            @ApiImplicitParam(name = "searchId", value = "检索号"),
            @ApiImplicitParam(name = "cardId", value = "书的编号"),
            @ApiImplicitParam(name = "author", value = "书的作者")
    })
    @GetMapping("/selectAllByCondition/{pageSize}/{currentPage}")
    public Result<PageInfo<Book>> selectAllByCondition(@PathVariable("pageSize")Integer pageSize,
             @PathVariable("pageSize")Integer currentPage,
             Book book){
        if(pageSize == null){
            pageSize = 5;
        }
        if(currentPage == null){
            currentPage = 1;
        }
        PageInfo<Book> pageInfo = bookService.selectAllByCondition(currentPage, pageSize, book);
        if(pageInfo.getList().size() > 0){
            return new Result(ResultCode.SUCCESS,"查询所有图书信息成功！",pageInfo);
        }
        return new Result(ResultCode.FAIL,"查询所有图书信息失败！");
    }

    @ApiOperation("新增书籍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookName", value = "书名",defaultValue = "西游记"),
            @ApiImplicitParam(name = "searchId", value = "检索号",defaultValue = "ISCN-1"),
            @ApiImplicitParam(name = "cardId", value = "书的编号",defaultValue = "ISBN-1"),
            @ApiImplicitParam(name = "cid", value = "所属类别",defaultValue = "22"),
            @ApiImplicitParam(name = "author", value = "书的作者",defaultValue = "罗贯中"),
            @ApiImplicitParam(name = "bookDesc", value = "书的描述",defaultValue = "唐僧师徒三人取经..."),
            @ApiImplicitParam(name = "price", value = "书的价格",defaultValue = "22.9"),
            @ApiImplicitParam(name = "press", value = "出版社",defaultValue = "xxxx"),
            @ApiImplicitParam(name = "pressDate", value = "出版日期",defaultValue = "2000年11月5日"),
            @ApiImplicitParam(name = "count", value = "图书数量",defaultValue = "5"),
            @ApiImplicitParam(name = "bookShelf", value = "所在书架",defaultValue = "1")
    })
    @PostMapping("/save")
    public Result<Integer> save(Book book){
        int row = bookService.save(book);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"添加图书信息成功！",row);
        }
        return new Result(ResultCode.FAIL,"添加图书信息失败！");
    }

    @ApiOperation("修改书籍信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "书ID",defaultValue = "1"),
            @ApiImplicitParam(name = "bookName", value = "书名",defaultValue = "西游记"),
            @ApiImplicitParam(name = "searchId", value = "检索号",defaultValue = "ISCN-1"),
            @ApiImplicitParam(name = "cardId", value = "书的编号",defaultValue = "ISBN-1"),
            @ApiImplicitParam(name = "cid", value = "所属类别",defaultValue = "22"),
            @ApiImplicitParam(name = "author", value = "书的作者",defaultValue = "罗贯中"),
            @ApiImplicitParam(name = "bookDesc", value = "书的描述",defaultValue = "唐僧师徒三人取经..."),
            @ApiImplicitParam(name = "price", value = "书的价格",defaultValue = "22.9"),
            @ApiImplicitParam(name = "press", value = "出版社",defaultValue = "xxxx"),
            @ApiImplicitParam(name = "pressDate", value = "出版日期",defaultValue = "2000年11月5日"),
            @ApiImplicitParam(name = "count", value = "图书数量",defaultValue = "5"),
            @ApiImplicitParam(name = "bookShelf", value = "所在书架",defaultValue = "1")
    })
    @PutMapping("/update")
    public Result<Integer> update(Book book){
        int row = bookService.update(book);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"修改图书信息成功！",row);
        }
        return new Result(ResultCode.FAIL,"修改图书信息失败！");
    }

    @ApiOperation("根据ID删除书籍")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "书的ID",defaultValue = "1"),
    })
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id")Integer id){
        int row = bookService.delete(id);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"删除图书成功！",row);
        }
        return new Result(ResultCode.FAIL,"删除图书失败！");
    }

}
