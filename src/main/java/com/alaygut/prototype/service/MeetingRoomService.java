package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.IDTransfer;

public interface MeetingRoomService {
	void addRoom(AddMeetingRoomForm addMeetingRoomForm);
	Iterable<MeetingRoom> getAllRooms();
	Iterable<MeetingRoom> getAllActiveRooms();
	MeetingRoom getMeetingRoom(Long meetingRoomId);
	void deactivate(IDTransfer idTransfer);
	void edit(AddMeetingRoomForm addMeetingRoomForm);
}
