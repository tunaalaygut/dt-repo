package com.alaygut.prototype.controller;

import javax.validation.Valid;

import com.alaygut.prototype.service.BuildingService;
import com.alaygut.prototype.service.RoomFeatureService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MeetingRoomService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetingRoomController {
	
	private MeetingRoomService meetingRoomService;
	private BuildingService buildingService;
	private RoomFeatureService roomFeatureService;

	public MeetingRoomController(MeetingRoomService meetingRoomService, BuildingService buildingService, RoomFeatureService roomFeatureService) {
		this.meetingRoomService = meetingRoomService;
		this.buildingService = buildingService;
		this.roomFeatureService = roomFeatureService;
	}
	
	@GetMapping("/add/meetingRoom")
	public ModelAndView addMeetingRoomPage() {
		AddMeetingRoomForm addMeetingRoomForm = new AddMeetingRoomForm();
		addMeetingRoomForm.setAllBuildings(buildingService.getAllActiveBuildings());
		addMeetingRoomForm.setAllFeatures(roomFeatureService.getAllActiveRoomFeatures());
		return new ModelAndView(
				"addMeetingRoom",
				"addMeetingRoomForm",
				addMeetingRoomForm
		);
	}
	
	@PostMapping("/add/meetingRoom")
	public String handleAddMeetingRoom(@Valid @ModelAttribute("addMeetingRoomForm") AddMeetingRoomForm addMeetingRoomForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			addMeetingRoomForm.setAllBuildings(buildingService.getAllActiveBuildings());
			addMeetingRoomForm.setAllFeatures(roomFeatureService.getAllActiveRoomFeatures());
			return "/addMeetingRoom";
		}
		meetingRoomService.addRoom(addMeetingRoomForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı odası başarıyla oluşturuldu.");
		return "redirect:/add/meetingRoom";
	}	
	
	@GetMapping("/list/meetingRoom")
	public ModelAndView listMeetingRoomsPage() {
		return new ModelAndView("listMeetingRooms", "listMeetingRooms", meetingRoomService.getAllActiveRooms());
	}
	
	@PostMapping("/list/meetingRoom")
	public String handleMeetingRoomDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		meetingRoomService.deactivate(idTransfer);
		return "redirect:/list/meetingRoom";
	}

	@GetMapping("/edit/meetingRoom/{id}")
	public ModelAndView editMeetingRoomPage(@PathVariable Long id) {
		return new ModelAndView("editMeetingRoom", "addMeetingRoomForm", meetingRoomService.getEditPage(id));
	}

	@PostMapping("/edit/meetingRoom/{id}")
	public String submitMeetingRoomEdit(@Valid @ModelAttribute("addMeetingRoomForm") AddMeetingRoomForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
			form.setAllBuildings(buildingService.getAllActiveBuildings());
			form.setAllFeatures(roomFeatureService.getAllActiveRoomFeatures());
			return "editMeetingRoom";
		}

		meetingRoomService.edit(form);
		redirectAttributes.addFlashAttribute("successMessage", "Toplantı odası başarıyla değiştirildi.");
		return "redirect:/edit/meetingRoom/" + form.getRecordId() ;
	}
}
