package com.alaygut.prototype.service;

import org.springframework.stereotype.Service;   
import com.alaygut.prototype.domain.Login;
import com.alaygut.prototype.repository.LoginRepository;
import org.springframework.transaction.annotation.Transactional;


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
