package com.alaygut.prototype.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.domain.MeetingStatus;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.dto.AddMeetingStatusForm;
import com.alaygut.prototype.dto.AddReasonForm;
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
	public String handleAddMeetingStatus(@Valid @ModelAttribute("addMeetingStatusForm") AddMeetingStatusForm addMeetingStatusForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
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
		if(bindingResult.hasErrors())
			return null;
		meetingStatusService.deactivate(idTransfer);
		return "redirect:/list/meetingStatus";
	}
	
	@PutMapping("/list/meetingStatus")
	public ModelAndView editReasonPage(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		AddMeetingStatusForm addMeetingStatusForm = new AddMeetingStatusForm();
		
		MeetingStatus meetingStatus = meetingStatusService.getMeetingStatus(idTransfer.getRecordId());
		addMeetingStatusForm.setMeetingStatusName(meetingStatus.getMeetingStatusName());
		addMeetingStatusForm.setReasonId(meetingStatus.getReason().getReasonId());
		addMeetingStatusForm.setRecordId(meetingStatus.getMeetingStatusId());
		addMeetingStatusForm.setAllReasons(reasonService.getAllActiveReasons());
		
		return new ModelAndView("editMeetingStatus", "addMeetingStatusForm", addMeetingStatusForm);
	}
	
	@PostMapping("/edit/meetingStatus")
	public String submitReasonEdit(@Valid @ModelAttribute("AddMeetingStatusForm") AddMeetingStatusForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		meetingStatusService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/meetingStatus";
	}
}