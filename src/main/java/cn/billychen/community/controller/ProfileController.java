package cn.billychen.community.controller;

import cn.billychen.community.dto.PaginationDTO;
import cn.billychen.community.model.User;
import cn.billychen.community.service.NotificationService;
import cn.billychen.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size) {
        //获取User
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            System.out.println("未登录");
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            //找出我的所有问题
            PaginationDTO pagination = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", pagination);

        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            //获取通知
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            //Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("pagination", paginationDTO);
            //model.addAttribute("unreadCount", unreadCount);
        }

        return "profile";
    }
}
