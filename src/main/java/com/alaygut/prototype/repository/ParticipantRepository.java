package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.Member;
import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.Participant;

import java.util.List;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
    List<Participant> getAllByMeetingRequest(MeetingRequest meetingRequest);
    List<Participant> getAllByMember(Member member);
}
