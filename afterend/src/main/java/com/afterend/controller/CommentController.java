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

    // 发表评论
    @PostMapping("/create")
    public Result<String> create(@RequestBody Comment comment, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userMapper.findById(userId);
        comment.setUserId(userId);
        comment.setNickname(user.getNickname());
        commentMapper.insert(comment);
        return Result.success("评论成功");
    }

    // 删除评论（管理员）
    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error("无权限，仅管理员可删除评论");
        }
        commentMapper.deleteById(id);
        return Result.success("评论已删除");
    }
}