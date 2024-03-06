package com.wanwan.springboot.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.common.Result;
import com.wanwan.springboot.entity.Files;
import com.wanwan.springboot.mapper.CourseMapper;
import org.springframework.web.bind.annotation.*;

import com.wanwan.springboot.service.ICourseService;
import com.wanwan.springboot.entity.Course;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-03-01
 */
@RestController
@RequestMapping("/course")
public class CourseController {
        @Resource
        private ICourseService courseService;

        @Resource
        private CourseMapper courseMapper;
        // 新增或者更新
        @PostMapping
        public Result save(@RequestBody Course course) {
                courseService.saveOrUpdate(course);
                return Result.success();
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
                courseService.removeById(id);
                return Result.success();
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
                courseService.removeByIds(ids);
                return Result.success();
        }

        @GetMapping
        public Result findAll() {
                return Result.success(courseService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
                return Result.success(courseService.getById(id));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String teacher) {
                return Result.success(courseService.findPage(new Page<>(pageNum, pageSize), name,teacher));
        }
        @PostMapping("/update")
        public Result update(@RequestBody Course course) {
                return Result.success(courseMapper.updateById(course));
        }

}

