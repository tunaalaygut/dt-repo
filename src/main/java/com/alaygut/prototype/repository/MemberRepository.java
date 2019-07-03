package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.Member; 
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByLoginUsername(String username);
}
