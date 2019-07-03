package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository; 

import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.domain.RecordState;

public interface MeetingStatusRepository extends CrudRepository<MeetingStatus, Long> {
	Iterable<MeetingStatus> findAllByStateEquals(RecordState state);
}
