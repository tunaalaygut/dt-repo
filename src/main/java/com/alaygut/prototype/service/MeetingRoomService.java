package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.dto.AddMeetingRoomForm;

public interface MeetingRoomService {
	void addRoom(AddMeetingRoomForm addMeetingRoomForm);
	Iterable<MeetingRoom> getAllRooms();
	Iterable<MeetingRoom> getAllActiveRooms();
}
