package com.wanwan.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanwan.springboot.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author：玩玩
 * @since：2024/2/16 20:19
 * @description:
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Delete("delete from sys_role_menu where role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") Integer roleId);

    @Select("select menu from sys_role_menu where role_id = #{roleId}")
    List<Integer> selectByRoleId(@Param("roleId")Integer roleId);
}
