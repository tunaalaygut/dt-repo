package com.alaygut.prototype.controller;

import javax.validation.Valid; 

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.dto.AddReasonForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.ReasonService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReasonController {

	ReasonService reasonService;

	public ReasonController(ReasonService reasonService) {
		this.reasonService = reasonService;
	}

	@GetMapping("/add/reason")
	public ModelAndView addReasonPage() {
		return new ModelAndView("addReason","addReasonForm", new AddReasonForm());
	}
	
	@PostMapping("/add/reason")
	public String handleAddReason(@Valid @ModelAttribute("addReasonForm") AddReasonForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors())
			return "/addReason";
		reasonService.addReason(form);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni sebep başarıyla oluşturuldu.");
		return "redirect:/add/reason";
	}
	
	@GetMapping("/list/reason")
	public ModelAndView listReasonsPage() {
		return new ModelAndView("listReasons", "listReasons", reasonService.getAllActiveReasons());
	}
	
	@PostMapping("/list/reason")
	public String handleReasonDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		reasonService.deactivate(idTransfer);
		return "redirect:/list/reason";
	}
	
	@PutMapping("/list/reason")
	public ModelAndView editReasonPage(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		AddReasonForm addReasonForm = new AddReasonForm();
		
		Reason reason = reasonService.getReason(idTransfer.getRecordId());
		addReasonForm.setReasonName(reason.getReasonName());
		addReasonForm.setDescription(reason.getDescription());
		addReasonForm.setRecordId(reason.getReasonId());
		
		return new ModelAndView("editReason", "addReasonForm", addReasonForm);
	}
	
	@PostMapping("/edit/reason")
	public String submitReasonEdit(@Valid @ModelAttribute("AddReasonForm") AddReasonForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		reasonService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/reason";
	}
}
