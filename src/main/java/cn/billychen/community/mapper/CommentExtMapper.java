package cn.billychen.community.mapper;

import cn.billychen.community.model.Comment;
import cn.billychen.community.model.CommentExample;
import cn.billychen.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incComment(Comment comment);
}