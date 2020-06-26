package cn.billychen.community.controller;

import cn.billychen.community.mapper.UserMapper;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {

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

        return "index";
    }
}
