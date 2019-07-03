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
		
	@GetMapping("/add/meetingRequest")
	public ModelAndView addMeetingRequestPage() {
		AddMeetingRequestForm addMeetingRequestForm = new AddMeetingRequestForm();
		addMeetingRequestForm.setAllMeetingRoom(meetingRoomService.getAllRooms());
		addMeetingRequestForm.setAllMember(memberService.getAllMembers());
		addMeetingRequestForm.setAllMeetingType(meetingTypeService.getAllTypes());
		addMeetingRequestForm.setAllMeetingStatus(meetingStatusService.getAllStatus());
		
		return new ModelAndView("addMeetingRequest", "addMeetingRequestForm", new AddMeetingRequestForm());	
	}
	
	@PostMapping("/add/meetingRequest")
	public String handleAddMeetingRequest(@Valid @ModelAttribute("addMeetingRequestForm") AddMeetingRequestForm addMeetingRequestForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			addMeetingRequestForm.setAllMeetingRoom(meetingRoomService.getAllRooms());
			addMeetingRequestForm.setAllMember(memberService.getAllMembers());
			addMeetingRequestForm.setAllMeetingType(meetingTypeService.getAllTypes());
			addMeetingRequestForm.setAllMeetingStatus(meetingStatusService.getAllStatus());
			return "/add/meetingRequest";
		}		
		meetingRequestService.addRequest(addMeetingRequestForm);
		//redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı talebi başarıyla oluşturuldu.");
		return "redirect:/add/meetingRequest";
	}
	
	@GetMapping("/list/meetingRequest")
	public ModelAndView listMeetingRequestsPage() {
		return new ModelAndView("listMeetingRequests", "listMeetingRequests", meetingRequestService.getAllActiveRequests());
	}
}
