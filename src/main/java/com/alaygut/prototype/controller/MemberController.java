package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MemberService;
import com.alaygut.prototype.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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
        addMemberForm.setAllRoles(roleService.getAllActiveRoles());
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

    @GetMapping("/edit/member/{id}")
    public ModelAndView editMemberPage(@PathVariable Long id) {
        return new ModelAndView("editMember", "addMemberForm", memberService.getEditForm(id));
    }

    @PostMapping("/edit/member/{id}")
    public String submitMemberEdit(@Valid @ModelAttribute("addMemberForm")AddMemberForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){

            List<ObjectError> error = bindingResult.getAllErrors();

            for (ObjectError e : error){
                System.out.println(e.toString());
            }

            form.setAllRoles(roleService.getAllActiveRoles());
            return "editMember";
        }
        try{
            memberService.edit(form);
        }
        catch (Exception e){
            bindingResult.addError(new FieldError("addMemberForm", "email", "Email ** mevcut."));
            bindingResult.addError(new FieldError("addMemberForm", "username", "Kullanıcı ** adı mevcut."));
            form.setAllRoles(roleService.getAllActiveRoles());
            return "editMember";
        }

        return "redirect:/list/member";
	}

}
