package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.*;
import com.alaygut.prototype.repository.BuildingRepository;
import com.alaygut.prototype.repository.RoomFeatureRepository;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingRoomRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

	private MeetingRoomRepository meetingRoomRepository;
	private BuildingRepository buildingRepository;
	private RoomFeatureRepository roomFeatureRepository;

	public MeetingRoomServiceImpl(BuildingRepository buildingRepository, MeetingRoomRepository meetingRoomRepository, RoomFeatureRepository roomFeatureRepository) {
		this.buildingRepository = buildingRepository;
		this.meetingRoomRepository = meetingRoomRepository;
		this.roomFeatureRepository = roomFeatureRepository;
	}

	@Override
	public void addRoom(AddMeetingRoomForm addMeetingRoomForm) {
		Optional<Building> building = buildingRepository.findById(addMeetingRoomForm.getBuildingId());

		Iterable<RoomFeature> selectedFeatures = roomFeatureRepository.findAllById(addMeetingRoomForm.getRoomFeatureIds());
		Set<RoomFeature> roomFeatures = new HashSet<>();

		for (RoomFeature roomFeature : selectedFeatures) {
			roomFeatures.add((roomFeatureRepository.findById(roomFeature.getFeatureId())).orElse(null));
		}
		MeetingRoom meetingRoom = new MeetingRoom(
				addMeetingRoomForm.getMeetingRoomName(),
				building.orElse(null),
				addMeetingRoomForm.getCapacity(), roomFeatures
				);
		meetingRoomRepository.save(meetingRoom);
	}

	@Override
	public Iterable<MeetingRoom> getAllRooms() {
		return meetingRoomRepository.findAll();
	}

	@Override
	public Iterable<MeetingRoom> getAllActiveRooms() {
		return meetingRoomRepository.findAllByStateEquals(RecordState.ACTIVE);
	}

	@Override
	public void deactivate(IDTransfer idTransfer) {
		MeetingRoom meetingRoom = meetingRoomRepository.findById(idTransfer.getRecordId()).orElse(null);
		meetingRoom.setState(RecordState.NONACTIVE);
		meetingRoomRepository.save(meetingRoom);

	}
}