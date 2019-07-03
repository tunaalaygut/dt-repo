package com.alaygut.prototype.controller;

import javax.validation.Valid; 

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.dto.AddMeetingTypeForm;
import com.alaygut.prototype.service.MeetingTypeService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetingTypeController {
	 
	private MeetingTypeService meetingTypeService;

	public MeetingTypeController(MeetingTypeService meetingTypeService) {
		this.meetingTypeService = meetingTypeService;
	}
	
	@GetMapping("/addMeetingType")
	public ModelAndView addMeetingTypePage() {
		return new ModelAndView("addMeetingType", "addMeetingTypeForm", new AddMeetingTypeForm());
	}
	
	@PostMapping("/addMeetingType")
	public String handleAddMeetingType(@Valid @ModelAttribute("addMeetingTypeForm") AddMeetingTypeForm addMeetingTypeForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors())
			return null;
		meetingTypeService.addType(addMeetingTypeForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı türü başarıyla oluşturuldu.");
		return "redirect:/addMeetingType";
	}
	
	@GetMapping("/listMeetingTypes")
	public ModelAndView listMeetingTypesPage() {
		return new ModelAndView("listMeetingTypes", "listMeetingTypes", meetingTypeService.getAllTypes());
	}
}
