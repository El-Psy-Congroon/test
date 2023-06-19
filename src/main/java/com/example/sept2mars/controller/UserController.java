package com.example.sept2mars.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sept2mars.common.Result;
import com.example.sept2mars.entity.User;
import com.example.sept2mars.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping("/login")
    public Result<?> loginforWeb(@RequestParam String name,@RequestParam String password) {
        User user=userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName,name).eq(User::getPassword,password));
        if(user==null){
            return Result.error("-1","不存在该用户");
        }
        return Result.success(user);
    }

    @PostMapping
    public Result<?> save(@RequestBody User user){
        if(user.getPassword()==null){
            user.setPassword("123");
            user.setLevel(2);
        }
        userMapper.insert(user);
        return Result.success();
    }

    @ApiOperation("更新用户")
    @PutMapping
    public Result<?> update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id){
        userMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wrapper= Wrappers.<User>lambdaQuery().like(User::getName,search);
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getName,search);
        }
        Page<User> userPage= (Page<User>) userMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(userPage);
    }
}
