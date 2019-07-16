package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Participant;
import com.alaygut.prototype.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ParticipantServiceImpl implements ParticipantService {
	
	private ParticipantRepository participantRepository;

	public ParticipantServiceImpl(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void addParticipant(Participant participant) {
		participantRepository.save(participant);
	}

	@Override
	public Iterable<Participant> getAllParticipants() {
		return participantRepository.findAll();
	}

}
