package com.alaygut.prototype.service;

import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.RoomFeature;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingRoomRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	@Transactional
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
	@Transactional
	public boolean edit(AddMeetingRoomForm addMeetingRoomForm) {
		MeetingRoom meetingRoom = this.getMeetingRoom(addMeetingRoomForm.getRecordId());

		if(isUpdated(addMeetingRoomForm, meetingRoom)){
			meetingRoom.setMeetingRoomName(addMeetingRoomForm.getMeetingRoomName());
			meetingRoom.setBuilding(buildingService.getBuilding(addMeetingRoomForm.getBuildingId()));
			meetingRoom.setCapacity(addMeetingRoomForm.getCapacity());
			meetingRoom.setRoomFeatureSet(this.getRoomFeatures(addMeetingRoomForm.getRoomFeatureIds()));
			meetingRoom.setUpdater(memberService.getMember(addMeetingRoomForm.getUpdaterId()));
			return true;
		}
		else
			return false;
	}

	private boolean isUpdated(AddMeetingRoomForm form, MeetingRoom meetingRoom){
		if(! meetingRoom.getMeetingRoomName().equals(form.getMeetingRoomName()))
			return true;
		if(! meetingRoom.getBuilding().equals(buildingService.getBuilding(form.getBuildingId())))
			return true;
		if(!meetingRoom.getCapacity().equals(form.getCapacity()))
			return true;
		if(!meetingRoom.getRoomFeatureSet().equals(this.getRoomFeatures(form.getRoomFeatureIds())))
			return true;

		return false;
	}

	/**
	 * Verilen bir roomFeatureId list'i ile
	 * o listedeki tüm featureları getiren ve
	 * bir set içinde return eden fonksiyon.
	 * @param roomFeatureIds idlerin listesi
	 * @return roomFeatures return edilen set.
	 */

	private Set<RoomFeature> getRoomFeatures(List<Long> roomFeatureIds){
		Iterable<RoomFeature> selectedFeatures = roomFeatureService.getAllById(roomFeatureIds);
		Set<RoomFeature> roomFeatures = new HashSet<>();

		for (RoomFeature roomFeature : selectedFeatures)
			roomFeatures.add(roomFeatureService.getById(roomFeature.getRoomFeatureId()));

		return roomFeatures;
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
		if(addMeetingRoomForm.getRecordId() != null)
			addMeetingRoomForm.setMeetingRoomFeatures(this.getAllRoomFeatures(this.getMeetingRoom(addMeetingRoomForm.getRecordId())));
	}

	@Override
	public Map<Long, String> filterMeetingRoomsByCapacityAndFeatures(String capacity) {
		Map<Long, String> filteredRooms = new HashMap<>();

		Iterable<MeetingRoom> meetingRooms = meetingRoomRepository.findAllByCapacityGreaterThanEqual(Integer.valueOf(capacity));
		Iterator<MeetingRoom> meetingRoomIterator = meetingRooms.iterator();

		while (meetingRoomIterator.hasNext()){
			MeetingRoom current = meetingRoomIterator.next();
			filteredRooms.put(current.getMeetingRoomId(), current.getMeetingRoomName());
		}

		return filteredRooms;
	}


}