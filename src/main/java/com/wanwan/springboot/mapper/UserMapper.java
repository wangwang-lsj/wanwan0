package com.wanwan.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanwan.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author：玩玩
 * @Date：2024/1/15 21:39
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // @Select("SELECT * FROM sys_user")
    // List<User> findAll();
    //
    // @Insert("INSERT INTO sys_user(username,password,nickname,email,phone,address) VALUES " +
    //         "(#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    // int insert(User user);
    //
    //
    // int update(User user);
    //
    // @Delete("delete from sys_user where id=#{id}")
    // Integer deleteById(@Param("id") Integer id);
    //
    // @Select("select * from sys_user limit #{pageNum}, #{pageSize}")
    // List<User> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize")Integer pageSize);
    //
    // @Select("select count(*) from sys_user")
    // Integer selectTotal();

    // @Select("select * from sys_user where username like concat('%',#{username},'%') limit #{pageNum}, #{pageSize}")
    // List<User> selectPage(@Param("pageNum")Integer pageNum, @Param("pageSize")Integer pageSize, @Param("username")String username);
    //
    // @Select("select count(*) from sys_user where username like concat('%',#{username},'%')")
    // Integer selectTotal(@Param("username")String username);
}
