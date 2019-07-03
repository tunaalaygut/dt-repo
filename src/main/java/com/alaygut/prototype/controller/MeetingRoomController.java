package com.alaygut.prototype.controller;

import javax.validation.Valid;

import com.alaygut.prototype.service.BuildingService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.service.MeetingRoomService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetingRoomController {
	
	private MeetingRoomService meetingRoomService;
	private BuildingService buildingService;

	public MeetingRoomController(MeetingRoomService meetingRoomService, BuildingService buildingService) {
		this.meetingRoomService = meetingRoomService;
		this.buildingService = buildingService;
	}
	
	@GetMapping("/addMeetingRoom")
	public ModelAndView addMeetingRoomPage() {
		AddMeetingRoomForm addMeetingRoomForm = new AddMeetingRoomForm();
		addMeetingRoomForm.setAllBuildings(buildingService.getAllBuildings());
		return new ModelAndView(
				"addMeetingRoom",
				"addMeetingRoomForm",
				addMeetingRoomForm
		);
	}
	
	@PostMapping("/addMeetingRoom")
	public String handleAddMeetingRoom(@Valid @ModelAttribute("addMeetingRoomForm") AddMeetingRoomForm addMeetingRoomForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			addMeetingRoomForm.setAllBuildings(buildingService.getAllBuildings());
			return "/addMeetingRoom";
		}
		meetingRoomService.addRoom(addMeetingRoomForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı odası başarıyla oluşturuldu.");
		return "redirect:/addMeetingRoom";
	}	
	
	@GetMapping("/listMeetingRooms")
	public ModelAndView listMeetingRoomsPage() {
		return new ModelAndView("listMeetingRooms", "listMeetingRooms", meetingRoomService.getAllRooms());
	}
	
}
