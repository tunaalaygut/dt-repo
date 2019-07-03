package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Building; 
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.IDTransfer;
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

    @Override
    public Iterable<Building> getAllActiveBuildings() {
        return buildingRepository.findAllByStateEquals(RecordState.ACTIVE);
    }

    @Override
    public void deactivate(IDTransfer IDTransfer) {
        Building building = buildingRepository.findById(IDTransfer.getRecordId()).orElse(null);
        building.setState(RecordState.NONACTIVE);
        buildingRepository.save(building);
    }
}
