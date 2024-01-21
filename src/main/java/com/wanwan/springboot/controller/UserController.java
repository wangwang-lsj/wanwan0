package com.wanwan.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.entity.User;
import com.wanwan.springboot.mapper.UserMapper;
import com.wanwan.springboot.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：玩玩
 * @Date：2024/1/15 21:34
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Api(value = "测试接口", tags = "用户管理相关的接口", description = "用户测试接口")
public class UserController {
    // @Autowired
    // private UserMapper userMapper;

    @Autowired
    private UserService userService;
    // 新增和修改
    @PostMapping
    public boolean save(@RequestBody User user){
        //新增或者更新
        return userService.saveUser(user);
    }

    // 查询所有
    @GetMapping
    public List<User> index(){
        return userService.list();
    }
    // 通过id删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }
    //通过id批量删除
    @DeleteMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeBatchByIds(ids);
    }
    //手动分页
    // @GetMapping("/page")
    // public Map<String, Object> findPage(@RequestParam Integer pageNum,
    //                                     @RequestParam Integer pageSize
    //                                     ){
    //     pageNum = (pageNum - 1) * pageSize;
    //     List<User> data = userMapper.selectPage(pageNum,pageSize);
    //     Integer total = userMapper.selectTotal();
    //     Map<String, Object> res = new HashMap<>();
    //     res.put("data", data);
    //     res.put("total" ,total);
    //     return  res;
    // }


    //mybatis-plus自动分页
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String nickname,
                                @RequestParam(defaultValue = "") String address,
                                @RequestParam(defaultValue = "") String phone,
                                @RequestParam(defaultValue = "") String email

                                ){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(nickname)){
            queryWrapper.like("nickname",nickname);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
        if(!"".equals(phone)){
            queryWrapper.like("phone",phone);
        }
        if(!"".equals(email)){
            queryWrapper.like("email",email);
        }
        IPage<User> userIPage = userService.page(page,queryWrapper);
        return  userIPage;
    }
}
