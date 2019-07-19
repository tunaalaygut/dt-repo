package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;
import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.MeetingState;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.RecordState;
import java.time.LocalDate;


public interface MeetingRequestRepository extends CrudRepository<MeetingRequest, Long> {
	Iterable<MeetingRequest> findAllByStateEquals(RecordState state);
	Iterable<MeetingRequest> findAllByMeetingRequestStateEquals(MeetingState meetingState);
	Iterable<MeetingRequest> findAllByMember(Member member);
	int countAllByMeetingRequestState(MeetingState meetingState);
	Iterable<MeetingRequest> getAllByDateAndMeetingRoomAndMeetingRequestState(LocalDate date, MeetingRoom meetingRoom, MeetingState state);
}
