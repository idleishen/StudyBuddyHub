package com.afterend.mapper;

import com.afterend.entity.Comment;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comment(content, user_id, nickname, post_id) VALUES(#{content}, #{userId}, #{nickname}, #{postId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Comment comment);

    @Select("SELECT * FROM comment WHERE post_id = #{postId} ORDER BY create_time ASC")
    List<Comment> findByPostId(Long postId);
}