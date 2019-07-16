package com.alaygut.prototype.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.alaygut.prototype.domain.*;
import com.alaygut.prototype.dto.ParticipantDetails;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingRequestRepository;
import com.alaygut.prototype.repository.MeetingRoomRepository;
import com.alaygut.prototype.repository.MeetingStatusRepository;
import com.alaygut.prototype.repository.MeetingTypeRepository;
import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MeetingRequestServiceImpl implements MeetingRequestService {
	
	private MeetingRequestRepository meetingRequestRepository;
	private MemberService memberService;
	private BuildingService buildingService;
	private MeetingRoomService meetingRoomService;
	private MeetingTypeService meetingTypeService;
	private MeetingRequestTimeGenerator meetingRequestTimeGenerator;
	private ParticipantService participantService;

	public MeetingRequestServiceImpl(MeetingRequestRepository meetingRequestRepository, MemberService memberService, BuildingService buildingService, MeetingRoomService meetingRoomService, MeetingTypeService meetingTypeService, MeetingRequestTimeGenerator meetingRequestTimeGenerator, ParticipantService participantService) {
		this.meetingRequestRepository = meetingRequestRepository;
		this.memberService = memberService;
		this.buildingService = buildingService;
		this.meetingRoomService = meetingRoomService;
		this.meetingTypeService = meetingTypeService;
		this.meetingRequestTimeGenerator = meetingRequestTimeGenerator;
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
		form.setTimes(meetingRequestTimeGenerator.generateTimes(8, 18, 5));
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

}
