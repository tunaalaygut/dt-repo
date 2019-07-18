package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.RoomFeature;
import com.alaygut.prototype.dto.*;
import com.alaygut.prototype.repository.MemberRepository;
import com.alaygut.prototype.repository.RoomFeatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoomFeatureServiceImpl implements RoomFeatureService {
    private RoomFeatureRepository roomFeatureRepository;
    private MemberService memberService;

    public RoomFeatureServiceImpl(RoomFeatureRepository roomFeatureRepository, MemberService memberService) {
        this.roomFeatureRepository = roomFeatureRepository;
        this.memberService= memberService;
    }

    /**
     * Database'e toplantı odası özelliği ekleme
     * @param form roomFeature DTO
     */

    @Override
    @Transactional(readOnly = false)
    public void addRoomFeature(AddRoomFeatureForm form) {
        RoomFeature roomFeature =
                new RoomFeature(form.getFeatureName(), form.getDescription());
        roomFeature.setCreator(memberService.getMember(form.getCreatorId()));
        roomFeatureRepository.save(roomFeature);
    }

    @Override
    public Iterable<RoomFeature> getAllFeatures() {
        return roomFeatureRepository.findAll();
    }

    /**
     * Stateleri Aktif(1) olan toplantı odası özelliklerini döndürür
     */

   @Override
    public Iterable<RoomFeature> getAllActiveRoomFeatures() {
        return roomFeatureRepository.findAllByStateEquals(RecordState.ACTIVE);
    }

   /**
    * Spesifik bir toplantı odası özelliği döndürür
    * @param featureId özelliğin unique id'si    
    */

   @Override
   public RoomFeature getRoomFeature(Long featureId) {
	   return roomFeatureRepository.findById(featureId).orElse(null);
   }
   
   /**
    * State'i Aktiften(1) Deaktife(0) alır
    * @param idTransfer id transfer objesi
    */

    @Override
    @Transactional(readOnly = false)
    public void deactivate(IDTransfer idTransfer) {
        RoomFeature roomFeature = roomFeatureRepository.findById(idTransfer.getRecordId()).orElse(null);
        roomFeature.setState(RecordState.NONACTIVE);
        roomFeatureRepository.save(roomFeature);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void edit(AddRoomFeatureForm addRoomFeatureForm) {
    	RoomFeature roomFeature = roomFeatureRepository.findById(addRoomFeatureForm.getRecordId()).orElse(null);  	
    	roomFeature.setFeatureName(addRoomFeatureForm.getFeatureName());
    	roomFeature.setDescription(addRoomFeatureForm.getDescription());
        roomFeature.setUpdater(memberService.getMember(addRoomFeatureForm.getUpdaterId()));
    	
    	roomFeatureRepository.save(roomFeature);
    }

    /**
     * Editlenecek özelliğin formunu dolu halde getirir
     * @param roomFeatureId editlenen özelliğin Id'si
     * @return dolu roomFeature DTO'su
     */

    @Override
    public AddRoomFeatureForm getEditForm(Long roomFeatureId) {
        RoomFeature roomFeature = getRoomFeature(roomFeatureId);
        AddRoomFeatureForm addRoomFeatureForm = new AddRoomFeatureForm();

        addRoomFeatureForm.setRecordId(roomFeature.getRoomFeatureId());
        addRoomFeatureForm.setFeatureName(roomFeature.getFeatureName());
        addRoomFeatureForm.setDescription(roomFeature.getDescription());

        return addRoomFeatureForm;
    }

    @Override
    public Iterable<RoomFeature> getAllById(List<Long> roomFeatureIds) {
        return roomFeatureRepository.findAllById(roomFeatureIds);
    }

    @Override
    public RoomFeature getById(Long roomFeatureId) {
        return roomFeatureRepository.findById(roomFeatureId).orElse(null);
    }

    public Iterable <RoomFeature> getAllByMeetingRoomSet(MeetingRoom meetingRoom) {
        return roomFeatureRepository.findAllByMeetingRoomSet(meetingRoom);
    }

}