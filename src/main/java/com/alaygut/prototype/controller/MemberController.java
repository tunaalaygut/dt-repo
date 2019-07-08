package com.alaygut.prototype.controller;

import com.alaygut.prototype.domain.Member;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.AddReasonForm;
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
            addMemberForm.setAllRoles(roleService.getAllActiveRoles());
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
    
    @PutMapping("/list/member")
    public ModelAndView editMemberPage(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
    	AddMemberForm addMemberForm = new AddMemberForm();
		
		Member member = memberService.getMember(idTransfer.getRecordId());

		addMemberForm.setFirstName(member.getFirstName());
		addMemberForm.setLastName(member.getLastName());
		addMemberForm.setEmail(member.getEmail());
		addMemberForm.setPhone(member.getPhone());
		addMemberForm.setRoleId(member.getRole().getRoleId());
		addMemberForm.setRecordId(member.getMemberId());
		addMemberForm.setAllRoles(roleService.getAllActiveRoles());
		
		return new ModelAndView("editmember", "addMemberForm", addMemberForm);
    }
    
    @PostMapping("/edit/member")
    public String submitMemberEdit(@Valid @ModelAttribute("AddMemberForm") AddMemberForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		
		memberService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/member";
	}

}
