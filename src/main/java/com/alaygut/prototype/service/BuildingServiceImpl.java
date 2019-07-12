package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Building; 
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.BuildingRepository;
import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
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

    	if (!addBuildingForm.getNewBuildingName().equals(building.getBuildingName()))
    	    building.setBuildingName(addBuildingForm.getNewBuildingName());

    	building.setBuildingAddr(addBuildingForm.getBuildingAddr());
        building.setUpdater(memberRepository.findById(addBuildingForm.getUpdaterId()).orElse(null));

    }

    @Override
    public AddBuildingForm getEditForm(Long buildingId) {
        Building building = getBuilding(buildingId);
        AddBuildingForm addBuildingForm = new AddBuildingForm();

        addBuildingForm.setRecordId(building.getBuildingId());
        addBuildingForm.setBuildingName(building.getBuildingName());
        addBuildingForm.setBuildingAddr(building.getBuildingAddr());

        return addBuildingForm;
    }

    public Building LoadBuildingName(String buildingName) {
        Building building = this.buildingRepository.findByBuildingName(buildingName);
        return building;
    }

}
