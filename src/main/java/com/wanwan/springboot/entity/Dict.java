package com.wanwan.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author：玩玩
 * @since：2024/2/16 13:52
 * @description:
 */
@TableName("dict")
@Data
public class Dict {
    private String name;
    private String value;
    private String type;
}
