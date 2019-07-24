package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;
import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.MeetingState;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.RecordState;
import java.time.LocalDate;
import java.time.LocalTime;


public interface MeetingRequestRepository extends CrudRepository<MeetingRequest, Long> {
	Iterable<MeetingRequest> findAllByStateEquals(RecordState state);
	Iterable<MeetingRequest> findAllByMeetingRequestStateEquals(MeetingState meetingState);
	Iterable<MeetingRequest> findAllByMember(Member member);
	int countAllByMeetingRequestState(MeetingState meetingState);
	Iterable<MeetingRequest> getAllByDateAndMeetingRoomAndMeetingRequestState(LocalDate date, MeetingRoom meetingRoom, MeetingState state);
	boolean existsByMeetingRoomAndDateAndStartTimeAndEndTimeAndMeetingRequestStateEquals(MeetingRoom meetingRoom, LocalDate date, LocalTime startTime, LocalTime endTime, MeetingState meetingState);
	Iterable<MeetingRequest> findAllByMemberAndMeetingRequestState(Member member, MeetingState state);
	MeetingRequest getByDateAndStartTimeAndEndTimeAndMember(LocalDate date, LocalTime startTime, LocalTime endTime, Member member);
	MeetingRequest getAllByDateAndStartTimeAndEndTimeAndMeetingRequestStateAndMember(LocalDate date, LocalTime startTime, LocalTime endTime, MeetingState state, Member member);
	Iterable<MeetingRequest> findAllByRequestMadeToAndMeetingRequestState(Member member, MeetingState state);
}
