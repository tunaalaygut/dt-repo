package com.alaygut.prototype.repository;

import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.Role;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByLoginUsername(String username);
    Iterable<Member> findByStateEquals(RecordState state);
    boolean existsByLoginUsername(String username);
    boolean existsByEmail(String email);
    List<Member> getAllByRole(Role role);
}
