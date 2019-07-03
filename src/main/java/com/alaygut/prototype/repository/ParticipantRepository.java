package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.Participant;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {

}
