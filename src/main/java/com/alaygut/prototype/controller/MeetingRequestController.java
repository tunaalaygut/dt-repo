package com.alaygut.prototype.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.service.MeetingRequestService;
import com.alaygut.prototype.service.MeetingRoomService;
import com.alaygut.prototype.service.MeetingStatusService;
import com.alaygut.prototype.service.MeetingTypeService;
import com.alaygut.prototype.service.MemberService;

@Controller
public class MeetingRequestController {
	private MeetingRequestService meetingRequestService;
	private MeetingRoomService meetingRoomService;
	private MemberService memberService;
	private MeetingTypeService meetingTypeService;
	private MeetingStatusService meetingStatusService;

	public MeetingRequestController(MeetingRequestService meetingRequestService, MeetingRoomService meetingRoomService, 
	MemberService memberService, MeetingTypeService meetingTypeService, MeetingStatusService meetingStatusService) {
		this.meetingRequestService = meetingRequestService;
		this.meetingRoomService = meetingRoomService;
		this.memberService = memberService;
		this.meetingTypeService = meetingTypeService;
		this.meetingStatusService = meetingStatusService;
	}
		
	@GetMapping("/addMeetingRequest")
	public ModelAndView addMeetingRequestPage() {
		ModelAndView modelAndView = new ModelAndView("addMeetingRequest", "addMeetingRequestForm", new AddMeetingRequestForm());
		modelAndView.addObject("allMeetingRooms", meetingRoomService.getAllRooms());
		modelAndView.addObject("allMembers", memberService.getAllMembers());
		modelAndView.addObject("allMeetingTypes", meetingTypeService.getAllTypes());
		modelAndView.addObject("allMeetingStatus", meetingStatusService.getAllStatus());
		
		return modelAndView;	
	}
	
	@PostMapping("/addMeetingRequest")
	public String handleAddMeetingRequest(@Valid @ModelAttribute("addMeetingRequestForm") AddMeetingRequestForm addMeetingRequestForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		meetingRequestService.addRequest(addMeetingRequestForm);
		return "redirect:/addMeetingRequest";
	}
	
	@GetMapping("/listMeetingRequests")
	public ModelAndView listMeetingRequestsPage() {
		return new ModelAndView("listMeetingRequests", "listMeetingRequests", meetingRequestService.getAllRequests());
	}
}
