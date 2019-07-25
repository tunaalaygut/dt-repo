package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.Participant;

import java.util.List;

public interface ParticipantService {
	void addParticipant(Participant participant);
	Iterable<Participant> getAllParticipants();
	void generateParticipants(List<String> participantDetails, MeetingRequest meetingRequest);
	List<Participant> getAllParticipantsInMeetingRequest(MeetingRequest meetingRequest);
	List<Participant> getAllByMember(Member member);
}
