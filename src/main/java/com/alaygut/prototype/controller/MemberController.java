package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MemberService;
import com.alaygut.prototype.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class MemberController {

    private MemberService memberService;
    private RoleService roleService;

    public MemberController(MemberService memberService, RoleService roleService) {
        this.memberService = memberService;
        this.roleService = roleService;
    }

    @GetMapping("/add/member")
    public ModelAndView addMemberPage(){
        AddMemberForm addMemberForm = new AddMemberForm();
        addMemberForm.setAllRoles(roleService.getAllRoles());
        return new ModelAndView(
                "addMember",
                "addMemberForm",
                addMemberForm
        );
    }

    @PostMapping("/add/member")
    public String handleAddMember(@Valid @ModelAttribute("addMemberForm") AddMemberForm addMemberForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            addMemberForm.setAllRoles(roleService.getAllRoles());
            return "/addMember";
        }
        memberService.addMember(addMemberForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni kullanıcı başarıyla oluşturuldu.");
        return "redirect:/add/member";
    }

    @GetMapping("/list/member")
    public ModelAndView listMembersPage(){
        return new ModelAndView("listMembers", "listMembers", memberService.getAllActiveMembers());
    }
    
    @PostMapping("/list/member")
    public String handleMemberDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
    	if(bindingResult.hasErrors())
    		return null;
    	memberService.deactivate(idTransfer);
    	return "redirect:/list/member";
    }

}
