package com.alaygut.prototype.controller;

import org.springframework.stereotype.Controller; 
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MemberService;


import java.security.Principal;

import javax.validation.Valid;

@Controller
public class ProfileController {
	
	private MemberService memberService;


    public ProfileController(MemberService memberService) {
        this.memberService = memberService;
    }

	
    @GetMapping("/profile")
    public String getProfilePage(Principal principal){
        return "/profile";
    }
    
    @PutMapping("/profile")
    public ModelAndView editProfilePage(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer) {
		return new ModelAndView("editProfile", "addMemberForm", memberService.getEditForm(idTransfer.getRecordId()));
    }
    
    @PostMapping("/profile/edit")
    public String submitProfileEdit(@Valid @ModelAttribute("AddMemberForm") AddMemberForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return null;
		}
			
		
		memberService.profileEdit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/profile";
	}
}
