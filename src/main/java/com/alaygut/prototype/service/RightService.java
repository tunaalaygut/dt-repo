package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddRightForm;
import com.alaygut.prototype.dto.IDTransfer;

import java.util.List;

public interface RightService {
    void addRight(AddRightForm form);
    Iterable<Right> getAllRights();
    Iterable<Right> getAllActiveRights();
    Right getRight(Long rightId);
    void deactivate(IDTransfer idTransfer);
    void edit(AddRightForm addRightForm);
    AddRightForm getEditForm(Long rightId);
    Iterable <Right> getRights(List <Long> rightIds);
    Iterable<Right> getAllByRoles(Role role);
}
