package com.library.controller;

import com.github.pagehelper.PageInfo;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.pojo.SummaryResult;
import com.library.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

//http://10.10.102.163:8001/admin/summary/pageSize/currentPage?status=0 &date=2020-11-1
//统计
@Validated
@RestController
@RequestMapping("/admin/")
@CrossOrigin
public class AdminSummaryController {
	public static final String regex = "((((19|20)\\d{2})-(0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})-(0[469]|11)-(0[1-9]|[12]\\d|30))|(((19|20)\\d{2})-02-(0[1-9]|1\\d|2[0-8])))";
	@Autowired(required = false)
	SummaryService summaryService;

	// http://10.10.102.163:8001/admin/summary/pageSize/currentPage?status=0&pdate=2020-01-06
	// status
	// 0:按日查询
	// 1:按月查询
	// 2：按年查询
	@GetMapping("/summary/{pageSize}/{currentPage}")
	public Result<PageInfo<SummaryResult>> summary(@PathVariable("pageSize") Integer pageSize,
			@PathVariable("currentPage") Integer currentPage, int status,
			@Pattern(regexp = AdminSummaryController.regex) String pdate) throws Exception {
		System.err.println("校验成功");
		// Date date = new SimpleDateFormat("yyyy-MM-dd").parse(pdate);
		LocalDate date = LocalDate.parse(pdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		int day = 0, month = 0, year = 0;
		LocalDate startDate = null;
		LocalDate endDate = null;
		int lastDay = 1;

		LocalDate myDate = LocalDate.now();
		switch (status) {
		case 0:

			startDate = LocalDate.of(myDate.getYear(), myDate.getMonth().getValue(), date.getDayOfMonth());
			endDate = LocalDate.of(myDate.getYear(), myDate.getMonth().getValue(), date.getDayOfMonth());
			break;
		case 1:

			startDate = LocalDate.of(myDate.getYear(), date.getMonth().getValue(), 1);
			// 计算当月的最后一天
			lastDay = startDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
			endDate = LocalDate.of(myDate.getYear(), date.getMonth().getValue(), lastDay);
			break;
		case 2:
			startDate = LocalDate.of(date.getYear(), 1, 1);
			endDate = LocalDate.of(date.getYear(), 12, 31);

			break;
		}
		PageInfo<SummaryResult> result = summaryService.summary(
				startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), status, pageSize, currentPage);
		if (result.getList().size() > 0) {
			return new Result<PageInfo<SummaryResult>>(ResultCode.SUCCESS, "统计成功", result);
		}
		return new Result<PageInfo<SummaryResult>>(ResultCode.FAIL, "统计失败");
	}

	@ExceptionHandler(Exception.class)
	public Object handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入自定义异常处理方法");
		return response;

	}

}
