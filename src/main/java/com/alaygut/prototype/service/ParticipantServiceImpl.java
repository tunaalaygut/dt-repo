package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.Participant;
import com.alaygut.prototype.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ParticipantServiceImpl implements ParticipantService {
	
	private ParticipantRepository participantRepository;

	private MemberService memberService;

	public ParticipantServiceImpl(ParticipantRepository participantRepository, MemberService memberService) {
		this.participantRepository = participantRepository;
		this.memberService = memberService;
	}
	
	/**
	 * Katılımcı tablosuna katılımcı ekler
	 * @param participant katılımcı entitysi
	 */
	@Override
	@Transactional(readOnly = false)
	public void addParticipant(Participant participant) {
		participantRepository.save(participant);
	}

	/**
	 * Bütün katılımcıları sırayla döndürür
	 */
	@Override
	public Iterable<Participant> getAllParticipants() {
		return participantRepository.findAll();
	}

	@Override
	public void generateParticipants(List<String> participantDetails, MeetingRequest meetingRequest) {
		for (int i = 0; i < participantDetails.size(); i+=3){
			String fullName, email;
			Member member;

			if (participantDetails.get(i).equals(""))
				member = null;
			else									//member
				member = memberService.getMember(Long.parseLong(participantDetails.get(i)));	//reference to the member

			fullName = participantDetails.get(i+1);
			email = participantDetails.get(i+2);

			Participant participant = new Participant(member, fullName, email, meetingRequest);
			participantRepository.save(participant);
		}
	}

	@Override
	public List<Participant> getAllParticipantsInMeetingRequest(MeetingRequest meetingRequest) {
		return participantRepository.getAllByMeetingRequest(meetingRequest);
	}
}
