package com.alaygut.prototype.dto;

import com.alaygut.prototype.annotation.UniqueUsername;
import com.alaygut.prototype.domain.Role;
import javax.validation.constraints.*;



public class AddMemberForm extends FormBase  {
    @Size(min = 3, max = 20, message = "İsim [3-20] karakter uzunluğunda olmalıdır.")
    private String firstName;

    @Size(min = 3, max = 20, message = "Soyisim [3-20] karakter uzunluğunda olmalıdır.")
    private String lastName;

    @Size(min = 5, max = 30, message = "E-mail adresi [5-30] karakter uzunluğunda olmalıdır.")
    private String email;

    @Size(min = 3, max = 20, message = "Telefon numarasını kontrol edin.")
    private String phone;

    //@NotNull(message = "Kullanıcı bir rol'e sahip olmalıdır.")
    private Long roleId;

    @Size(min = 6, max = 20, message = "Kullanıcı adı [6-20] karakter uzunluğunda olmalıdır.")
    //@UniqueUsername(message = "Kullanıcı adı mevcut.")
    private String username;

    @Size(min = 6, max = 20, message = "Şifre [6-20] karakter uzunluğunda olmalıdır.")
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
 
}