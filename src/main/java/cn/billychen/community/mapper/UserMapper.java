package cn.billychen.community.mapper;

import cn.billychen.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified, avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    public void insert(User user);


    @Select("select * from user where token = #{token}")
    public User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    public User findById(@Param("id") Integer creator);
}
