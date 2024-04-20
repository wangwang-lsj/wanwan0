package com.wanwan.springboot.service.impl;

import com.wanwan.springboot.entity.Category;
import com.wanwan.springboot.mapper.CategoryMapper;
import com.wanwan.springboot.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanwan
 * @since 2024-03-07
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
