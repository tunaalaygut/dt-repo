package com.alaygut.prototype.controller;

import javax.validation.Valid;

import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.domain.RoomFeature;
import com.alaygut.prototype.service.BuildingService;
import com.alaygut.prototype.service.RoomFeatureService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.AddReasonForm;
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
		addMeetingRoomForm.setAllBuildings(buildingService.getAllBuildings());
		addMeetingRoomForm.setAllFeatures(roomFeatureService.getAllFeatures());
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
			addMeetingRoomForm.setAllFeatures(roomFeatureService.getAllFeatures());
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
	
	@PutMapping("/list/meetingRoom")
	public ModelAndView editMeetingRoomPage(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer) {
		return new ModelAndView("editMeetingRoom", "addMeetingRoomForm", meetingRoomService.getEditPage(idTransfer.getRecordId()));
	}
	
	@PostMapping("/edit/meetingRoom")
	public String submitMeetingRoomEdit(@Valid @ModelAttribute("AddMeetingRoomForm") AddMeetingRoomForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		meetingRoomService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/meetingRoom";
	}
	
}
