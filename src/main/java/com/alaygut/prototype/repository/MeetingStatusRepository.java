package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository; 

import com.alaygut.prototype.domain.MeetingStatus;

public interface MeetingStatusRepository extends CrudRepository<MeetingStatus, Long> {
}
