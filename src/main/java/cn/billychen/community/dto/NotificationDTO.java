package cn.billychen.community.dto;

import cn.billychen.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private Integer notifier;
    private String notifierName;
    private String outerTitle;
    private Integer outerId;
    private String typeName;    //类型，回复问题还是回复评论
    private Integer type;
}
