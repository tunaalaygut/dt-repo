package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.*; 

import com.alaygut.prototype.repository.RoomFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingRoomRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class MeetingRoomServiceImpl implements MeetingRoomService {

	private MeetingRoomRepository meetingRoomRepository;


	private BuildingService buildingService;
	private RoomFeatureService roomFeatureService;
	private MemberService memberService;

	public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository, BuildingService buildingService, RoomFeatureService roomFeatureService, MemberService memberService) {
		this.meetingRoomRepository = meetingRoomRepository;
		this.buildingService = buildingService;
		this.roomFeatureService = roomFeatureService;
		this.memberService = memberService;
	}
	
	/**
	 * Database'e toplantı odası ekleme
	 * @param addMeetingRoomForm meetingRoom DTO
	 */
	@Override
	@Transactional(readOnly = false)
	public void addRoom(AddMeetingRoomForm addMeetingRoomForm) {
		Building building = buildingService.getBuilding(addMeetingRoomForm.getBuildingId());


		Iterable<RoomFeature> selectedFeatures = roomFeatureService.getAllById(addMeetingRoomForm.getRoomFeatureIds());

		Set<RoomFeature> roomFeatures = new HashSet<>();

		for (RoomFeature roomFeature : selectedFeatures) {
			roomFeatures.add(roomFeatureService.getRoomFeature(roomFeature.getRoomFeatureId()));
		}
		MeetingRoom meetingRoom = new MeetingRoom(
				addMeetingRoomForm.getMeetingRoomName(),
				building,
				addMeetingRoomForm.getCapacity(),
				roomFeatures
				);
		meetingRoom.setCreator(memberService.getMember(addMeetingRoomForm.getCreatorId()));
		meetingRoomRepository.save(meetingRoom);
	}

	@Override
	public Iterable<MeetingRoom> getAllRooms() {
		return meetingRoomRepository.findAll();
	}
	
	/**
	 * Stateleri Aktif(1) olan toplantı odalarını döndürür
	 */
	@Override
	public Iterable<MeetingRoom> getAllActiveRooms() {
		
		return meetingRoomRepository.findAllByStateEquals(RecordState.ACTIVE);
	}
	/**
	 * Spesifik bir toplantı odasını döndürür
	 * @param meetingRoomId odanın unique id'si    
	 */
	@Override
	public MeetingRoom getMeetingRoom(Long meetingRoomId) {
		return meetingRoomRepository.findById(meetingRoomId).orElse(null);
	}
	
	/**
	 * State'i Aktiften(1) Deaktife(0) alır
	 * @param idTransfer id transfer objesi
	 */
	@Override
	@Transactional(readOnly = false)
	public void deactivate(IDTransfer idTransfer) {
		MeetingRoom meetingRoom = meetingRoomRepository.findById(idTransfer.getRecordId()).orElse(null);
		meetingRoom.setState(RecordState.NONACTIVE);
		meetingRoomRepository.save(meetingRoom);

	}
	
	@Override
	@Transactional(readOnly = false)
	public void edit(AddMeetingRoomForm addMeetingRoomForm) {
		MeetingRoom meetingRoom = meetingRoomRepository.findById(addMeetingRoomForm.getRecordId()).orElse(null);
		Building building = buildingService.getBuilding(addMeetingRoomForm.getBuildingId());

		meetingRoom.setMeetingRoomName(addMeetingRoomForm.getMeetingRoomName());
		meetingRoom.setBuilding(building);
		meetingRoom.setCapacity(addMeetingRoomForm.getCapacity());

		Iterable<RoomFeature> selectedFeatures = roomFeatureService.getAllById(addMeetingRoomForm.getRoomFeatureIds());
		Set<RoomFeature> roomFeatures = new HashSet<>();

		for (RoomFeature roomFeature : selectedFeatures) {
			roomFeatures.add(roomFeatureService.getById(roomFeature.getRoomFeatureId()));
		}

		meetingRoom.setRoomFeatureSet(roomFeatures);
		meetingRoom.setUpdater(memberService.getMember(addMeetingRoomForm.getUpdaterId()));
		
		meetingRoomRepository.save(meetingRoom);
	}
	
	/**
	 * Editlenecek toplantı odası formunu dolu halde getirir
	 * @param meetingRoomId editlenen toplantı odası Id'si
	 * @return dolu meetingRoom DTO'su
	 */
	@Override
	public AddMeetingRoomForm getEditPage(Long meetingRoomId) { //returns a dto that is pre-filled with a record's details.
		AddMeetingRoomForm addMeetingRoomForm = new AddMeetingRoomForm();

		MeetingRoom meetingRoom = this.getMeetingRoom(meetingRoomId);

		addMeetingRoomForm.setMeetingRoomName(meetingRoom.getMeetingRoomName());
		addMeetingRoomForm.setCapacity(meetingRoom.getCapacity());
		addMeetingRoomForm.setBuildingId(meetingRoom.getBuilding().getBuildingId());
		addMeetingRoomForm.setRecordId(meetingRoom.getMeetingRoomId());
		addMeetingRoomForm.setAllBuildings(buildingService.getAllActiveBuildings());
		addMeetingRoomForm.setAllFeatures(roomFeatureService.getAllActiveRoomFeatures());
		addMeetingRoomForm.setMeetingRoomFeatures(this.getAllRoomFeatures(meetingRoom));

		return addMeetingRoomForm;
	}

	/**
	 * Belli bir odadaki tüm oda özelliklerini dödürür
	 * @param meetingRoom toplantı odası entitysi 
	 */
	@Override
	public Iterable<RoomFeature> getAllRoomFeatures(MeetingRoom meetingRoom) {
		return roomFeatureService.getAllByMeetingRoomSet(this.getMeetingRoom(meetingRoom.getMeetingRoomId()));
	}

	/**
	 * Belli bir binadaki tüm toplantı odalarını döndürür
	 * @param buildingId aranan binanın unique Id'si
	 */
	@Override
	public Iterable<MeetingRoom> getAllInBuilding(Long buildingId) {
		return meetingRoomRepository.findByBuilding(buildingService.getBuilding(buildingId));
	}

	@Override
	public AddMeetingRoomForm getAddMeetingRoomPage() {	//returns an empty dto.
		AddMeetingRoomForm addMeetingRoomForm = new AddMeetingRoomForm();
		addMeetingRoomForm.setAllBuildings(buildingService.getAllActiveBuildings());
		addMeetingRoomForm.setAllFeatures(roomFeatureService.getAllActiveRoomFeatures());
		return addMeetingRoomForm;
	}

	@Override
	public void fixAddForm(AddMeetingRoomForm addMeetingRoomForm) {
		addMeetingRoomForm.setAllBuildings(buildingService.getAllActiveBuildings());
		addMeetingRoomForm.setAllFeatures(roomFeatureService.getAllActiveRoomFeatures());
		addMeetingRoomForm.setMeetingRoomFeatures(this.getAllRoomFeatures(this.getMeetingRoom(addMeetingRoomForm.getRecordId())));
	}

}