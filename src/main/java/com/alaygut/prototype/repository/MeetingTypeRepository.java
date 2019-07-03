package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.domain.RecordState;

public interface MeetingTypeRepository extends CrudRepository<MeetingType, Long> {
	Iterable<MeetingType> findAllByStateEquals(RecordState state);
}
