package com.alaygut.prototype.dto;

import com.alaygut.prototype.domain.Right;

import javax.validation.constraints.Size;
import java.util.List;

public class AddRoleForm extends FormBase  {
    @Size(min = 3, max = 30, message = "Rol adı [3-30] karakter uzunluğunda olmalıdır.")
    private String roleName;

    private String description;

    private List<Long> rightIds;        //selected in the add form.

    private Iterable<Right> allRights;  //in the database.

    private Iterable<Right> roleRights; //selected role's rights. carried to the edit page.

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

    public List<Long> getRightIds() {
        return rightIds;
    }

    public void setRightIds(List<Long> rightIds) {
        this.rightIds = rightIds;
    }

    public Iterable<Right> getAllRights() {
        return allRights;
    }

    public void setAllRights(Iterable<Right> allRights) {
        this.allRights = allRights;
    }

    public Iterable<Right> getRoleRights() {
        return roleRights;
    }

    public void setRoleRights(Iterable<Right> roleRights) {
        this.roleRights = roleRights;
    }

    public boolean hasRight(Long rightId){
        for (Right r: this.getRoleRights())
            if (r.getRightId() == rightId)
                return true;
        return false;
    }
}
