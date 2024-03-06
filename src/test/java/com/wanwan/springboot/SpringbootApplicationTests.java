package com.wanwan.springboot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanwan.springboot.entity.Menu;
import com.wanwan.springboot.mapper.MenuMapper;
import com.wanwan.springboot.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootApplicationTests {
    @Value("${spring.datasource.url}")
    private String url;
    @Test
    void contextLoads() {
        System.out.println(url);
    }
    @Autowired
    private MenuMapper menuMapper;
    @Test
    void contextLoads1() {
        System.out.println(menuMapper.selectAllBySortNum());
    }
    @Autowired
    private MenuServiceImpl menuService;
    private QueryWrapper<Menu> queryWrapper;
    @Test
    void contextLoads2() {
        queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_num");
        System.out.println(menuService.list(queryWrapper));
    }

}
