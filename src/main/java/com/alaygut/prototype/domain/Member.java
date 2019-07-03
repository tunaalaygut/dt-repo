package com.alaygut.prototype.domain;

import org.springframework.context.annotation.Bean;

import javax.persistence.*;

@Entity
public class Member extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberId", nullable = false, updatable = false)
    private Long memberId;

    @Column(name="firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(optional = true)
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToOne
    @JoinColumn(name = "loginId")
    private Login login;

    public Member() {
        //Default constructor
    }

    public Member(String firstName, String lastName, String email, String phone, Role role, Login login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.login = login;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getRoleName(){
        return role.getRoleName();
    }


}
