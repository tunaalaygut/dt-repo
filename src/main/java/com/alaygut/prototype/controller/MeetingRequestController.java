package com.alaygut.prototype.controller;

import javax.validation.Valid;

import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;

import java.util.*;

@Controller
public class MeetingRequestController {
	private MeetingRequestService meetingRequestService;
	private MeetingRoomService meetingRoomService;
	private MemberService memberService;
	private MeetingTypeService meetingTypeService;
	private MeetingRequestTimeGenerator meetingRequestTimeGenerator;
	private BuildingService buildingService;

	public MeetingRequestController(MeetingRequestService meetingRequestService, MeetingRoomService meetingRoomService, MemberService memberService, MeetingTypeService meetingTypeService, MeetingRequestTimeGenerator meetingRequestTimeGenerator, BuildingService buildingService) {
		this.meetingRequestService = meetingRequestService;
		this.meetingRoomService = meetingRoomService;
		this.memberService = memberService;
		this.meetingTypeService = meetingTypeService;
		this.meetingRequestTimeGenerator = meetingRequestTimeGenerator;
		this.buildingService = buildingService;
	}

	@GetMapping("/add/meetingRequest")
	public ModelAndView addMeetingRequestPage() {
		return new ModelAndView(
				"addMeetingRequest",
				"addMeetingRequestForm",
				meetingRequestService.getAddMeetingRequestForm());
	}
	
	@PostMapping("/add/meetingRequest")
	public String handleAddMeetingRequest(@Valid @ModelAttribute("addMeetingRequestForm") AddMeetingRequestForm addMeetingRequestForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			meetingRequestService.setExternalData(addMeetingRequestForm);
			return "/addMeetingRequest";
		}
		meetingRequestService.addRequest(addMeetingRequestForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı talebi başarıyla oluşturuldu.");
		return "redirect:/add/meetingRequest";
	}
	
	@GetMapping("/list/meetingRequest")
	public ModelAndView listMeetingRequestsPage() {
		return new ModelAndView("listMeetingRequests", "listMeetingRequests", meetingRequestService.getAllActiveRequests());
	}
	
	@PostMapping("/list/meetingRequest")
	public String handleMeetingRequestDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		meetingRequestService.deactivate(idTransfer);
		return "redirect:/list/meetingRequest";
	}

	@GetMapping("/getBuildingMeetingRooms")
	public @ResponseBody Map<String, String> getBuildingMeetingRooms(@RequestParam("buildingId") Long buildingId){
		return meetingRequestService.getBuildingMeetingRooms(buildingId);
	}

	@GetMapping("/getMeetingRoomCapacity")
	public @ResponseBody int getMeetingRoomCapacity(@RequestParam("meetingRoomId") Long meetingRoomId){
		return meetingRoomService.getMeetingRoom(meetingRoomId).getCapacity();
	}


}
