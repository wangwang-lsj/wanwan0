package com.wanwan.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer parentId;

    private Integer replyUserId;

    private Integer replyCommentId;

    private Integer userId;

    private String commentContent;

    private Date createTime;

    private Integer likeNum;

    private Integer articleId;

    private static final long serialVersionUID = 1L;
}