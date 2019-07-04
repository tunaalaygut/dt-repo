package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Building; 
import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.IDTransfer;

public interface BuildingService {
    void addBuilding(AddBuildingForm addBuildingForm);
    Iterable<Building> getAllBuildings();
    Iterable<Building> getAllActiveBuildings();
    void deactivate(IDTransfer idTransfer);
}
