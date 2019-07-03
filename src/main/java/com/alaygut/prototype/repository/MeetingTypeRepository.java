package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.MeetingType;

public interface MeetingTypeRepository extends CrudRepository<MeetingType, Long> {
}
