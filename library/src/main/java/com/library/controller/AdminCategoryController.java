package com.library.controller;

import com.library.pojo.Category;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
    @Autowired
    CategoryService categoryService;

    //    查看所有类别图书
//    http://10.10.102.163:8001/admin/book/list/pageSize/currentPage
//    参数：
//    pageSize：每页显示大小
//    currentPage：当前页
    @GetMapping("/list/{pageSize}/{currentPage}")
    public String listBook(@PathVariable("pageSize")Integer pageSize, @PathVariable("currentPage")Integer currentPage, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return "redirect:/category/select/"+pageSize+"/"+currentPage;

    }

    //            图书类别添加
//    http://10.10.102.163:8001/admin/category/add
//    参数：表单提交
    @ResponseBody
    @PostMapping("/category/add")
    public Result<Integer> save(Category category){
        System.out.println("category:"+category);
        int row = categoryService.save(category);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"添加分类成功！",row);
        }
        return new Result(ResultCode.SUCCESS,"添加分类失败！");
    }

    //            图书类别详情查看
//    http://10.10.102.163:8001/admin/category/categoryId
//    参数：
//    categoryId：查询类别的ID
    @GetMapping("/category/{categoryId}")
    public String category(@PathVariable("categoryId") Integer categoryId){
        return "forward:/category/get/"+categoryId;
    }


}
