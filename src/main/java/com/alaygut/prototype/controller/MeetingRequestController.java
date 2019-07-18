package com.alaygut.prototype.controller;

import javax.validation.Valid;

import com.alaygut.prototype.domain.Member;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.BuildingService;
import com.alaygut.prototype.service.MeetingRequestService;
import com.alaygut.prototype.service.MeetingRoomService;
import com.alaygut.prototype.service.MeetingTypeService;
import com.alaygut.prototype.service.MemberService;

import java.security.Principal;
import java.util.*;

@Controller
public class MeetingRequestController {
	private MeetingRequestService meetingRequestService;
	private MeetingRoomService meetingRoomService;
	private MemberService memberService;
	private MeetingTypeService meetingTypeService;
	private BuildingService buildingService;

	public MeetingRequestController(MeetingRequestService meetingRequestService, MeetingRoomService meetingRoomService, MemberService memberService, MeetingTypeService meetingTypeService, BuildingService buildingService) {
		this.meetingRequestService = meetingRequestService;
		this.meetingRoomService = meetingRoomService;
		this.memberService = memberService;
		this.meetingTypeService = meetingTypeService;
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
		return new ModelAndView("listMeetingRequests", "meetingRequestDetailProvider", meetingRequestService.getListMeetingRequestsDetailProvider());
	}
	
	@PostMapping("/list/meetingRequest")
	public String handleMeetingRequestDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		meetingRequestService.deactivate(idTransfer);
		return "redirect:/list/meetingRequest";
	}

	@GetMapping("/list/pendingRequest")
	public ModelAndView listPendingMeetingRequestsPage() {
		return new ModelAndView("pendingRequests", "meetingRequestDetailProvider", meetingRequestService.getPendingMeetingRequestsDetailProvider());
	}

	@GetMapping("/getBuildingMeetingRooms")
	public @ResponseBody Map<String, String> getBuildingMeetingRooms(@RequestParam("buildingId") Long buildingId){
		return meetingRequestService.getBuildingMeetingRooms(buildingId);
	}

	@GetMapping("/getMeetingRoomCapacity")
	public @ResponseBody int getMeetingRoomCapacity(@RequestParam("meetingRoomId") Long meetingRoomId){
		return meetingRoomService.getMeetingRoom(meetingRoomId).getCapacity();
	}

	@PostMapping("/accept/meetingRequest/{meetingRequestId}")
	public String acceptMeetingRequest(@PathVariable Long meetingRequestId, Principal principal){
		Member supervisor = memberService.getMember(principal.getName());
		meetingRequestService.acceptMeetingRequest(meetingRequestId, supervisor.getMemberId());
		return "redirect:/list/pendingRequest";
	}

	@PostMapping("/decline/meetingRequest/{meetingRequestId}")
	public String declineMeetingRequest(@PathVariable Long meetingRequestId, Principal principal){
		Member supervisor = memberService.getMember(principal.getName());
		meetingRequestService.declineMeetingRequest(meetingRequestId, supervisor.getMemberId());
		return "redirect:/list/pendingRequest";
	}

	@GetMapping("/member/meetingRequest")
	public ModelAndView getMemberMeetingRequestsPage(Principal principal){
		Member member = memberService.getMember(principal.getName());
		return new ModelAndView("listMeetingRequests", "meetingRequestDetailProvider", meetingRequestService.getMemberMeetingRequestDetailsProvider(member));
	}

	@GetMapping("/getGridData")
	public @ResponseBody Map<String, String> getMeetingRoomCapacity(@RequestParam("date") String date, @RequestParam("meetingRoomId") String meetingRoomId){
		Long roomId = Long.parseLong(meetingRoomId);
		return meetingRequestService.getGridData(date, roomId);
	}

}
