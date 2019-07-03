package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Participant;
import com.alaygut.prototype.repository.ParticipantRepository;

public class ParticipantServiceImpl implements ParticipantService {
	
	private ParticipantRepository participantRepository;

	public ParticipantServiceImpl(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}
	
	@Override
	public void addParticipant(Participant participant) {
		participantRepository.save(participant);
	}

	@Override
	public Iterable<Participant> getAllParticipants() {
		return participantRepository.findAll();
	}

}
