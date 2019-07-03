package com.alaygut.prototype.service;

import org.springframework.stereotype.Service;

import com.alaygut.prototype.domain.Login; 
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.repository.LoginRepository;
import com.alaygut.prototype.repository.MemberRepository;

@Service
public class LoginServiceImpl implements LoginService {
    private LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void addLogin(Login login) {
        loginRepository.save(login);
    }
}
