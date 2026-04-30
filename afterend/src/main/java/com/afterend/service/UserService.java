package com.afterend.service;

import com.afterend.entity.User;
import com.afterend.mapper.UserMapper;
import com.afterend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册
     */
    public void register(User user) {
        // 检查账号是否已存在
        User existUser = userMapper.findByAccount(user.getAccount());
        if (existUser != null) {
            throw new RuntimeException("该账号已被注册");
        }
        // 检查密码长度
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("密码长度不能少于6位");
        }
        // 保存用户
        userMapper.insert(user);
    }

    /**
     * 登录
     */
    public Map<String, Object> login(String account, String password) {
        // 查询用户
        User user = userMapper.findByAccount(account);
        if (user == null) {
            throw new RuntimeException("账号不存在");
        }
        // 验证密码
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        // 生成token
        String token = JwtUtil.createToken(user.getId(), user.getRole());
        // 返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("userId", user.getId());
        resultMap.put("nickname", user.getNickname());
        resultMap.put("role", user.getRole());
        return resultMap;
    }
}
