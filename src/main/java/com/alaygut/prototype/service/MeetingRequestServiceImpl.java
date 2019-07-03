package com.alaygut.prototype.service;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.repository.MeetingRequestRepository;
import com.alaygut.prototype.repository.MeetingRoomRepository;
import com.alaygut.prototype.repository.MeetingStatusRepository;
import com.alaygut.prototype.repository.MeetingTypeRepository;
import com.alaygut.prototype.repository.MemberRepository;

@Service
public class MeetingRequestServiceImpl implements MeetingRequestService {
	
	private MeetingRequestRepository meetingRequestRepository;
	private MeetingRoomRepository meetingRoomRepository;
	private MemberRepository memberRepository;
	private MeetingTypeRepository meetingTypeRepository;
	private MeetingStatusRepository meetingStatusRepository;

	public MeetingRequestServiceImpl(MeetingRequestRepository meetingRequestRepository, MeetingRoomRepository meetingRoomRepository,
									 MemberRepository memberRepository, MeetingTypeRepository meetingTypeRepository,
									 MeetingStatusRepository meetingStatusRepository) {
		this.meetingRequestRepository = meetingRequestRepository;
		this.meetingRoomRepository = meetingRoomRepository;
		this.meetingStatusRepository = meetingStatusRepository;
		this.meetingTypeRepository = meetingTypeRepository;
		this.memberRepository = memberRepository;
	}

	@Override
	public void addRequest(AddMeetingRequestForm form) {
		Optional<MeetingRoom> meetingRoom = meetingRoomRepository.findById(form.getMeetingRoomId());
		Optional<MeetingStatus> meetingStatus = meetingStatusRepository.findById(form.getMeetingStatusId());
		Optional<MeetingType> meetingType = meetingTypeRepository.findById(form.getMeetingTypeId());
		Optional<Member> member = memberRepository.findById(form.getMemberId());
		
		MeetingRequest meetingRequest = new MeetingRequest(
				meetingRoom.orElse(null),
				member.orElse(null),
				meetingType.orElse(null),
				form.getStartTime(),
				form.getEndTime(),
				form.getDescription(),
				meetingStatus.orElse(null));
			
		meetingRequestRepository.save(meetingRequest);
	}

	@Override
	public Iterable<MeetingRequest> getAllRequests() {
		return meetingRequestRepository.findAll();
	}

}
