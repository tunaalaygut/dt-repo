package com.alaygut.prototype.controller;


import com.alaygut.prototype.service.MeetingRequestService;
import com.alaygut.prototype.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class HomeController {

    MeetingRequestService meetingRequestService;
    MemberService memberService;

    public HomeController(MeetingRequestService meetingRequestService, MemberService memberService) {
        this.meetingRequestService = meetingRequestService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/getNumOfPendingRequests")
    public @ResponseBody int numOfPendingRequests(){
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
