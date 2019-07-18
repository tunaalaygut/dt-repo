package com.alaygut.prototype.service;

import org.springframework.stereotype.Service; 

import com.alaygut.prototype.domain.Login;
<<<<<<< HEAD
import com.alaygut.prototype.repository.LoginRepository;

=======
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.repository.LoginRepository;
import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> 88a5f7e0967aa42ebeb02dd4a9dd65cb1bed5cf0

@Service
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService {
    private LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
/**
 * Kullanıcı login bilgilerini login tablosuna ekler
 * @param login login entitysi
 */
    @Override
    @Transactional(readOnly = false)
    public void addLogin(Login login) {
        loginRepository.save(login);
    }


}
