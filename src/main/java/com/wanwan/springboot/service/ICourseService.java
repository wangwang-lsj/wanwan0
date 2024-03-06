package com.wanwan.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanwan
 * @since 2024-03-01
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> page, String name,String teacher);
}
