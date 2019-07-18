package com.alaygut.prototype.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alaygut.prototype.annotation.UniqueEmail; 
import com.alaygut.prototype.domain.Role;




public class AddMemberForm extends FormBase  {
    @Size(min = 3, max = 20, message = "İsim [3-20] karakter uzunluğunda olmalıdır.")
    private String firstName;

    @Size(min = 3, max = 20, message = "Soyisim [3-20] karakter uzunluğunda olmalıdır.")
    private String lastName;


    @Email(message = "Girdiğiniz email adresi doğru değil.")
    @Size(min = 5, max = 30, message = "E-mail adresi [5-30] karakter uzunluğunda olmalıdır.")
    @UniqueEmail(message = "Email mevcut.")
    private String email;

    @Email(message = "Girdiğiniz email adresi doğru değil.")
    @Size(min = 5, max = 30, message = "E-mail adresi [5-30] karakter uzunluğunda olmalıdır.")
    private String newEmail;

    @Size(min = 6, max = 20, message = "Kullanıcı adı [6-20] karakter uzunluğunda olmalıdır.")
    private String newUsername;

    private String originalEmail;

    private String originalUsername;

    @Size(min = 3, max = 20, message = "Telefon numarasını kontrol edin.")
    private String phone;

    @NotNull(message = "Kullanıcı bir rol'e sahip olmalıdır.")
    private Long roleId;

    @Size(min = 6, max = 20, message = "Kullanıcı adı [6-20] karakter uzunluğunda olmalıdır.")
    //@UniqueUsername(message = "Kullanıcı adı mevcut.")
    private String username;

    @Size(min = 3, max = 20, message = "Şifre [6-20] karakter uzunluğunda olmalıdır.")
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