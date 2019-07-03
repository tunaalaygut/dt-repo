package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.dto.AddBuildingForm;

public interface BuildingService {
    void addBuilding(AddBuildingForm addBuildingForm);
    Iterable<Building> getAllBuildings();
}
