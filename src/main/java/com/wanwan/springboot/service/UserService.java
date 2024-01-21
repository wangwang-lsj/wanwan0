package com.wanwan.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanwan.springboot.entity.User;
import com.wanwan.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：玩玩
 * @Date：2024/1/15 22:56
 * @Description:
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    // @Autowired
    // private UserMapper userMapper;
    //
    // public int save(User user){
    //     if(user.getId() == null){
    //         return userMapper.insert(user);
    //     }else {
    //         return userMapper.update(user);
    //     }
    //
    // }
    public boolean saveUser(User user){
        return saveOrUpdate(user);
    }


}
