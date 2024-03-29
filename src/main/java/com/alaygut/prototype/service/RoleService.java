package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddRoleForm;
import com.alaygut.prototype.dto.IDTransfer;

public interface RoleService {
    void addRole(AddRoleForm form);
    Iterable<Role> getAllRoles();
    Iterable<Role> getAllActiveRoles();
    Role getRole(Long roleId);
    void deactivate(IDTransfer idTransfer);
    void edit (AddRoleForm addRoleForm);
    AddRoleForm getEditPage(Long roleId);
    Iterable<Right> getAllRights(Long roleId);
    void fixForm(AddRoleForm form);
    AddRoleForm getAddRoleForm();
    Role getRole (String roleName);
}
