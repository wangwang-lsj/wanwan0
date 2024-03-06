package com.wanwan.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.entity.Course;
import com.wanwan.springboot.mapper.CourseMapper;
import com.wanwan.springboot.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanwan
 * @since 2024-03-01
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    private CourseMapper courseMapper;
    @Override
    public Page<Course> findPage(Page<Course> page, String name, String teacher) {
        return courseMapper.findPage(page,name,teacher);
    }
}
