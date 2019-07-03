package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.dto.AddRightForm;

public interface RightService {
    void addRight(AddRightForm form);
    Iterable<Right> getAllRights();
    Iterable<Right> getAllActiveRights();
}
