package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.*;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Map;

public interface MeetingRequestRepository extends CrudRepository<MeetingRequest, Long> {
	Iterable<MeetingRequest> findAllByStateEquals(RecordState state);
	Iterable<MeetingRequest> findAllByMeetingRequestStateEquals(MeetingState meetingState);
	Iterable<MeetingRequest> findAllByMember(Member member);
	int countAllByMeetingRequestState(MeetingState meetingState);
	Iterable<MeetingRequest> getAllByDateAndMeetingRoomAndMeetingRequestState(LocalDate date, MeetingRoom meetingRoom, MeetingState state);
}
