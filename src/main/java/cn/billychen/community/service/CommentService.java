package cn.billychen.community.service;

import cn.billychen.community.enums.CommentTypeEnum;
import cn.billychen.community.exception.CustomizeErrorCode;
import cn.billychen.community.exception.CustomizeException;
import cn.billychen.community.mapper.CommentMapper;
import cn.billychen.community.mapper.QuestionExtMapper;
import cn.billychen.community.mapper.QuestionMapper;
import cn.billychen.community.model.Comment;
import cn.billychen.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        //父问题不存在
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        //以下是正常情况
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //该评论是对评论的回复
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                //当前回复的评论不存在
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            //该评论是对问题的回复
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //还需要增加问题的回复数
            Question updateQuestion = new Question();
            updateQuestion.setId(dbQuestion.getId());
            updateQuestion.setCommentCount(1);
            questionExtMapper.incComment(updateQuestion);
        }
    }
}
