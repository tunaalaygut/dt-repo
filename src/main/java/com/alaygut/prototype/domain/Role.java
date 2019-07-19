package com.alaygut.prototype.domain;

 
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Role extends BaseClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    private Long roleId;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "description")
    private String description;

    @ManyToMany(targetEntity = Right.class, fetch = FetchType.EAGER)
    @JoinTable(name = "Role_Right",
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "rightId"))
    private Set<Right> rights;

    public Role() {
    }

    public Role(String roleName, String description, Set<Right> rights) {
        this.roleName = roleName;
        this.description = description;
        this.rights = rights;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Right> getRights() {
        return rights;
    }

    public void setRights(Set<Right> rights) {
        this.rights = rights;
    }

    public String getAllRightNamesString(){
        String result = "";

        for (Right right : this.getRights())
            result += (right.getRightName() + ", ");

        return result.replaceAll(", $", "");    //to remove the last comma from the string.
    }

    public List<String> getAllRightNames(){
        List<String> rightNames = new ArrayList<>();

        for (Right right : this.getRights())
            rightNames.add(right.getRightName());

        return rightNames;
    }
}
