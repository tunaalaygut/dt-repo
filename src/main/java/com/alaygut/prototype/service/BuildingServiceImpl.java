package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.repository.BuildingRepository;
import org.springframework.stereotype.Service;

@Service
public class BuildingServiceImpl implements BuildingService {

    private BuildingRepository buildingRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public void addBuilding(AddBuildingForm form) {
        Building building = new Building(
                form.getBuildingName(),
                form.getBuildingAddr()
        );
        buildingRepository.save(building);
    }

    @Override
    public Iterable<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }
}
