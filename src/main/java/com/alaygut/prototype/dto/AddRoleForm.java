package com.alaygut.prototype.dto;

import com.alaygut.prototype.domain.Right;

import javax.validation.constraints.Size;
import java.util.List;

public class AddRoleForm extends FormBase  {
    @Size(min = 3, max = 30, message = "Rol adı [3-30] karakter uzunluğunda olmalıdır.")
    private String roleName;

    private String description;

    private List<Long> rightIds;

    private Iterable<Right> allRights;

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
}
