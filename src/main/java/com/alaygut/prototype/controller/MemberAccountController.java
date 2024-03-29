package com.alaygut.prototype.controller;

import com.alaygut.prototype.domain.ConfirmationToken;   
import com.alaygut.prototype.domain.Login;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.ResetPasswordDTO;
import com.alaygut.prototype.repository.ConfirmationTokenRepository;
import com.alaygut.prototype.repository.LoginRepository;
import com.alaygut.prototype.repository.MemberRepository;
import com.alaygut.prototype.service.EmailSenderService;
import com.alaygut.prototype.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import javax.validation.Valid;


@Controller
public class MemberAccountController {

    private MemberRepository memberRepository;
    private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;
    private PasswordEncoder passwordEncoder;
    private MemberService memberService;
    private LoginRepository loginRepository;

    public MemberAccountController(MemberRepository memberRepository, ConfirmationTokenRepository confirmationTokenRepository, EmailSenderService emailSenderService, LoginRepository loginRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailSenderService = emailSenderService;
        this.loginRepository = loginRepository;
        this.memberService = memberService;
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

    @GetMapping("/change-password")
    public ModelAndView displayChangePassword(Principal principal) {
        return new ModelAndView("changePassword", "member", memberService.getMember(principal.getName()));
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
            mailMessage.setSubject("Dijital Toplantı Şifre Yenileme Talebi");
            mailMessage.setFrom("dijital.toplanti@gmail.com");
            mailMessage.setText("Dijital Toplantı hesabınızın şifresini yenilemeyi talep ettiniz. Yenileme işlemini tamamlamak için lütfen bu linke tıklayın: "
                    + "http://localhost:8080/confirm-reset?token=" + confirmationToken.getConfirmationToken() + "\nBöyle bir işlem talep etmediyseniz bu maili dikkate almayın.");

            // Send the email
            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("successMessage", "Şifre değiştirme talebiniz alındı. Şifre yenileme linki için e-mail adresinizi kontrol edin.");
            modelAndView.setViewName("login");

        } else {
            modelAndView.addObject("passwordErrorMessage", "E-mail adresi bulunamadı.");
            modelAndView.setViewName("forgotPassword");
        }
        return modelAndView;
    }

    // Receive the address and send an email
    @PostMapping("/change-password")
    public ModelAndView changeUserPassword(ModelAndView modelAndView, Member member, RedirectAttributes redirectAttributes) {
        Member existingMember = memberRepository.findByEmail(member.getEmail());

        if (existingMember != null) {
        	
            // Create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingMember);
            //Make sure User has 
            // Save it
            confirmationTokenRepository.save(confirmationToken);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingMember.getEmail());
            mailMessage.setSubject("Dijital Toplantı Şifre Yenileme Talebi");
            mailMessage.setFrom("dijital.toplanti@gmail.com");
            mailMessage.setText("Dijital Toplantı hesabınızın şifresini yenilemeyi talep ettiniz. Yenileme işlemini tamamlamak için lütfen bu linke tıklayın: "
                    + "http://localhost:8080/confirm-reset?token=" + confirmationToken.getConfirmationToken() + "\nBöyle bir işlem talep etmediyseniz bu maili dikkate almayın.");

            // Send the email
            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("successMessage", "Şifre değiştirme talebiniz alındı. Şifre yenileme linki için e-mail adresinizi kontrol edin.");
            modelAndView.setViewName("login");

        } else {
            modelAndView.addObject("passwordErrorMessage", "E-mail adresi bulunamadı.");
            modelAndView.setViewName("changePassword");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/confirm-reset", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken, BindingResult bindingResult) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
            resetPasswordDTO.setEmail(token.getMember().getEmail());
            modelAndView.addObject("resetPasswordDTO", resetPasswordDTO);
            modelAndView.setViewName("resetPassword");
            confirmationTokenRepository.delete(token);
        } else
            modelAndView.setViewName("login");

        return modelAndView;
    }

    
    @PostMapping("/reset-password")
    public String resetUserPassword(@Valid @ModelAttribute("resetPasswordDTO") ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            return "resetPassword";
        }

        Member tokenMember = memberRepository.findByEmail(resetPasswordDTO.getEmail());
        Login login = tokenMember.getLogin();
        login.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        loginRepository.save(login);
        redirectAttributes.addFlashAttribute("successMessage", "Şifreniz başarıyla yenilendi. Yeni şifrenizle giriş yapabilirsiniz.");
        return "redirect:/login";
    }


}
