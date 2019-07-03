package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.MeetingRequest;

public interface MeetingRequestRepository extends CrudRepository<MeetingRequest, Long> {
}
