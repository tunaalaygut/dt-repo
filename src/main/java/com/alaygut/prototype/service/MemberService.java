package com.alaygut.prototype.service;

import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.domain.Member;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

public interface MemberService extends UserDetailsService {
    void addMember(AddMemberForm addMemberForm);
    Iterable<Member> getAllMembers();
    Iterable<Member> getAllActiveMembers();
    Member getMember(Long memberId);
    void deactivate(IDTransfer idTransfer);
    void edit(AddMemberForm addMemberForm);
    AddMemberForm getEditForm(Long memberId);
    void profileEdit(AddMemberForm addMemberForm);
    AddMemberForm getAddMemberForm();
    void fixForm(AddMemberForm addMemberForm);
    void addErrors(AddMemberForm addMemberForm, BindingResult bindingResult);
    boolean usernameExists(String username);
    boolean emailExists(String email);
    Member getMember(String username);
}
