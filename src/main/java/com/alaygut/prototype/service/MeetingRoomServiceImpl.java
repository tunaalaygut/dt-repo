package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.repository.BuildingRepository;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.repository.MeetingRoomRepository;
import java.util.Optional;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

	private MeetingRoomRepository meetingRoomRepository;
	private BuildingRepository buildingRepository;

	public MeetingRoomServiceImpl(BuildingRepository buildingRepository, MeetingRoomRepository meetingRoomRepository) {
		this.buildingRepository = buildingRepository;
		this.meetingRoomRepository = meetingRoomRepository;
	}
	
	@Override
	public void addRoom(AddMeetingRoomForm addMeetingRoomForm) {
		Optional<Building> building = buildingRepository.findById(addMeetingRoomForm.getBuildingId());
		MeetingRoom meetingRoom = new MeetingRoom(
				addMeetingRoomForm.getMeetingRoomName(),
				building.orElse(null),
				addMeetingRoomForm.getCapacity()
		);
		meetingRoomRepository.save(meetingRoom);
	}

	@Override
	public Iterable<MeetingRoom> getAllRooms() {
		return meetingRoomRepository.findAll();
	}
	
	@Override
	public Iterable<MeetingRoom> getAllActiveRooms() {
		return meetingRoomRepository.findAllbyStateEquals(RecordState.ACTIVE);
	}
}