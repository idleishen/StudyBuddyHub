package com.afterend.mapper;

import com.afterend.entity.Post;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PostMapper {

    @Insert("INSERT INTO post(title, content, user_id, nickname) VALUES(#{title}, #{content}, #{userId}, #{nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Post post);

    @Select("SELECT * FROM post ORDER BY create_time DESC")
    List<Post> findAll();

    @Select("SELECT * FROM post WHERE id = #{id}")
    Post findById(Long id);

    @Delete("DELETE FROM post WHERE id = #{id}")
    void deleteById(Long id);
}