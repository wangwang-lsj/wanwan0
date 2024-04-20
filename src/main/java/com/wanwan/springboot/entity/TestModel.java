package com.wanwan.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author：玩玩
 * @since：2024/3/9 20:10
 * @description:
 */
@Data
@TableName("test")
public class TestModel {
    @TableId(type=IdType.ASSIGN_ID)
    private Integer id;
    private String name;
    private String age;

}
