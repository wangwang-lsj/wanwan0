package com.wanwan.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName user_article_like
 */
@TableName(value ="user_article_like")
@Data
public class UserArticleLike implements Serializable {
    private Integer userId;

    private Integer articleId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}