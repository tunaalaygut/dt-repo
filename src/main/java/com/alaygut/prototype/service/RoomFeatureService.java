package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.RoomFeature;
import com.alaygut.prototype.dto.AddRoomFeatureForm;
import com.alaygut.prototype.dto.IDTransfer;


import java.util.List;

public interface RoomFeatureService {
    void addRoomFeature(AddRoomFeatureForm addMeetingRoomForm);
    Iterable<RoomFeature> getAllFeatures();
    Iterable<RoomFeature> getAllActiveRoomFeatures();
    RoomFeature getRoomFeature(Long featureId);
    void deactivate(IDTransfer idTransfer);
    void edit(AddRoomFeatureForm addRoomFeatureForm);
    AddRoomFeatureForm getEditForm(Long roomFeatureId);
    Iterable<RoomFeature> getAllById(List<Long> roomFeatureIds);
    RoomFeature getById(Long roomFeatureId);
    Iterable <RoomFeature> getAllByMeetingRoomSet(MeetingRoom meetingRoom);
}
