package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Participant;

public interface ParticipantService {
	void addParticipant(Participant participant);
	Iterable<Participant> getAllParticipants();
}
