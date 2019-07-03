package com.alaygut.prototype.repository;

import org.springframework.data.repository.CrudRepository;

import com.alaygut.prototype.domain.Reason;

public interface ReasonRepository extends CrudRepository<Reason, Long> {
}
