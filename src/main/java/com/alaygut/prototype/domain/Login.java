package com.alaygut.prototype.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Login extends BaseClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="loginId", nullable = false, updatable = false)
    private Long loginId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    public Login() {
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
