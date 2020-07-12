package cn.billychen.community.controller;

import cn.billychen.community.dto.NotificationDTO;
import cn.billychen.community.enums.NotificationTypeEnum;
import cn.billychen.community.model.Notification;
import cn.billychen.community.model.User;
import cn.billychen.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String read(@PathVariable("id") Integer id,
                    HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        //读通知,设置已读
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if ((NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType())
                || (NotificationTypeEnum.REPLY_QUESTION.getType())== notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
