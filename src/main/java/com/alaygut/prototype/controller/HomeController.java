package com.alaygut.prototype.controller;


import com.alaygut.prototype.service.MeetingRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {

    MeetingRequestService meetingRequestService;

    public HomeController(MeetingRequestService meetingRequestService) {
        this.meetingRequestService = meetingRequestService;
    }

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/getNumOfPendingRequets")
    public @ResponseBody int numOfPendingRequets(){
        return meetingRequestService.getNumberOfPendingRequests();
    }

    @GetMapping("/login")
    public String getLoginPage(Authentication authentication){
        if(authentication != null)
            return "index";
        return "login";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "login";
    }






}
