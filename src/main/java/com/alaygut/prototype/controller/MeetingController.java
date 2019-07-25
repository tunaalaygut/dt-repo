package com.alaygut.prototype.controller;

import com.alaygut.prototype.service.MeetingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MeetingController {
    MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/member/meetings")
    public ModelAndView getMemberMeetingsPage(Principal principal){
        return new ModelAndView(
                "memberMeetings",
                "meetingsDetails",
                meetingService.getMemberMeetingDetailsProvider(principal));
    }
}
