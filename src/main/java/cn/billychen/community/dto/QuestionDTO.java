package cn.billychen.community.dto;

import cn.billychen.community.model.User;
import lombok.Data;

//在Question实体类的基础上新增了User对象，用于直接显示用户头像
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private long gmtCreate;
    private long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}
