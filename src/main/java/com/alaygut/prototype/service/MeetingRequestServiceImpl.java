package com.alaygut.prototype.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.RecordState;
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
	private MeetingRoomService meetingRoomService;
	private MemberService memberService;
	private MeetingTypeService meetingTypeService;
	private MeetingStatusService meetingStatusService;

	public MeetingRequestServiceImpl(MeetingRequestRepository meetingRequestRepository, MeetingRoomService meetingRoomService, MemberService memberService, MeetingTypeService meetingTypeService, MeetingStatusService meetingStatusService) {
		this.meetingRequestRepository = meetingRequestRepository;
		this.meetingRoomService = meetingRoomService;
		this.memberService = memberService;
		this.meetingTypeService = meetingTypeService;
		this.meetingStatusService = meetingStatusService;
	}

	@Override
	@Transactional(readOnly = false)
	public void addRequest(AddMeetingRequestForm form) {
		/*Optional<MeetingRoom> meetingRoom = meetingRoomRepository.findById(form.getMeetingRoomId());
		Optional<MeetingStatus> meetingStatus = meetingStatusRepository.findById(form.getMeetingStatusId());
		Optional<MeetingType> meetingType = meetingTypeRepository.findById(form.getMeetingTypeId());
		Optional<Member> member = memberRepository.findById(form.getMemberId());*/

		LocalDate date = LocalDate.parse(form.getDate());
		LocalTime startTime = LocalTime.parse(form.getBeginningTime());
		LocalTime endTime = LocalTime.parse(form.getEndTime());

		System.out.println(date + " " + startTime + " - " + endTime);




		/*MeetingRequest meetingRequest = new MeetingRequest(
				meetingRoom.orElse(null),
				member.orElse(null),
				meetingType.orElse(null),
				form.getBeginningTime(),
				form.getEndTime(),
				form.getDescription(),
				meetingStatus.orElse(null));
			
		meetingRequestRepository.save(meetingRequest);*/
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

}
