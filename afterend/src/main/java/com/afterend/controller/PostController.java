package com.afterend.controller;

import com.afterend.common.Result;
import com.afterend.entity.Post;
import com.afterend.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    // 发布帖子
    @PostMapping("/create")
    public Result<String> create(@RequestBody Post post, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        post.setUserId(userId);
        postService.create(post);
        return Result.success("发布成功");
    }

    // 帖子列表
    @GetMapping("/list")
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(postService.list(page, pageSize));
    }

    // 删除帖子（管理员）
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