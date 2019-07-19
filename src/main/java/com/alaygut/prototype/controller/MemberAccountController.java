package com.alaygut.prototype.controller;

import com.alaygut.prototype.domain.ConfirmationToken;  
import com.alaygut.prototype.domain.Login;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.ResetPasswordDTO;
import com.alaygut.prototype.repository.ConfirmationTokenRepository;
import com.alaygut.prototype.repository.LoginRepository;
import com.alaygut.prototype.repository.MemberRepository;
import com.alaygut.prototype.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberAccountController {

    private MemberRepository memberRepository;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;
    private PasswordEncoder passwordEncoder;

    private LoginRepository loginRepository;

    public MemberAccountController(MemberRepository memberRepository, ConfirmationTokenRepository confirmationTokenRepository, EmailSenderService emailSenderService, LoginRepository loginRepository) {
        this.memberRepository = memberRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
        this.loginRepository = loginRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/forgot-password")
    public ModelAndView displayResetPassword(Member member) {
        return new ModelAndView("forgotPassword", "member", member);
    }

    // Receive the address and send an email
    @PostMapping("/forgot-password")
    public ModelAndView forgotUserPassword(ModelAndView modelAndView, Member member, RedirectAttributes redirectAttributes) {
        Member existingMember = memberRepository.findByEmail(member.getEmail());

        if (existingMember != null) {
            // Create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingMember);
            // Save it
            confirmationTokenRepository.save(confirmationToken);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingMember.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("alpotomail@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://localhost:8080/confirm-reset?token="+confirmationToken.getConfirmationToken());

            // Send the email
            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("successMessage", "Şifre değiştirme talebiniz alındı.Şifre yenileme linki için e-mail adresinizi kontrol edin.");
            modelAndView.setViewName("login");

        } else {
            modelAndView.addObject("passwordErrorMessage", "E-mail adresi bulunamadı.");
            modelAndView.setViewName("forgotPassword");
        }
        return modelAndView;
    }

    // Endpoint to confirm the token
    @RequestMapping(value="/confirm-reset", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token")String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
            resetPasswordDTO.setEmail(token.getMember().getEmail());
            modelAndView.addObject("resetPasswordDTO", resetPasswordDTO);
            modelAndView.setViewName("resetPassword");
        } else {
            modelAndView.addObject("errorMessage", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    // Endpoint to update a user's password
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView resetUserPassword(ModelAndView modelAndView, ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("resetPassword");
        }
        if (resetPasswordDTO.getEmail() != null) {
            Member tokenMember = memberRepository.findByEmail(resetPasswordDTO.getEmail());
            Login login = tokenMember.getLogin();
            login.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
            loginRepository.save(login);
            modelAndView.addObject("successMessage", "Şifreniz başarıyla yenilendi.Yeni şifrenizle giriş yapabilirsiniz.");
            modelAndView.setViewName("login");
        } else {
            modelAndView.addObject("errorMessage","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }


}
