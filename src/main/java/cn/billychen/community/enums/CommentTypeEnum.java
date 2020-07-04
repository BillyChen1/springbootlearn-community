package cn.billychen.community.enums;

//枚举评论的类型，1表示某条评论的父亲类型是问题，2表示评论的父亲类型是一个回复（即该评论是一个二级回复）
public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    //检查一个type值是否在枚举类型中存在
    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
