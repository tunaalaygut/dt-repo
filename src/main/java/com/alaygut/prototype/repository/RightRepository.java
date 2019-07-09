package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RightRepository extends CrudRepository<Right, Long> {
	Iterable<Right> findAllByStateEquals(RecordState state);
	Iterable<Right> findAllByRoles(Role role);
}
