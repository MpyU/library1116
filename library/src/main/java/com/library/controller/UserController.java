package com.library.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.library.pojo.Result;
import com.library.pojo.ResultCode;
import com.library.pojo.User;
import com.library.service.UserService;
import com.library.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private JwtUtils jwtUtils;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "李四",required=true),
            @ApiImplicitParam(name = "password", value = "用户密码", defaultValue = "123", required = true),
            @ApiImplicitParam(name = "telephone", value = "联系方式", defaultValue = "12312312312"),
            @ApiImplicitParam(name = "email", value = "邮箱地址", defaultValue = "1156642797@qq.com"),
            @ApiImplicitParam(name = "headImage", value = "头像地址")
    })
    @ApiOperation("添加用户")
    @PostMapping("/save")
    public Result<Integer> save(User user){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setRegisterDate(simpleDateFormat.format(new Date()));
        System.out.println(user);
        int row = userService.save(user);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"注册成功！",row);
        }
        return new Result(ResultCode.FAIL,"注册失败！");
    }

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userStr", value = "传递一个user对象的json串",required=true)
    })
    @PostMapping("/login")
    public Result<String> login(@RequestBody String userStr){
        System.out.println(userStr);
        Gson gson = new Gson();
        User user = gson.fromJson(userStr, User.class);
        System.out.println(user);
        User u = userService.getByPWD(user.getUsername(),user.getPassword());
        System.out.println("------"+u);
        if(u != null){
            String token = jwtUtils.generateToken(u);
            redisTemplate.opsForValue().set("token",token, 3600,TimeUnit.SECONDS);
            Object token1 = redisTemplate.opsForValue().get("token");
            System.out.println(token1);
            return new Result(ResultCode.SUCCESS,"登录成功！",token);
        }else{
            return new Result(ResultCode.FAIL,"用户名或密码有误！");
        }
    }

    @ApiOperation("根据ID查询单个用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID",required=true)
    })
    @GetMapping("/get/{id}")
    public Result<User> get(@PathVariable("id")Integer id){
        User u = userService.get(new User(id));
        if(u != null){
            return new Result(ResultCode.SUCCESS,"查询用户成功！",u);
        }
        return new Result(ResultCode.FAIL,"无此用户！");
    }

    @ApiOperation("分页查询所有用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小",required=true),
            @ApiImplicitParam(name = "currentPage", value = "当前页",required=true)
    })
    @GetMapping("/select/{pageSize}/{currentCount}")
    public Result<PageInfo<User>> select(@PathVariable("pageSize")Integer pageSize
        ,@PathVariable("currentCount")Integer currentPage){
        if(pageSize == null){
            pageSize = 5;
        }
        if(currentPage == null){
            currentPage = 1;
        }
        PageInfo<User> pageInfo = userService.selectAll(pageSize,currentPage);
        if(pageInfo.getList().size() > 0){
            return new Result(ResultCode.SUCCESS,"查询所有用户成功！",pageInfo);
        }
        return new Result(ResultCode.FAIL,"查询所有用户失败！");
    }

    @ApiOperation("根据ID更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "李四",required=true),
            @ApiImplicitParam(name = "telephone", value = "联系方式", defaultValue = "12312312312"),
            @ApiImplicitParam(name = "email", value = "邮箱地址", defaultValue = "1156642797@qq.com"),
            @ApiImplicitParam(name = "headImage", value = "头像地址")
    })
    @PutMapping("/update")
    public Result<Integer> update(User user){
        int row = userService.update(user);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"用户信息修改成功！",row);
        }else{
            return new Result(ResultCode.FAIL,"用户信息修改失败！");
        }
    }


    @ApiOperation("根据ID删除用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID",required=true)
    })
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Integer id){
        int row = userService.delete(id);
        if(row > 0){
            return new Result(ResultCode.SUCCESS,"用户删除成功！",row);
        }else{
            return new Result(ResultCode.FAIL,"用户删除失败！");
        }
    }

}
