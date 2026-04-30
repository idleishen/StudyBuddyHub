package com.afterend.controller;
import com.afterend.common.Result;
import com.afterend.entity.User;
import com.afterend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册接口
     * POST /api/user/register
     * 请求体：{ "account": "xxx", "password": "xxx", "nickname": "xxx" }
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        try {
            userService.register(user);
            return Result.success("注册成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 登录接口
     * POST /api/user/login
     * 请求体：{ "account": "xxx", "password": "xxx" }
     * 返回：{ token, userId, nickname, role }
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        try {
            Map<String, Object> result = userService.login(user.getAccount(), user.getPassword());
            return Result.success(result);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
