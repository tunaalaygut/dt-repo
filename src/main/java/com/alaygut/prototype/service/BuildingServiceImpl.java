package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Building; 
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.BuildingRepository;
import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class BuildingServiceImpl implements BuildingService {

    private BuildingRepository buildingRepository;
    private MemberRepository memberRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository, MemberRepository memberRepository) {
        this.buildingRepository = buildingRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void addBuilding(AddBuildingForm form) {
        Building building = new Building(
                form.getBuildingName(),
                form.getBuildingAddr()
        );
        building.setCreator(memberRepository.findById(form.getCreatorId()).orElse(null));
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
    public Building getBuilding(Long buildingId) {
    	return buildingRepository.findById(buildingId).orElse(null);
    }

    @Override
    public void deactivate(IDTransfer idTransfer) {
        Building building = buildingRepository.findById(idTransfer.getRecordId()).orElse(null);
        building.setState(RecordState.NONACTIVE);
        buildingRepository.save(building);
    }
    
    @Override
    public void edit(AddBuildingForm addBuildingForm) {
    	Building building = buildingRepository.findById(addBuildingForm.getRecordId()).orElse(null);
    	building.setBuildingName(addBuildingForm.getBuildingName());
    	building.setBuildingAddr(addBuildingForm.getBuildingAddr());
    	
    	buildingRepository.save(building);
    }
}
