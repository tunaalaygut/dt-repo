package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddRoleForm;

public interface RoleService {
    void addRole(AddRoleForm form);
    Iterable<Role> getAllRoles();
}
