package com.alaygut.prototype.controller;

import java.util.List;

import javax.validation.Valid;  

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;


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
		meetingTypeService.deactivate(idTransfer);
		return "redirect:/list/meetingType";
		
	}
	
	@GetMapping("/edit/meetingType/{id}")
	public ModelAndView editMeetingTypePage(@PathVariable Long id) {
		return new ModelAndView("editMeetingType", "addMeetingTypeForm", meetingTypeService.getEditForm(id));
}
	
	@PostMapping("/edit/meetingType/{id}")
	public String submitMeetingTypeEdit(@Valid @ModelAttribute("addMeetingTypeForm") AddMeetingTypeForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
            List<ObjectError> error = bindingResult.getAllErrors();

            for (ObjectError e : error){
                System.out.println(e.toString());
            }

            return "editMeetingType";
        }
		meetingTypeService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/meetingType";
	}
}
