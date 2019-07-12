package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.RoomFeature;
import com.alaygut.prototype.dto.*;
import com.alaygut.prototype.repository.MemberRepository;
import com.alaygut.prototype.repository.RoomFeatureRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomFeatureServiceImpl implements RoomFeatureService {
    private RoomFeatureRepository roomFeatureRepository;
    private MemberRepository memberRepository;

    public RoomFeatureServiceImpl(RoomFeatureRepository roomFeatureRepository, MemberRepository memberRepository) {
        this.roomFeatureRepository = roomFeatureRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void addRoomFeature(AddRoomFeatureForm form) {
        RoomFeature roomFeature =
                new RoomFeature(form.getFeatureName(), form.getDescription());
        roomFeature.setCreator(memberRepository.findById(form.getCreatorId()).orElse(null));
        roomFeatureRepository.save(roomFeature);
    }

    @Override
    public Iterable<RoomFeature> getAllFeatures() {
        return roomFeatureRepository.findAll();
    }

   @Override
    public Iterable<RoomFeature> getAllActiveRoomFeatures() {
        return roomFeatureRepository.findAllByStateEquals(RecordState.ACTIVE);
    }

   @Override
   public RoomFeature getRoomFeature(Long featureId) {
	   return roomFeatureRepository.findById(featureId).orElse(null);
   }
   
    @Override
    public void deactivate(IDTransfer idTransfer) {
        RoomFeature roomFeature = roomFeatureRepository.findById(idTransfer.getRecordId()).orElse(null);
        roomFeature.setState(RecordState.NONACTIVE);
        roomFeatureRepository.save(roomFeature);
    }
    
    @Override
    public void edit(AddRoomFeatureForm addRoomFeatureForm) {
    	RoomFeature roomFeature = roomFeatureRepository.findById(addRoomFeatureForm.getRecordId()).orElse(null);  	
    	roomFeature.setFeatureName(addRoomFeatureForm.getFeatureName());
    	roomFeature.setDescription(addRoomFeatureForm.getDescription());
        roomFeature.setUpdater(memberRepository.findById(addRoomFeatureForm.getUpdaterId()).orElse(null));
    	
    	roomFeatureRepository.save(roomFeature);
    }

    @Override
    public AddRoomFeatureForm getEditForm(Long roomFeatureId) {
        RoomFeature roomFeature = getRoomFeature(roomFeatureId);
        AddRoomFeatureForm addRoomFeatureForm = new AddRoomFeatureForm();

        addRoomFeatureForm.setRecordId(roomFeature.getRoomFeatureId());
        addRoomFeatureForm.setFeatureName(roomFeature.getFeatureName());
        addRoomFeatureForm.setDescription(roomFeature.getDescription());

        return addRoomFeatureForm;
    }
}