package com.afterend.controller;

import com.afterend.common.Result;
import com.afterend.entity.Comment;
import com.afterend.entity.User;
import com.afterend.mapper.CommentMapper;
import com.afterend.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/create")
    public Result<String> create(@RequestBody Comment comment, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userMapper.findById(userId);
        comment.setUserId(userId);
        comment.setNickname(user.getNickname());
        commentMapper.insert(comment);
        return Result.success("评论成功");
    }
}