package com.alaygut.prototype.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.dto.AddMeetingStatusForm;
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
	public String handleAddMeetingStatus(@Valid @ModelAttribute("addMeetingStatusForm") AddMeetingStatusForm addMeetingStatusForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			addMeetingStatusForm.setAllReasons(reasonService.getAllReasons());
			return "/add/meetingStatus";
		}
		meetingStatusService.addStatus(addMeetingStatusForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı durumu başarıyla oluşturuldu.");
		return "redirect:/add/meetingStatus";
	}
	
	@GetMapping("/list/meetingStatus")
	public ModelAndView listMeetingStatusPage() {
		return new ModelAndView("listMeetingStatus", "listMeetingStatus", meetingStatusService.getAllStatus());
	}
}