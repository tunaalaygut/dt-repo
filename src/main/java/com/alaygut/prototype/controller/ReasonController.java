package com.alaygut.prototype.controller;

import java.util.List;

import javax.validation.Valid;  

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;


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
		reasonService.deactivate(idTransfer);
		return "redirect:/list/reason";
	}
	
	@GetMapping("/edit/reason/{id}")
	public ModelAndView editReasonPage(@PathVariable Long id) {
		return new ModelAndView("editReason", "addReasonForm", reasonService.getEditForm(id));
	}
	
	@PostMapping("/edit/reason/{id}")
	public String submitReasonEdit(@Valid @ModelAttribute("addReasonForm") AddReasonForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
            List<ObjectError> error = bindingResult.getAllErrors();

            for (ObjectError e : error){
                System.out.println(e.toString());
            }

            return "editReason";
        }
		reasonService.edit(form);
		redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/reason";
	}
}
