package com.afterend.entity;
import java.time.LocalDateTime;
public class User {
    private Long id;
    private String account;
    private String password;
    private String nickname;
    private String role;
    private LocalDateTime createTime;
    // 构造方法
    public User() {}
    // Getter和Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    @Override
    public String toString() {
        return "User{id=" + id + ", account='" + account + "', nickname='" + nickname + "', role='" + role + "'}";
    }
}