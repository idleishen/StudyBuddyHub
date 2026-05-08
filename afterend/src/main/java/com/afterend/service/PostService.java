package com.afterend.service;

import com.afterend.entity.Post;
import com.afterend.entity.User;
import com.afterend.mapper.PostMapper;
import com.afterend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    public void create(Post post) {
        User user = userMapper.findById(post.getUserId());
        post.setNickname(user.getNickname());
        postMapper.insert(post);
    }

    public Map<String, Object> list(Integer page, Integer pageSize) {
        List<Post> allPosts = postMapper.findAll();
        int total = allPosts.size();

        // 手动分页
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        List<Post> pageList = allPosts.subList(Math.min(start, total), end);

        Map<String, Object> result = new HashMap<>();
        result.put("records", pageList);
        result.put("total", total);
        result.put("current", page);
        result.put("pageSize", pageSize);
        return result;
    }

    public Post detail(Long id) {
        return postMapper.findById(id);
    }

    public void delete(Long id) {
        postMapper.deleteById(id);
    }
}