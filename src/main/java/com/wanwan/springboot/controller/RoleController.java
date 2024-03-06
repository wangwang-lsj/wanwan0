package com.wanwan.springboot.controller;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.common.Constants;
import com.wanwan.springboot.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.wanwan.springboot.service.IRoleService;
import com.wanwan.springboot.entity.Role;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-02-15
 */
@RestController
@RequestMapping("/role")
public class RoleController {
        @Resource
        private IRoleService roleService;
        @Autowired
        private StringRedisTemplate stringRedisTemplate;
        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Role role) {
                stringRedisTemplate.delete(Constants.ROLE_KEY);
                roleService.saveOrUpdate(role);
                return Result.success();
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
                stringRedisTemplate.delete(Constants.ROLE_KEY);
                roleService.removeById(id);
                return Result.success();
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
                stringRedisTemplate.delete(Constants.ROLE_KEY);
                roleService.removeByIds(ids);
                return Result.success();
        }

        @GetMapping
        public Result findAll(@RequestParam(defaultValue = "") String name) {
                QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
                if(!"".equals(name)){
                        queryWrapper.like("name",name);
                }
                List<Role> list;
                String jsonStr = stringRedisTemplate.opsForValue().get(Constants.ROLE_KEY);
                if(StrUtil.isBlank(jsonStr)){
                        list = roleService.list(queryWrapper);
                        stringRedisTemplate.opsForValue().set(Constants.ROLE_KEY, JSONUtil.toJsonStr(list));
                }else {
                        list = JSONUtil.toBean(jsonStr, new TypeReference<List<Role>>() {
                        },true);
                }
                return Result.success(list);
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
                return Result.success(roleService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String name) {
                QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
                if(!"".equals(name)){
                        queryWrapper.like("name",name);
                }
                // queryWrapper.orderByDesc("id");
                return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }

        /**
         * 绑定角色和菜单的关系
         * @param roleId 角色id
         * @param menuIds 菜单id数组
         * @return
         */
        @PostMapping("/roleMenu/{roleId}")
        public Result save(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds) {
                roleService.setRoleMenu(roleId, menuIds);
                return Result.success();
        }
        @GetMapping("/roleMenu/{roleId}")
        public Result getRoleMenu(@PathVariable Integer roleId){
                return Result.success(roleService.getRoleMenu(roleId));
        }
        @PostMapping("/reset")
        public Result reset(){
                stringRedisTemplate.delete(Constants.ROLE_KEY);
                return Result.success();
        }

}

