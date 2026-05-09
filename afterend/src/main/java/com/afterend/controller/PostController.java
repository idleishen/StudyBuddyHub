package com.afterend.controller;

import com.afterend.common.Result;
import com.afterend.entity.Comment;
import com.afterend.entity.Post;
import com.afterend.mapper.CommentMapper;
import com.afterend.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping("/create")
    public Result<String> create(@RequestBody Post post, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        post.setUserId(userId);
        postService.create(post);
        return Result.success("发布成功");
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(postService.list(page, pageSize));
    }

    @GetMapping("/detail/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id) {
        Post post = postService.detail(id);
        List<Comment> comments = commentMapper.findByPostId(id);
        Map<String, Object> result = new HashMap<>();
        result.put("post", post);
        result.put("comments", comments);
        return Result.success(result);
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error("无权限");
        }
        postService.delete(id);
        return Result.success("删除成功");
    }
}