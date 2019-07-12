package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Login; 
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.repository.LoginRepository;
import com.alaygut.prototype.repository.MemberRepository;

import com.alaygut.prototype.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private LoginService loginService;
    private RoleService roleService;


    private PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, RoleRepository roleRepository, LoginService loginService, RoleService roleService) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.loginService = loginService;
        this.roleService = roleService;
    }

    @Override
    public void addMember(AddMemberForm form) {
        Optional<Role> role = roleRepository.findById(form.getRoleId());
        Login login = new Login(
                form.getUsername(),
                passwordEncoder.encode(form.getPassword())
        );
        Member member = new Member(
                form.getFirstName(),
                form.getLastName(),
                form.getEmail(),
                form.getPhone(),
                role.orElse(null),
                login
        );
        loginService.addLogin(login);
        if (form.getCreatorId() != null)
            member.setCreator(memberRepository.findById(form.getCreatorId()).orElse(null));
        memberRepository.save(member);
    }

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    @Override
    public Iterable<Member> getAllActiveMembers() {
    	return memberRepository.findByStateEquals(RecordState.ACTIVE);
    }
    
    @Override
    public Member getMember(Long memberId) {
    	return memberRepository.findById(memberId).orElse(null);
    }
    
    @Override
    public void deactivate(IDTransfer idTransfer) {
    	Member member = memberRepository.findById(idTransfer.getRecordId()).orElse(null);
    	member.setState(RecordState.NONACTIVE);
    	memberRepository.save(member);
    }
    
    @Override
    public void edit(AddMemberForm addMemberForm) {

    	Member member = memberRepository.findById(addMemberForm.getRecordId()).orElse(null);
    	Role role = roleRepository.findById(addMemberForm.getRoleId()).orElse(null);
    	Login login = member.getLogin();
    	member.setFirstName(addMemberForm.getFirstName());
    	member.setLastName(addMemberForm.getLastName());
        member.setPhone(addMemberForm.getPhone());
        member.setRole(role);

        if (!addMemberForm.getNewUsername().equals(member.getUsername()))
            login.setUsername(addMemberForm.getNewUsername());

        if (!addMemberForm.getNewEmail().equals(member.getEmail()) )
            member.setEmail(addMemberForm.getNewEmail());

    	//login.setPassword(addMemberForm.getPassword());
    	//login.setPassword(passwordEncoder.encode(addMemberForm.getPassword()));
        member.setUpdater(memberRepository.findById(addMemberForm.getUpdaterId()).orElse(null));

        loginService.addLogin(login);
    	memberRepository.save(member);
    }

    @Override
    public AddMemberForm getEditForm(Long memberId) {
        Member member = getMember(memberId);
        AddMemberForm addMemberForm = new AddMemberForm();

        addMemberForm.setRecordId(member.getMemberId());
        addMemberForm.setFirstName(member.getFirstName());
        addMemberForm.setLastName(member.getLastName());
        addMemberForm.setEmail(member.getEmail());
        addMemberForm.setPhone(member.getPhone());
        addMemberForm.setUsername(member.getUsername());
        addMemberForm.setPassword(member.getPassword());
        addMemberForm.setRoleId(member.getRole().getRoleId());
        addMemberForm.setAllRoles(roleService.getAllActiveRoles());
        return addMemberForm;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository.findByLoginUsername(username);

        if (member == null)
            throw new UsernameNotFoundException("User " + username + " not found.");

        return member;
    }

    public Member LoadUserEmail(String email) {
        Member member = this.memberRepository.findByEmail(email);
        return member;
    }


}
