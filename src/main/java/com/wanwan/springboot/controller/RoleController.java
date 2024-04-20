package com.wanwan.springboot.controller;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.common.Constants;
import com.wanwan.springboot.common.Result;
import com.wanwan.springboot.entity.dto.MyRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.wanwan.springboot.service.IRoleService;
import com.wanwan.springboot.entity.Role;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-02-15
 */
@RestController
@RequestMapping("/api")
public class RoleController {
    @Resource
    private IRoleService roleService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 角色分页
     * @param pageNum
     * @param pageSize
     * @param name
     * @return Page
     */
    @GetMapping("/roles/page")
    public Result page(@RequestParam Integer pageNum,
                          @RequestParam Integer pageSize,
                          @RequestParam(defaultValue = "") String name

    ) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    /**
     * 获取所有角色
     * @return List<Role>
     */
    @GetMapping("/roles")
    public Result getRoles() {
        return Result.success(roleService.list());
    }

    /**
     * 根据id获取角色
     * @param id
     * @return Role
     */
    @GetMapping("/roles/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    /**
     * 新增或者更新角色
     * @param role
     * @return Boolean
     */
    @PostMapping("/roles")
    public Result saveOrUpdate(@RequestBody Role role) {
        stringRedisTemplate.delete(Constants.ROLE_KEY);
        return Result.success(roleService.saveOrUpdate(role));
    }

    /**
     * 删除角色
     * @param id
     * @return Boolean
     */
    @DeleteMapping("/roles/{id}")
    public Result deleteById(@PathVariable Integer id) {
        stringRedisTemplate.delete(Constants.ROLE_KEY);
        return Result.success(roleService.removeById(id));
    }

    /**
     * 删除多个角色
     * @param ids
     * @return Boolean
     */
    @DeleteMapping("/roles")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        stringRedisTemplate.delete(Constants.ROLE_KEY);

        return Result.success(roleService.removeByIds(ids));
    }



    /**
     * 获取角色对应的菜单
     * @param roleId
     * @return List<Integer>
     */
    @GetMapping("/roles/{roleId}/menus")
    public Result getRoleMenus(@PathVariable Integer roleId) {
        return Result.success(roleService.getRoleMenu(roleId));
    }

    /**
     * 绑定角色和菜单的关系
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     * @return Boolean
     */
    @PostMapping("/roles/{roleId}/menus")
    public Result saveRoleMenus(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }


    /**
     * 删除redis缓存
     * @return Boolean
     */
    @PostMapping("/reset")
    public Result reset() {
        return Result.success(stringRedisTemplate.delete(Constants.ROLE_KEY));
    }

}

