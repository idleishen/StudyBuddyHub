package com.afterend.mapper;
import com.afterend.entity.User;
import org.apache.ibatis.annotations.*;
@Mapper
public interface UserMapper {

    /**
     * 插入新用户
     */
    @Insert("INSERT INTO user(account, password, nickname, role) VALUES(#{account}, #{password}, #{nickname}, 'USER')")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 根据账号查询用户
     */
    @Select("SELECT * FROM user WHERE account = #{account}")
    User findByAccount(String account);

    /**
     * 根据ID查询用户
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);
}