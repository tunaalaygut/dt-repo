package com.alaygut.prototype.service;
import java.time.LocalDate; 
import java.time.LocalTime;

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
	private MeetingRoomRepository meetingRoomRepository;
	private MemberService memberService;
	private MeetingTypeRepository meetingTypeRepository;
	private MeetingStatusRepository meetingStatusRepository;

	public MeetingRequestServiceImpl(MeetingRequestRepository meetingRequestRepository, MeetingRoomRepository meetingRoomRepository, MemberService memberService, MeetingTypeRepository meetingTypeRepository, MeetingStatusRepository meetingStatusRepository) {
		this.meetingRequestRepository = meetingRequestRepository;
		this.meetingRoomRepository = meetingRoomRepository;
		this.memberService = memberService;
		this.meetingTypeRepository = meetingTypeRepository;
		this.meetingStatusRepository = meetingStatusRepository;
	}

	@Override
	@Transactional
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
