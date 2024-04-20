package com.wanwan.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanwan.springboot.common.enums.ResultCodeEnum;
import com.wanwan.springboot.entity.Comment;
import com.wanwan.springboot.entity.dto.CommentDTO;
import com.wanwan.springboot.exception.ServiceException;
import com.wanwan.springboot.service.ICommentService;
import com.wanwan.springboot.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 玩玩
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2024-03-14 18:02:20
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public CommentDTO addComment(Comment comment) {

        if (comment.getUserId() == null) {
            throw new ServiceException(ResultCodeEnum.USER_NO_LOGIN);
        }
        commentMapper.insert(comment);
        // 666返回插入id到comment中了
        return commentMapper.getSingleComment(comment.getId());
    }

    @Override
    public IPage<CommentDTO> getCommentListByPage(Integer pageNum, Integer pageSize,Integer articleId,Integer currentUserId) {

        return commentMapper.queryPage(new Page<>(pageNum, pageSize),articleId,currentUserId);
    }

    @Override
    public List<CommentDTO> getReplyListByPage(Integer commentId, Integer startIndex, Integer count, Integer currentUserId) {
        return commentMapper.queryChildrenByPage(commentId,startIndex,count,currentUserId);
    }

    @Transactional
    @Override
    public void like(Integer commentId, Integer userId) {
        commentMapper.commentLike(commentId);
        commentMapper.like(commentId,userId);


    }

    @Transactional
    @Override
    public void disLike(Integer commentId, Integer userId) {
        commentMapper.commentDisLike(commentId);
        commentMapper.disLike(commentId,userId);
    }
}




