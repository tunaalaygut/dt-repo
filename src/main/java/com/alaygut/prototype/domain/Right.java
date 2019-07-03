package com.alaygut.prototype.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rights")
public class Right extends BaseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rightId", nullable = false)
    private Long rightId;

    @Column(name = "rightName")
    private String rightName;

    @Column(name = "description")
    private String description;

    @ManyToMany(targetEntity = Role.class)
    private Set<Role> roles;

    public Right() {
        //default constructor
    }

    public Right(String rightName, String description) {
        this.rightName = rightName;
        this.description = description;
    }

    public Long getRightId() {
        return rightId;
    }

    public void setRightId(Long rightId) {
        this.rightId = rightId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

