package com.wanwan.springboot.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.common.Result;
import com.wanwan.springboot.entity.dto.MyRequestBody;
import javafx.beans.DefaultProperty;
import org.springframework.web.bind.annotation.*;

import com.wanwan.springboot.service.ICategoryService;
import com.wanwan.springboot.entity.Category;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-03-07
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Resource
    private ICategoryService categoryService;


    @GetMapping()
    public Result getAll() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(categoryService.getById(id));
    }

    @GetMapping ("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam String name
                       ) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.eq("name", name);
        }
        return Result.success(categoryService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }
    // 新增或者更新
    @PostMapping
    public Result saveOrUpdate(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        categoryService.removeById(id);
        return Result.success();
    }

    @DeleteMapping()
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        categoryService.removeByIds(ids);
        return Result.success();
    }



}

