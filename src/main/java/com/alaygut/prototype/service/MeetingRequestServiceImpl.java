package com.alaygut.prototype.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.alaygut.prototype.dto.MeetingDetail;
import com.alaygut.prototype.dto.MeetingRequestDetailProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@Transactional(readOnly = true)
public class MeetingRequestServiceImpl implements MeetingRequestService {

	private MeetingRequestRepository meetingRequestRepository;
	private MemberService memberService;
	private BuildingService buildingService;
	private MeetingRoomService meetingRoomService;
	private MeetingTypeService meetingTypeService;
	private ParticipantService participantService;
	private EmailSenderService emailSenderService;

	@Value("{program.url}")
	private String PROGRAM_URL;

	public MeetingRequestServiceImpl(MeetingRequestRepository meetingRequestRepository, MemberService memberService, BuildingService buildingService, MeetingRoomService meetingRoomService, MeetingTypeService meetingTypeService, ParticipantService participantService, EmailSenderService emailSenderService) {
		this.meetingRequestRepository = meetingRequestRepository;
		this.memberService = memberService;
		this.buildingService = buildingService;
		this.meetingRoomService = meetingRoomService;
		this.meetingTypeService = meetingTypeService;
		this.participantService = participantService;
		this.emailSenderService = emailSenderService;

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
		sendRejectionEmail(request);
	}

	@Override
	@Transactional
	public void acceptMeetingRequest(Long meetingRequestId, Long supervisorId) {
		MeetingRequest request = this.getMeetingRequest(meetingRequestId);
		request.setUpdater(memberService.getMember(supervisorId));
		request.setMeetingRequestState(MeetingState.ONAYLANDI);
		sendConfirmationEmail(request);
	}


	@Override
	@Transactional
	public void cancel(Long meetingRequestId) {
		MeetingRequest meetingRequest = this.getMeetingRequest(meetingRequestId);
		meetingRequest.setMeetingRequestState(MeetingState.IPTAL_EDILDI);
		sendCancelEmail(meetingRequest);
	}

	@Override
	@Transactional
	public boolean requestFromUser(AddMeetingRequestForm form){

		if(!meetingRequestRepository.existsByDateAndStartTimeAndEndTimeAndMemberAndMeetingRoom(
				LocalDate.parse(form.getDate()),
				LocalTime.parse(form.getBeginningTime()),
				LocalTime.parse(form.getEndTime()),
				memberService.getMember(form.getCreatorId()),
				meetingRoomService.getMeetingRoom(form.getMeetingRoomId())
		)){
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
			participantService.generateParticipants(form.getParticipantDetails(), meetingRequest);
			meetingRequestRepository.save(meetingRequest);
			return true;
		}

		return false;
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
	@Async
	public void sendCancelEmail(MeetingRequest meetingRequest) {
		List<Participant> participants = participantService.getAllParticipantsInMeetingRequest(meetingRequest);
		InternetAddress[] Address = new InternetAddress[participants.size()];
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject("Toplantı İptali Hakkında Bilgilendirme");
		mailMessage.setFrom("dijital.toplanti@gmail.com");
		mailMessage.setText("Aşağıda özellikleri belirtilen toplantı iptal edilmiştir.\nToplantının Bilgileri: " +
				"\nTarih: " + meetingRequest.getDate()+ "\nSaat: " +meetingRequest.getStartTime() + " - " +meetingRequest.getEndTime()+
				"\nBina: " + meetingRequest.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " +meetingRequest.getMeetingRoom().getMeetingRoomName()+
				"\nToplantı Türü: " + meetingRequest.getMeetingType().getMeetingTypeName() +
				"\nToplantı Açıklaması: " +meetingRequest.getDescription());

		for( int i = 0; i < participants.size() ; i++ ) {
			try {
				Address[i] = new InternetAddress(participants.get(i).getEmail());
			} catch (AddressException e) {
				e.printStackTrace();
			}
			mailMessage.setTo(Address[i].toString());
			emailSenderService.sendEmail(mailMessage);
		}
	}

	@Transactional
	@Override
	public boolean acceptMemberRequest(Long requestId) {
		MeetingRequest newRequest = this.getMeetingRequest(requestId);

		MeetingRequest original = meetingRequestRepository.getByDateAndStartTimeAndEndTimeAndMeetingRequestState(
				newRequest.getDate(),
				newRequest.getStartTime(),
				newRequest.getEndTime(),
				MeetingState.ONAYLANDI
		) ;

		original.setMeetingRequestState(MeetingState.TRANSFER_EDILDI);
		newRequest.setMeetingRequestState(MeetingState.ONAYLANDI);
		newRequest.setUpdater(original.getMember());

		return true;
	}

	@Transactional
	@Override
	public boolean declineMemberRequest(Long requestId, Member member) {
		MeetingRequest newRequest = this.getMeetingRequest(requestId);

		newRequest.setMeetingRequestState(MeetingState.REDDEDILDI);
		newRequest.setUpdater(member);

		return true;
	}

	@Override
	public int otherMemberRequestNumber(Member member) {
		return meetingRequestRepository.countAllByMeetingRequestStateAndRequestMadeTo(MeetingState.KULLANICI_ONAYI_BEKLIYOR, member);
	}

	@Async
	public void sendConfirmationEmail(MeetingRequest request) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(request.getCreator().getEmail());
		mailMessage.setSubject("Toplantı Talebiniz Hakkında Bilgilendirme");
		mailMessage.setFrom("dijital.toplanti@gmail.com");
		mailMessage.setText("Toplantı talebiniz kabul edilmiştir.\nToplantı Bilgileriniz: " +
				"\nTarih: " + request.getDate()+ "\nSaat: " +request.getStartTime() + " - " +request.getEndTime()+
				"\nBina: " + request.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " +request.getMeetingRoom().getMeetingRoomName()+
				"\nToplantı Türü: " + request.getMeetingType().getMeetingTypeName() +
				"\nToplantı Açıklaması: " + request.getDescription() +
				"\n\nAşağıdaki linkten Dijital Toplantı'ya ulaşabilirsiniz:\n" +PROGRAM_URL);

		// Sends the email to the user who requested the meeting
		emailSenderService.sendEmail(mailMessage);

		// Sends the email to all participants of the meeting
		List<Participant> participants = participantService.getAllParticipantsInMeetingRequest(request);
		InternetAddress[] Address = new InternetAddress[participants.size()];
		SimpleMailMessage mailMessage1 = new SimpleMailMessage();
		mailMessage1.setSubject("Toplantı Hakkında Bilgilendirme");
		mailMessage1.setFrom("dijital.toplanti@gmail.com");
		mailMessage1.setText( request.getCreator().getFirstName() + " " + request.getCreator().getLastName() +
				" sizi aşağıda bilgileri belirtilen toplantıya eklemiştir.\n\nToplantının Bilgileri: " +
				"\nTarih: " + request.getDate()+ "\nSaat: " +request.getStartTime() + " - " +request.getEndTime()+
				"\nBina: " + request.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " +request.getMeetingRoom().getMeetingRoomName() +
				"\nToplantı Türü: " + request.getMeetingType().getMeetingTypeName() +
				"\nToplantı Açıklaması: " + request.getDescription()+
				"\n\nAşağıdaki linkten Dijital Toplantı'ya ulaşabilirsiniz:\n " +PROGRAM_URL );

		for( int i = 0; i < participants.size() ; i++ ) {
			try {
				Address[i] = new InternetAddress(participants.get(i).getEmail());
			} catch (AddressException e) {
				e.printStackTrace();
			}
			if(!request.getCreator().getEmail().equals(Address[i].toString())) {
				mailMessage1.setTo(Address[i].toString());
				emailSenderService.sendEmail(mailMessage1);
			}
		}
	}

	@Async
	public void sendRejectionEmail(MeetingRequest request) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(request.getCreator().getEmail());
		mailMessage.setSubject("Toplantı Talebiniz Hakkında Bilgilendirme");
		mailMessage.setFrom("dijital.toplanti@gmail.com");
		mailMessage.setText("Toplantı talebiniz reddedilmiştir.\nTalep Edilen Toplantının Bilgileri: " +
				"\nTarih: " + request.getDate()+ "\nSaat: " +request.getStartTime() + " - " +request.getEndTime()+
				"\nBina: " + request.getMeetingRoom().getBuilding().getBuildingName() + "\nOda: " +request.getMeetingRoom().getMeetingRoomName()+
				"\nToplantı Türü: " + request.getMeetingType().getMeetingTypeName() +
				"\nToplantı Açıklaması: " + request.getDescription());

		// Send the email
		emailSenderService.sendEmail(mailMessage);
	}
}
