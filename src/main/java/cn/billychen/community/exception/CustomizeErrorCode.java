package cn.billychen.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找到问题不存在了，要不换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登陆，请登录后重试"),
    SYSTEM_ERROR(2004, "服务器错误，请稍后重试"),
    TYPE_PARAM_WRONG(2005, "评论的类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论已经不存在了");

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
