package com.wanwan.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.common.Constants;
import com.wanwan.springboot.common.Result;
import com.wanwan.springboot.entity.Dict;
import com.wanwan.springboot.mapper.DictMapper;
import com.wanwan.springboot.mapper.MenuMapper;
import com.wanwan.springboot.service.IUserService;
import com.wanwan.springboot.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import com.wanwan.springboot.service.IMenuService;
import com.wanwan.springboot.entity.Menu;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-02-15
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
        @Resource
        private IMenuService menuService;
        @Resource
        private MenuMapper menuMapper;
        @Resource
        private DictMapper dictMapper;
        @Resource
        private UserServiceImpl userService;

        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Menu menu) {
                menuService.saveOrUpdate(menu);
                return Result.success();
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
                menuService.removeById(id);
                return Result.success();
        }

        @DeleteMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
                menuService.removeByIds(ids);
                return Result.success();
        }

        @GetMapping
        public Result findAll(@RequestParam(defaultValue = "") String name) {
                return Result.success(menuService.findMenus(name));
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
                return Result.success(menuService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String name
                               ) {
                QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
                IPage<Menu> page = new Page<>(pageNum, pageSize);
                if(!"".equals(name)){
                        queryWrapper.like("name",name);
                }
                queryWrapper.orderByAsc("sort_num");
                return Result.success(menuService.page(page,queryWrapper));
        }
        @GetMapping("/icons")
        public Result getIcons(){
                QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
                queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
                return Result.success(dictMapper.selectList(queryWrapper));
        }

}

