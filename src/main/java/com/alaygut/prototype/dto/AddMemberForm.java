package com.alaygut.prototype.dto;

import javax.validation.constraints.Email; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.alaygut.prototype.annotation.UniqueEmail;
import com.alaygut.prototype.annotation.UniqueUsername;
import com.alaygut.prototype.domain.Role;


public class AddMemberForm extends FormBase  {
    @Size(min = 3, max = 20, message = "{firstName.size.not.valid}")
    @Pattern(regexp ="[a-zA-Z]*", message = "{firstName.not.valid}")
    private String firstName;

    @Pattern(regexp ="[a-zA-Z]*", message = "{lastName.not.valid}")
    @Size(min = 3, max = 20, message = "{lastName.size.not.valid}")
    private String lastName;

    @Email(message = "{email.not.valid}")
    @Size(min = 6, max = 60, message = "{email.size.not.valid}")
    @UniqueEmail(message = "{email.not.unique}")
    private String email;

    @Email(message = "{newEmail.not.valid}")
    @Size(min = 5, max = 60, message = "{newEmail.size.not.valid}")
    private String newEmail;

    @Size(min = 6, max = 20, message = "{newUsername.size.not.valid}")
    @Pattern(regexp ="[a-zA-Z\\d]*", message = "{newUsername.not.valid}")
    private String newUsername;

    private String originalEmail;

    private String originalUsername;

    @Size(min = 3, max = 20, message = "{phone.size.not.valid}")
    @Pattern(regexp ="[0-9\\s]*", message = "{phone.not.valid}")
    private String phone;

    @NotNull(message = "{roleId.not.null}")
    private Long roleId;

    @Size(min = 6, max = 20, message = "{username.size.not.valid}")
    @UniqueUsername(message = "{username.not.unique}")
    @Pattern(regexp ="[a-zA-Z\\d]*", message = "{username.not.valid}")
    private String username;

    @Size(min =6 , max = 25, message = "{password.size.not.valid}")
    private String password;

    private Iterable<Role> allRoles;

    public AddMemberForm() {
        //default constructor
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public Iterable<Role> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(Iterable<Role> allRoles) {
        this.allRoles = allRoles;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getOriginalEmail() {
        return originalEmail;
    }

    public void setOriginalEmail(String originalEmail) {
        this.originalEmail = originalEmail;

    }
    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getOriginalUsername() {
        return originalUsername;
    }

    public void setOriginalUsername(String originalUsername) {
        this.originalUsername = originalUsername;
    }

}