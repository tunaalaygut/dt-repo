package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRoom; 
import com.alaygut.prototype.domain.RoomFeature;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.IDTransfer;

import java.util.List;
import java.util.Map;

public interface MeetingRoomService {
	void addRoom(AddMeetingRoomForm addMeetingRoomForm);
	Iterable<MeetingRoom> getAllRooms();
	Iterable<MeetingRoom> getAllActiveRooms();
	MeetingRoom getMeetingRoom(Long meetingRoomId);
	void deactivate(IDTransfer idTransfer);
	boolean edit(AddMeetingRoomForm addMeetingRoomForm);
	AddMeetingRoomForm getEditPage(Long meetingRoomId);
	Iterable<RoomFeature> getAllRoomFeatures(MeetingRoom meetingRoom);
	Iterable<MeetingRoom> getAllInBuilding(Long buildingId);
	AddMeetingRoomForm getAddMeetingRoomPage();
	void fixAddForm(AddMeetingRoomForm addMeetingRoomForm);
	Map<Long, String> filterMeetingRoomsByCapacityAndFeatures(String capacity);
	List<String> loadMeetingRoomProperties(Long meetingRoomId);
}
