package com.library.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.library.pojo.Result;
import com.library.pojo.User;
import com.library.service.UserService;
import com.library.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @PostMapping("/save")
    public Result<Integer> save(User user){
        user.setRegisterDate(new Date());
        int row = userService.save(user);
        if(row > 0){
            return new Result(200,"注册成功！",row);
        }
        return new Result(444,"注册失败！");
    }

    @PostMapping("/login")
    public Result<String> login(User user){
        User u = userService.get(user);
        if(u != null){
            String token = jwtUtils.generateToken(user);

            redisTemplate.opsForValue().set("token",u, 60,TimeUnit.SECONDS);
            Object token1 = redisTemplate.opsForValue().get("token");
            System.out.println(token1);
            return new Result(200,"登录成功！",token);
        }else{
            return new Result(444,"用户名或密码有误！");
        }

    }

    @GetMapping("/get")
    public Result<User> get(User user){

        User u = userService.get(user);
        if(u != null){
            return new Result(200,"查询用户成功！",u);
        }
        return new Result(444,"无此用户！");
    }

    @GetMapping("/select/{pageSize}/{currentCount}")
    public Result<PageInfo<User>> select(@PathVariable("pageSize")@RequestParam(defaultValue = "5") Integer pageSize
        ,@PathVariable("currentCount")@RequestParam(defaultValue = "1")Integer currentPage){
        PageInfo<User> pageInfo = userService.selectAll(pageSize,currentPage);
        if(pageInfo.getList().size() > 0){
            return new Result(200,"查询所有用户成功！",pageInfo);
        }
        return new Result(444,"查询所有用户失败！");
    }

    @PutMapping("/update")
    public Result<Integer> update(User user){
        int row = userService.update(user);
        if(row > 0){
            return new Result(200,"用户信息修改成功！",row);
        }else{
            return new Result(444,"用户信息修改失败！");
        }
    }



    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Integer id){
        int row = userService.delete(id);
        if(row > 0){
            return new Result(200,"用户删除成功！",row);
        }else{
            return new Result(444,"用户删除失败！");
        }
    }

}
