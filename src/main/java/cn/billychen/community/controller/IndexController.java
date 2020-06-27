package cn.billychen.community.controller;

import cn.billychen.community.dto.PaginationDTO;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    //page页码 size一页的问题记录数
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") int page,
                        @RequestParam(name = "size",defaultValue = "5") int size) {

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

        //显示该页信息
        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
