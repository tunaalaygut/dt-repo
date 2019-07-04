package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Login;
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.repository.MemberRepository;
import com.alaygut.prototype.repository.RightRepository;
import com.alaygut.prototype.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private LoginService loginService;

    private PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, RoleRepository roleRepository, LoginService loginService) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.loginService = loginService;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = this.memberRepository.findByLoginUsername(username);

        if (member == null)
            throw new UsernameNotFoundException("User " + username + " not found.");

        return member;
    }
}
