package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.domain.RecordState;

public interface ReasonRepository extends CrudRepository<Reason, Long> {
	Iterable<Reason> findAllByStateEquals(RecordState state);
}
