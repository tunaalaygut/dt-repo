package com.alaygut.prototype.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;


import com.alaygut.prototype.dto.AddMeetingStatusForm;

import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MeetingStatusService;
import com.alaygut.prototype.service.ReasonService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetingStatusController {
	
	private MeetingStatusService meetingStatusService;
	private ReasonService reasonService;

	public MeetingStatusController(MeetingStatusService meetingStatusService, ReasonService reasonService) {
		this.meetingStatusService = meetingStatusService;
		this.reasonService = reasonService;
	}
	
	@GetMapping("/add/meetingStatus")
	public ModelAndView addMeetingStatusPage() {
		AddMeetingStatusForm addMeetingStatusForm = new AddMeetingStatusForm();
		addMeetingStatusForm.setAllReasons(reasonService.getAllReasons());
		ModelAndView modelAndView = new ModelAndView(
				"addMeetingStatus",
				"addMeetingStatusForm",
				addMeetingStatusForm
		);
		return modelAndView;
	}
	
	@PostMapping("/add/meetingStatus")
	public String handleAddMeetingStatus(@Valid @ModelAttribute("addMeetingStatusForm") AddMeetingStatusForm addMeetingStatusForm, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			addMeetingStatusForm.setAllReasons(reasonService.getAllActiveReasons());
			return "/addMeetingStatus";
		}
		meetingStatusService.addStatus(addMeetingStatusForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı durumu başarıyla oluşturuldu.");
		return "redirect:/add/meetingStatus";
	}
	
	@GetMapping("/list/meetingStatus")
	public ModelAndView listMeetingStatusPage() {
		return new ModelAndView("listMeetingStatus", "listMeetingStatus", meetingStatusService.getAllActiveStatus());
	}
	
	@PostMapping("/list/meetingStatus")
	public String handleMeetingStatusDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		meetingStatusService.deactivate(idTransfer);
		return "redirect:/list/meetingStatus";
	}
	
	@GetMapping("/edit/meetingStatus/{id}")
	public ModelAndView editMeetingStatusPage(@PathVariable Long id) {
		return new ModelAndView("editMeetingStatus", "addMeetingStatusForm", meetingStatusService.getEditForm(id));
	}
	
	@PostMapping("/edit/meetingStatus/{id}")
	public String submitReasonEdit(@Valid @ModelAttribute("addMeetingStatusForm") AddMeetingStatusForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
           meetingStatusService.fixForm(form);
            return "editMeetingStatus";
        }
		meetingStatusService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/meetingStatus";
	}
}