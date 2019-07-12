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

	public MeetingRoomController(MeetingRoomService meetingRoomService) {
		this.meetingRoomService = meetingRoomService;
	}
	
	@GetMapping("/add/meetingRoom")
	public ModelAndView addMeetingRoomPage() {
		return new ModelAndView(
				"addMeetingRoom",
				"addMeetingRoomForm",
				meetingRoomService.getAddMeetingRoomPage()
		);
	}
	
	@PostMapping("/add/meetingRoom")
	public String handleAddMeetingRoom(@Valid @ModelAttribute("addMeetingRoomForm") AddMeetingRoomForm addMeetingRoomForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			meetingRoomService.fixAddForm(addMeetingRoomForm);
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
			meetingRoomService.fixAddForm(form);
			return "editMeetingRoom";
		}

		meetingRoomService.edit(form);
		redirectAttributes.addFlashAttribute("successMessage", "Toplantı odası başarıyla değiştirildi.");
		return "redirect:/list/meetingRoom";
	}
}
