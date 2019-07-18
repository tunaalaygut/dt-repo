package com.alaygut.prototype.controller;

import javax.validation.Valid; 

import com.alaygut.prototype.domain.MeetingRoom;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alaygut.prototype.dto.AddMeetingRequestForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.BuildingService;
import com.alaygut.prototype.service.MeetingRequestService;
import com.alaygut.prototype.service.MeetingRequestTimeGenerator;
import com.alaygut.prototype.service.MeetingRoomService;
import com.alaygut.prototype.service.MeetingTypeService;
import com.alaygut.prototype.service.MemberService;

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
		AddMeetingRequestForm addMeetingRequestForm = new AddMeetingRequestForm();
		addMeetingRequestForm.setAllBuildings(buildingService.getAllActiveBuildings());
		addMeetingRequestForm.setAllMeetingRooms(meetingRoomService.getAllActiveRooms());
		addMeetingRequestForm.setAllMeetingTypes(meetingTypeService.getAllActiveTypes());
		addMeetingRequestForm.setTimes(meetingRequestTimeGenerator.generateTimes(8, 18, 5));
		addMeetingRequestForm.setAllMembers(memberService.getAllActiveMembers());

		return new ModelAndView("addMeetingRequest", "addMeetingRequestForm", addMeetingRequestForm);
	}
	
	@PostMapping("/add/meetingRequest")
	public String handleAddMeetingRequest(@Valid @ModelAttribute("addMeetingRequestForm") AddMeetingRequestForm addMeetingRequestForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			addMeetingRequestForm.setAllMeetingRooms(meetingRoomService.getAllRooms());
			addMeetingRequestForm.setAllMembers(memberService.getAllMembers());
			addMeetingRequestForm.setAllMeetingTypes(meetingTypeService.getAllTypes());
			addMeetingRequestForm.setTimes(meetingRequestTimeGenerator.generateTimes(8, 18, 5));
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
		if(bindingResult.hasErrors())
			return null;
		meetingRequestService.deactivate(idTransfer);
		return "redirect:/list/meetingRequest";
	}

	@GetMapping("/getBuildingMeetingRooms")
	public @ResponseBody Map<String, String> getBuildingMeetingRooms(@RequestParam("buildingId") Long buildingId){
		Map<String, String> buildingMeetingRooms = new HashMap<>();

		Iterable<MeetingRoom> meetingRooms = meetingRoomService.getAllInBuilding(buildingId);

		for (MeetingRoom meetingRoom : meetingRooms)
			buildingMeetingRooms.put(String.valueOf(meetingRoom.getMeetingRoomId()), meetingRoom.getMeetingRoomName());

		return buildingMeetingRooms;
	}

	@GetMapping("/addMemberToParticipants/{memberId}")
	public @ResponseBody void addMemberToParticipants(@PathVariable("memberId") Long memberId, @RequestParam("addMeetingRequestForm")AddMeetingRequestForm addMeetingRequestForm){

		addMeetingRequestForm.getAddedMembers().add(memberId);
		System.out.println(
				addMeetingRequestForm.getAddedMembers()
		);

		/*Iterable<MeetingRoom> meetingRooms = meetingRoomService.getAllInBuilding(buildingId);

		for (MeetingRoom meetingRoom : meetingRooms)
			buildingMeetingRooms.put(String.valueOf(meetingRoom.getMeetingRoomId()), meetingRoom.getMeetingRoomName());*/

	}

}
