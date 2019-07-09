package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.*; 
import com.alaygut.prototype.repository.BuildingRepository;
import com.alaygut.prototype.repository.MemberRepository;
import com.alaygut.prototype.repository.RoomFeatureRepository;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingRoomRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

	private MeetingRoomRepository meetingRoomRepository;
	private BuildingRepository buildingRepository;
	private RoomFeatureRepository roomFeatureRepository;
	private MemberRepository memberRepository;

	public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository, BuildingRepository buildingRepository, RoomFeatureRepository roomFeatureRepository, MemberRepository memberRepository) {
		this.meetingRoomRepository = meetingRoomRepository;
		this.buildingRepository = buildingRepository;
		this.roomFeatureRepository = roomFeatureRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public void addRoom(AddMeetingRoomForm addMeetingRoomForm) {
		Optional<Building> building = buildingRepository.findById(addMeetingRoomForm.getBuildingId());

		Iterable<RoomFeature> selectedFeatures = roomFeatureRepository.findAllById(addMeetingRoomForm.getRoomFeatureIds());
		Set<RoomFeature> roomFeatures = new HashSet<>();

		for (RoomFeature roomFeature : selectedFeatures) {
			roomFeatures.add((roomFeatureRepository.findById(roomFeature.getRoomFeatureId())).orElse(null));
		}
		MeetingRoom meetingRoom = new MeetingRoom(
				addMeetingRoomForm.getMeetingRoomName(),
				building.orElse(null),
				addMeetingRoomForm.getCapacity(), roomFeatures
				);
		meetingRoom.setCreator(memberRepository.findById(addMeetingRoomForm.getCreatorId()).orElse(null));
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
	public MeetingRoom getMeetingRoom(Long meetingRoomId) {
		return meetingRoomRepository.findById(meetingRoomId).orElse(null);
	}

	@Override
	public void deactivate(IDTransfer idTransfer) {
		MeetingRoom meetingRoom = meetingRoomRepository.findById(idTransfer.getRecordId()).orElse(null);
		meetingRoom.setState(RecordState.NONACTIVE);
		meetingRoomRepository.save(meetingRoom);

	}
	
	@Override
	public void edit(AddMeetingRoomForm addMeetingRoomForm) {
		MeetingRoom meetingRoom = meetingRoomRepository.findById(addMeetingRoomForm.getRecordId()).orElse(null);
		Building building = buildingRepository.findById(addMeetingRoomForm.getBuildingId()).orElse(null);
		//RoomFeature roomFeature = roomFeatureRepository.findById(addMeetingRoomForm.getRoomFeatureId()).orElse(null);
		meetingRoom.setMeetingRoomName(addMeetingRoomForm.getMeetingRoomName());
		meetingRoom.setBuilding(building);
		meetingRoom.setCapacity(addMeetingRoomForm.getCapacity());
		//meetingRoom.setRoomFeatureSet(roomFeature);
		
		meetingRoomRepository.save(meetingRoom);
	}
}