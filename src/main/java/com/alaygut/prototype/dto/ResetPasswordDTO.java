package com.alaygut.prototype.dto;

import javax.validation.constraints.Size;

public class ResetPasswordDTO {

    private String email;
    
    @Size(min=3, max=10, message = "{resetPassword.size.not.valid}" )
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}