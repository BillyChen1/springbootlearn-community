package cn.billychen.community.controller;

import cn.billychen.community.dto.CommentDTO;
import cn.billychen.community.dto.QuestionDTO;
import cn.billychen.community.enums.CommentTypeEnum;
import cn.billychen.community.model.Question;
import cn.billychen.community.service.CommentService;
import cn.billychen.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        //根据id拿到问题
        QuestionDTO questionDTO = questionService.getById(id);
        //拿到问题浏览问题详情前需要增加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        //获取回复列表
        List<CommentDTO> comments = commentService.listByQuestionOrCommentId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("comments", comments);
        //获取与该问题的相关问题
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        model.addAttribute("relatedQuestions", relatedQuestions);

        return "question";
    }
}
