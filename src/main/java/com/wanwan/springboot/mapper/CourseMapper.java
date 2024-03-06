package com.wanwan.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanwan
 * @since 2024-03-01
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    Page<Course> findPage(Page<Course> page,@Param("name") String name,@Param("teacher") String teacher);
}
