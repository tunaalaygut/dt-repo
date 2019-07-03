package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.MeetingRequest;
import com.alaygut.prototype.domain.RecordState;

public interface MeetingRequestRepository extends CrudRepository<MeetingRequest, Long> {
	Iterable<MeetingRequest> findAllByStateEquals(RecordState state);
}
