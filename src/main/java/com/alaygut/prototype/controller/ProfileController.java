package com.alaygut.prototype.controller;

import org.springframework.stereotype.Controller;   
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.alaygut.prototype.domain.Member;
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
    public ModelAndView getProfilePage(Principal principal){
    	Member member = memberService.getMember(principal.getName());        
        return new ModelAndView("profile", "member", member);
    }
    
    @GetMapping("/profile/edit")
    public ModelAndView editProfilePage(Principal principal) {
    	return new ModelAndView("editProfile", "addMemberForm", memberService.getEditForm(memberService.getMember(principal.getName()).getMemberId()));	
    }
    
    @PostMapping("/profile/edit")
    public String submitProfileEdit(@Valid @ModelAttribute("addMemberForm") AddMemberForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			memberService.fixForm(form);
			return "editProfile";
		}
		try {
			memberService.profileEdit(form);
		}
		catch(Exception e){
			memberService.addErrors(form, bindingResult);
            memberService.fixForm(form);
            return "editProfile";
		}
		redirectAttributes.addFlashAttribute("successMessage", "Profil başarıyla güncellendi.");
		return "redirect:/profile";
	}
}
