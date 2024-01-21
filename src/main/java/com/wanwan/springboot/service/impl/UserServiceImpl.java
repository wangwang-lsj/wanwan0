package com.wanwan.springboot.service.impl;

import com.wanwan.springboot.entity.User;
import com.wanwan.springboot.mapper.UserMapper;
import com.wanwan.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanwan
 * @since 2024-01-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
