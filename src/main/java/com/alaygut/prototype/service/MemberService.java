package com.alaygut.prototype.service;

import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.domain.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    void addMember(AddMemberForm addMemberForm);
    Iterable<Member> getAllMembers();
    Iterable<Member> getAllActiveMembers();
    Member getMember(Long memberId);
    void deactivate(IDTransfer idTransfer);
    void edit(AddMemberForm addMemberForm);
    AddMemberForm getEditForm(Long memberId);
    void profileEdit(AddMemberForm addMemberForm);
}
