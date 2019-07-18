package com.alaygut.prototype.controller;


import com.alaygut.prototype.service.MeetingRequestService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    MeetingRequestService meetingRequestService;

    public HomeController(MeetingRequestService meetingRequestService) {
        this.meetingRequestService = meetingRequestService;
    }

    @GetMapping("/")
    public ModelAndView getIndex(){
        return new ModelAndView("index", "numOfPendingRequests", meetingRequestService.getNumberOfPendingRequests());
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return "login";
    }

}
