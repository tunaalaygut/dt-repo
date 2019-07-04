package com.alaygut.prototype.service;

import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.domain.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    void addMember(AddMemberForm addMemberForm);
    Iterable<Member> getAllMembers();
    Iterable<Member> getAllActiveMembers();
    void deactivate(IDTransfer idTransfer);
}
