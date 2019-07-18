package com.alaygut.prototype.service;
import java.time.LocalDate; 
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.*;


import com.alaygut.prototype.domain.*;
import com.alaygut.prototype.dto.MeetingRequestDetailProvider;
import com.alaygut.prototype.dto.ParticipantDetails;
import org.springframework.stereotype.Service;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.RecordState;

import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingRequestRepository;
import com.alaygut.prototype.repository.MeetingRoomRepository;
import com.alaygut.prototype.repository.MeetingStatusRepository;
import com.alaygut.prototype.repository.MeetingTypeRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MeetingRequestServiceImpl implements MeetingRequestService {
	
	private MeetingRequestRepository meetingRequestRepository;
	private MemberService memberService;
	private BuildingService buildingService;
	private MeetingRoomService meetingRoomService;
	private MeetingTypeService meetingTypeService;
	private ParticipantService participantService;

	public MeetingRequestServiceImpl(MeetingRequestRepository meetingRequestRepository, MemberService memberService, BuildingService buildingService, MeetingRoomService meetingRoomService, MeetingTypeService meetingTypeService, ParticipantService participantService) {
		this.meetingRequestRepository = meetingRequestRepository;
		this.memberService = memberService;
		this.buildingService = buildingService;
		this.meetingRoomService = meetingRoomService;
		this.meetingTypeService = meetingTypeService;
		this.participantService = participantService;
	}

	@Override
	@Transactional(readOnly = false)
	public void addRequest(AddMeetingRequestForm form) {
		LocalDate date = LocalDate.parse(form.getDate());
		LocalTime startTime = LocalTime.parse(form.getBeginningTime());
		LocalTime endTime = LocalTime.parse(form.getEndTime());

		MeetingRequest meetingRequest = new MeetingRequest(
				meetingRoomService.getMeetingRoom(form.getMeetingRoomId()),
				memberService.getMember(form.getCreatorId()),
				meetingTypeService.getMeetingType(form.getMeetingTypeId()),
				date,
				startTime,
				endTime,
				form.getDescription()
		);
		meetingRequest.setCreator(memberService.getMember(form.getCreatorId()));

		participantService.generateParticipants(form.getParticipantDetails(), meetingRequest);
		meetingRequestRepository.save(meetingRequest);
	}

	@Override
	public Iterable<MeetingRequest> getAllRequests() {
		return meetingRequestRepository.findAll();
	}
	
	@Override
	public Iterable<MeetingRequest> getAllActiveRequests() {
		return meetingRequestRepository.findAllByStateEquals(RecordState.ACTIVE);
	}

	@Override
	public Iterable<MeetingRequest> getAllPendingRequests() {
		return meetingRequestRepository.findAllByMeetingRequestStateEquals(MeetingState.ONAY_BEKLIYOR);
	}

	@Override
	public Iterable<MeetingRequest> getAllMemberMeetingRequests(Member member) {
		return meetingRequestRepository.findAllByMember(member);
	}

	@Override
	public Iterable<MeetingRequest> getAllByDateAndMeetingRoomAndMeetingRequestState(LocalDate date, MeetingRoom meetingRoom, MeetingState state) {
		return meetingRequestRepository.getAllByDateAndMeetingRoomAndMeetingRequestState(date, meetingRoom, state);
	}

	@Override
	public Map<String, String> getGridData(String date, Long meetingRoomId){
		LocalDate meetingDate = LocalDate.parse(date);
		MeetingRoom meetingRoom = meetingRoomService.getMeetingRoom(meetingRoomId);
		Iterable<MeetingRequest> requests = this.getAllByDateAndMeetingRoomAndMeetingRequestState(meetingDate, meetingRoom, MeetingState.ONAYLANDI);
		Iterator<MeetingRequest> requestIterator = requests.iterator();
		MeetingRequest current;
		Map<String, String> startEndTimes = new HashMap<>();

		while(requestIterator.hasNext()){
			current = requestIterator.next();
			startEndTimes.put(current.getStartTime().toString(), current.getEndTime().toString());
		}

		return startEndTimes;
	}

	@Override
	public MeetingRequest getMeetingRequest(Long meetingRequestId) {
		return meetingRequestRepository.findById(meetingRequestId).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deactivate(IDTransfer idTransfer) {
		MeetingRequest meetingRequest = meetingRequestRepository.findById(idTransfer.getRecordId()).orElse(null);
		meetingRequest.setState(RecordState.NONACTIVE);
		meetingRequestRepository.save(meetingRequest);
	}
	
	@Override
	public void edit(AddMeetingRequestForm addMeetingRequestForm) {
		//MeetingRequest meetingRequest = meetingRequestRepository.findById(addMeetingRequestForm.getRecordId()).orElse(null);
		//meetingRequest.setMeetingRoom(meetingRoom);
	}

	@Override
	public AddMeetingRequestForm getAddMeetingRequestForm() {
		AddMeetingRequestForm addMeetingRequestForm = new AddMeetingRequestForm();

		setExternalData(addMeetingRequestForm);

		return addMeetingRequestForm;
	}

	public void setExternalData(AddMeetingRequestForm form){
		form.setAllBuildings(buildingService.getAllActiveBuildings());
		form.setAllMeetingRooms(meetingRoomService.getAllActiveRooms());
		form.setAllMembers(memberService.getAllActiveMembers());
		form.setAllMeetingTypes(meetingTypeService.getAllActiveTypes());
		form.setParticipantDetails(new ArrayList<>());
	}

	@Override
	public Map<String, String> getBuildingMeetingRooms(Long buildingId) {
		Map<String, String> buildingMeetingRooms = new HashMap<>();

		Iterable<MeetingRoom> meetingRooms = meetingRoomService.getAllInBuilding(buildingId);

		for (MeetingRoom meetingRoom : meetingRooms)
			buildingMeetingRooms.put(String.valueOf(meetingRoom.getMeetingRoomId()), meetingRoom.getMeetingRoomName());

		return buildingMeetingRooms;
	}

    @Override
    public MeetingRequestDetailProvider getListMeetingRequestsDetailProvider() {
        MeetingRequestDetailProvider meetingRequestDetailProvider = new MeetingRequestDetailProvider();
        Iterable<MeetingRequest> requests = this.getAllRequests();

        meetingRequestDetailProvider.setMeetingParticipants(mapParticipants(requests));
        return meetingRequestDetailProvider;
    }

	@Override
	public MeetingRequestDetailProvider getPendingMeetingRequestsDetailProvider() {
		MeetingRequestDetailProvider meetingRequestDetailProvider = new MeetingRequestDetailProvider();
		Iterable<MeetingRequest> requests = this.getAllPendingRequests();

		meetingRequestDetailProvider.setMeetingParticipants(mapParticipants(requests));
		return meetingRequestDetailProvider;
	}

	@Override
	public MeetingRequestDetailProvider getMemberMeetingRequestDetailsProvider(Member member) {
		MeetingRequestDetailProvider meetingRequestDetailProvider = new MeetingRequestDetailProvider();
		Iterable<MeetingRequest> requests = this.getAllMemberMeetingRequests(member);

		meetingRequestDetailProvider.setMeetingParticipants(this.mapParticipants(requests));
		return meetingRequestDetailProvider;
	}

	private Map<MeetingRequest, List<Participant>> mapParticipants(Iterable<MeetingRequest> requests){
		Iterator<MeetingRequest> requestIterator = requests.iterator();
		Map<MeetingRequest, List<Participant>> meetingParticipants = new HashMap<>();

		while(requestIterator.hasNext()){
			MeetingRequest meetingRequest = requestIterator.next();
			meetingParticipants.put(meetingRequest, participantService.getAllParticipantsInMeetingRequest(meetingRequest));
		}

		return meetingParticipants;
	}

	@Override
	public int getNumberOfPendingRequests() {
		return meetingRequestRepository.countAllByMeetingRequestState(MeetingState.ONAY_BEKLIYOR);
	}

	@Override
	@Transactional(readOnly = false)
	public void declineMeetingRequest(Long meetingRequestId, Long supervisorId) {
		MeetingRequest request = this.getMeetingRequest(meetingRequestId);
		request.setUpdater(memberService.getMember(supervisorId));
		request.setMeetingRequestState(MeetingState.REDDEDILDI);
	}

	@Override
	@Transactional(readOnly = false)
	public void acceptMeetingRequest(Long meetingRequestId, Long supervisorId) {
		MeetingRequest request = this.getMeetingRequest(meetingRequestId);
		request.setUpdater(memberService.getMember(supervisorId));
		request.setMeetingRequestState(MeetingState.ONAYLANDI);
	}

}
