package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.RoomFeature;
import com.alaygut.prototype.dto.*;
import com.alaygut.prototype.repository.RoomFeatureRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomFeatureServiceImpl implements RoomFeatureService {
    private RoomFeatureRepository roomFeatureRepository;

    public RoomFeatureServiceImpl(RoomFeatureRepository roomFeatureRepository) {
        this.roomFeatureRepository = roomFeatureRepository;
    }

    @Override
    public void addRoomFeature(AddRoomFeatureForm form) {
        RoomFeature roomFeature =
                new RoomFeature(form.getFeatureName(), form.getDescription());
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
    	roomFeatureRepository.save(roomFeature);
    }
}