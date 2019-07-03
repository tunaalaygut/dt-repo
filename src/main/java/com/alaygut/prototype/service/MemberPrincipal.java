package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemberPrincipal implements UserDetails {

    private Member member;

    public MemberPrincipal(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.member.getRole().getRights().forEach(right -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(right.getRightName());
            authorities.add(authority);
        });

        GrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + this.member.getRole().getRoleName());
        authorities.add(role);

        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getLogin().getPassword();
    }

    @Override
    public String getUsername() {
        return member.getLogin().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
