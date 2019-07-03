package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Role; 
import com.alaygut.prototype.dto.AddRoleForm;
import com.alaygut.prototype.dto.IDTransfer;

public interface RoleService {
    void addRole(AddRoleForm form);
    Iterable<Role> getAllRoles();
    Iterable<Role> getAllActiveRoles();
    void deactivate(IDTransfer IDTransfer);
}
