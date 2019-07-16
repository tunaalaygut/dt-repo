package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Building; 
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.BuildingRepository;
import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BuildingServiceImpl implements BuildingService {

    private BuildingRepository buildingRepository;
    private MemberService memberService;

    public BuildingServiceImpl(BuildingRepository buildingRepository, MemberService memberService) {
        this.buildingRepository = buildingRepository;
        this.memberService = memberService;
    }

    @Override
    @Transactional(readOnly = false)
    public void addBuilding(AddBuildingForm form) {
        Building building = new Building(
                form.getBuildingName(),
                form.getBuildingAddr()
        );
        building.setCreator(memberService.getMember(form.getCreatorId()));
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
    @Transactional(readOnly = false)
    public void deactivate(IDTransfer idTransfer) {
        Building building = buildingRepository.findById(idTransfer.getRecordId()).orElse(null);
        building.setState(RecordState.NONACTIVE);
        buildingRepository.save(building);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void edit(AddBuildingForm addBuildingForm) {
    	Building building = buildingRepository.findById(addBuildingForm.getRecordId()).orElse(null);

    	if (!addBuildingForm.getNewBuildingName().equals(building.getBuildingName()))
    	    building.setBuildingName(addBuildingForm.getNewBuildingName());

    	building.setBuildingAddr(addBuildingForm.getBuildingAddr());
        building.setUpdater(memberService.getMember(addBuildingForm.getUpdaterId()));

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
