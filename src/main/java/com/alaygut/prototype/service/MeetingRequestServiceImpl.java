package com.alaygut.prototype.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alaygut.prototype.dto.MeetingDetail;
import com.alaygut.prototype.dto.MeetingRequestDetailProvider;
import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.MeetingState;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.Participant;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MeetingRequestRepository;
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
	@Transactional
	public boolean addRequest(AddMeetingRequestForm form) {
		LocalDate date = LocalDate.parse(form.getDate());
		LocalTime startTime = LocalTime.parse(form.getBeginningTime());
		LocalTime endTime = LocalTime.parse(form.getEndTime());
		MeetingRoom meetingRoom = meetingRoomService.getMeetingRoom(form.getMeetingRoomId());

		if (meetingRequestRepository.existsByMeetingRoomAndDateAndStartTimeAndEndTimeAndMeetingRequestStateEquals(
				meetingRoom,
				date,
				startTime,
				endTime,
				MeetingState.ONAYLANDI
		)) {
			return false;
		}

		MeetingRequest meetingRequest = new MeetingRequest(
				meetingRoom,
				memberService.getMember(form.getCreatorId()),
				meetingTypeService.getMeetingType(form.getMeetingTypeId()),
				date,
				startTime,
				endTime,
				form.getDescription(),
				MeetingState.ONAY_BEKLIYOR
		);
		meetingRequest.setCreator(memberService.getMember(form.getCreatorId()));

		participantService.generateParticipants(form.getParticipantDetails(), meetingRequest);
		meetingRequestRepository.save(meetingRequest);

		return true;
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
	public Iterable<MeetingRequest> getAllMemberToMemberMeetingRequests(Member member) {
		return meetingRequestRepository.findAllByRequestMadeToAndMeetingRequestState(member, MeetingState.KULLANICI_ONAYI_BEKLIYOR);
	}


	@Override
	public Iterable<MeetingRequest> getAllByDateAndMeetingRoomAndMeetingRequestState(LocalDate date, MeetingRoom meetingRoom, MeetingState state) {
		return meetingRequestRepository.getAllByDateAndMeetingRoomAndMeetingRequestState(date, meetingRoom, state);
	}

	@Override
	public List<MeetingDetail> getGridData(String date, String meetingRoomId){
		List<MeetingDetail> meetingDetails = new ArrayList<>();

		MeetingRoom meetingRoom = meetingRoomService.getMeetingRoom(Long.parseLong(meetingRoomId));
		Iterable<MeetingRequest> requests = this.getAllByDateAndMeetingRoomAndMeetingRequestState( LocalDate.parse(date), meetingRoom, MeetingState.ONAYLANDI);
		Iterator<MeetingRequest> requestIterator = requests.iterator();
		MeetingRequest current;

		while(requestIterator.hasNext()){
			current = requestIterator.next();
			meetingDetails.add(getMeetingDetailObject(current));
		}

		return meetingDetails;
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

	@Override
	public MeetingRequestDetailProvider getMemberToMemberMeetingRequestDetailsProvider(Member member){
		MeetingRequestDetailProvider meetingRequestDetailProvider = new MeetingRequestDetailProvider();
		Iterable<MeetingRequest> requests = this.getAllMemberToMemberMeetingRequests(member);

		meetingRequestDetailProvider.setMeetingParticipants(this.mapParticipants(requests));
		return meetingRequestDetailProvider;
	}


	@Override
	@Transactional
	public void acceptMemberMeetingRequest(Long meetingRequestId, Long memberId) {
		MeetingRequest meetingRequest = this.getMeetingRequest(meetingRequestId);
		meetingRequest.setMeetingRequestState(MeetingState.ONAYLANDI);

		MeetingRequest original = meetingRequestRepository.getByDateAndStartTimeAndEndTimeAndMember(
				meetingRequest.getDate(),
				meetingRequest.getStartTime(),
				meetingRequest.getEndTime(),
				meetingRequest.getMember()
		);

		original.setMeetingRequestState(MeetingState.TRANSFER_EDILDI);

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
	@Transactional
	public void acceptMeetingRequest(Long meetingRequestId, Long supervisorId) {
		MeetingRequest request = this.getMeetingRequest(meetingRequestId);
		request.setUpdater(memberService.getMember(supervisorId));
		request.setMeetingRequestState(MeetingState.ONAYLANDI);
	}


	@Override
	@Transactional
	public void cancel(Long meetingRequestId) {
		MeetingRequest meetingRequest = this.getMeetingRequest(meetingRequestId);
		meetingRequest.setMeetingRequestState(MeetingState.IPTAL_EDILDI);

	}

	@Override
	@Transactional
	public boolean requestFromUser(AddMeetingRequestForm form){
		MeetingRequest meetingRequest = new MeetingRequest(
				meetingRoomService.getMeetingRoom(form.getMeetingRoomId()),
				memberService.getMember(form.getCreatorId()),
				meetingTypeService.getMeetingType(form.getMeetingTypeId()),
				LocalDate.parse(form.getDate()),
				LocalTime.parse(form.getBeginningTime()),
				LocalTime.parse(form.getEndTime()),
				form.getDescription(),
				MeetingState.KULLANICI_ONAYI_BEKLIYOR
		);
		meetingRequest.setCreator(memberService.getMember(form.getCreatorId()));
		meetingRequest.setRequestMadeTo(memberService.getMember(form.getRequestMadeTo()));
		meetingRequestRepository.save(meetingRequest);

		return true;
	}

	@Override
	public List<MeetingRequest> getMemberRequests(Member member) {
		List<MeetingRequest> requests = new ArrayList<>();
		Iterable<MeetingRequest> meetingRequests = meetingRequestRepository.findAllByMemberAndMeetingRequestState(member, MeetingState.KULLANICI_ONAYI_BEKLIYOR);
		Iterator<MeetingRequest> meetingRequestIterator = meetingRequests.iterator();

		while(meetingRequestIterator.hasNext()){
			MeetingRequest current = meetingRequestIterator.next();
			requests.add(current);
		}

		return requests;
	}

	private MeetingDetail getMeetingDetailObject(MeetingRequest meetingRequest){
		MeetingDetail meetingDetail = new MeetingDetail();

		meetingDetail.setSupervisorFullName(meetingRequest.getUpdater().getFirstName() + " " + meetingRequest.getUpdater().getLastName());
		meetingDetail.setBeginningTime(String.valueOf(meetingRequest.getStartTime()));
		meetingDetail.setEndTime(String.valueOf(meetingRequest.getEndTime()));
		meetingDetail.setMember(meetingRequest.getMember().getFirstName() + " " + meetingRequest.getMember().getLastName());
		meetingDetail.setMemberId(String.valueOf(meetingRequest.getMember().getMemberId()));
		meetingDetail.setParticipants(getParticipantString(meetingRequest));
		meetingDetail.setMeetingType(String.valueOf(meetingRequest.getMeetingType().getMeetingTypeName()));

		return meetingDetail;
	}

	private String getParticipantString(MeetingRequest meetingRequest){
		String participants = "";

		for (Participant p : participantService.getAllParticipantsInMeetingRequest(meetingRequest))
			participants += (p.getFullName() + ", ");

		return participants.replaceAll(", $", "");    //to remove the last comma from the string.
	}
}
