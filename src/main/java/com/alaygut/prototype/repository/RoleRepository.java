package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Iterable<Role> findAllByStateEquals(RecordState state);
	Role findByRoleName (String roleName);
}
