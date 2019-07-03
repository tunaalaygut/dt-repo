package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddMemberForm; 
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

    @GetMapping("/addMember")
    public ModelAndView addMemberPage(){
        AddMemberForm addMemberForm = new AddMemberForm();
        addMemberForm.setAllRoles(roleService.getAllRoles());
        return new ModelAndView(
                "addMember",
                "addMemberForm",
                addMemberForm
        );
    }

    @PostMapping("/addMember")
    public String handleAddMember(@Valid @ModelAttribute("addMemberForm") AddMemberForm addMemberForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            addMemberForm.setAllRoles(roleService.getAllRoles());
            return "/addMember";
        }
        memberService.addMember(addMemberForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni kullanıcı başarıyla oluşturuldu.");
        return "redirect:/addMember";
    }

    @GetMapping("/listMembers")
    public ModelAndView listMembersPage(){
        return new ModelAndView("listMembers", "listMembers", memberService.getAllMembers());
    }

}
