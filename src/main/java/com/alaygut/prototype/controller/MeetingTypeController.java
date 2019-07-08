package com.alaygut.prototype.controller;

import javax.validation.Valid; 

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.MeetingType;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.AddMeetingTypeForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MeetingTypeService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetingTypeController {
	 
	private MeetingTypeService meetingTypeService;

	public MeetingTypeController(MeetingTypeService meetingTypeService) {
		this.meetingTypeService = meetingTypeService;
	}
	
	@GetMapping("/add/meetingType")
	public ModelAndView addMeetingTypePage() {
		return new ModelAndView("addMeetingType", "addMeetingTypeForm", new AddMeetingTypeForm());
	}
	
	@PostMapping("/add/meetingType")
	public String handleAddMeetingType(@Valid @ModelAttribute("addMeetingTypeForm") AddMeetingTypeForm addMeetingTypeForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors())
			return "/addMeetingType";
		meetingTypeService.addType(addMeetingTypeForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı türü başarıyla oluşturuldu.");
		return "redirect:/add/meetingType";
	}
	
	@GetMapping("/list/meetingType")
	public ModelAndView listMeetingTypesPage() {
		return new ModelAndView("listMeetingTypes", "listMeetingTypes", meetingTypeService.getAllActiveTypes());
	}
	
	@PostMapping("/list/meetingType")
	public String handleMeetingTypeDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		meetingTypeService.deactivate(idTransfer);
		return "redirect:/list/meetingType";
		
	}
	
	@PutMapping("/list/meetingType")
	public ModelAndView editMeetingTypePage(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		AddMeetingTypeForm addMeetingTypeForm = new AddMeetingTypeForm();
		
		MeetingType meetingType = meetingTypeService.getMeetingType(idTransfer.getRecordId());
		addMeetingTypeForm.setMeetingTypeName(meetingType.getMeetingTypeName());
		addMeetingTypeForm.setDescription(meetingType.getDescription());
		addMeetingTypeForm.setRecordId(meetingType.getMeetingTypeId());
		
		return new ModelAndView("editMeetingType", "addMeetingTypeForm", addMeetingTypeForm);
	}
	
	@PostMapping("/edit/meetingType")
	public String submitMeetingTypeEdit(@Valid @ModelAttribute("AddMeetingTypeForm") AddMeetingTypeForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		meetingTypeService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/meetingType";
	}
}
