package cn.billychen.community.controller;

import cn.billychen.community.dto.QuestionDTO;
import cn.billychen.community.mapper.QuestionMapper;
import cn.billychen.community.mapper.UserMapper;
import cn.billychen.community.model.Question;
import cn.billychen.community.model.User;
import cn.billychen.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    //查询数据库中是否有对应token的用户信息，如果有，则是登陆状态
                    //如果没有，则不是登陆状态
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        //设置session
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        //显示问题列表
        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions", questionList);
        return "index";
    }
}
