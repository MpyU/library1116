package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.library.climb.ClimbData;
import com.library.pojo.Category;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/get/{id}")
	public Result<Category> get(@PathVariable("id") Integer id) {

		Category category = categoryService.get(new Category(id));
		if (category != null) {
			return new Result(ResultCode.SUCCESS, "查询分类信息成功！", category);
		}
		return new Result(ResultCode.FAIL, "查询分类信息失败！");
	}

	@GetMapping("/select/{pageSize}/{currentPage}")
	public Result<Category> select(@PathVariable(value = "pageSize") Integer pageSize,
			@PathVariable("currentPage") Integer currentPage) {
		if (pageSize == null) {
			pageSize = 5;
		}
		if (currentPage == null) {
			currentPage = 1;
		}
		PageInfo<Category> pageInfo = categoryService.selectAll(currentPage, pageSize);
		if (pageInfo.getList().size() > 0) {
			return new Result(ResultCode.SUCCESS, "查询所有分类信息成功！", pageInfo);
		}
		return new Result(ResultCode.FAIL, "查询所有分类信息失败！");
	}

	@PutMapping("/update")
	public Result<Integer> update(Category category) {
		int row = categoryService.update(category);
		if (row > 0) {
			return new Result(ResultCode.SUCCESS, "修改分类信息成功！", row);
		}
		return new Result(ResultCode.SUCCESS, "修改分类信息失败！");
	}

	@DeleteMapping("/delete/{id}")
	public Result<Integer> delete(@PathVariable("id") Integer id) {
		int row = categoryService.delete(id);
		if (row > 0) {
			return new Result(ResultCode.SUCCESS, "删除分类成功！", row);
		}
		return new Result(ResultCode.SUCCESS, "删除分类失败！");
	}

	// @GetMapping("/add")
	public String lists() {

		List<Category> list = ClimbData.categories();
		categoryService.add(list);

		return "success";
	}

}
