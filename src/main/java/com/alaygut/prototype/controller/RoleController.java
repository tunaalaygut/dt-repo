package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddRoleForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.RightService;
import com.alaygut.prototype.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RoleController {

    RoleService roleService;
    RightService rightService;

    public RoleController(RoleService roleService, RightService rightService) {
        this.roleService = roleService;
        this.rightService = rightService;
    }

    @GetMapping("/add/role")
    public ModelAndView addRolePage(){
        AddRoleForm addRoleForm = new AddRoleForm();
        addRoleForm.setAllRights(rightService.getAllRights());
        return new ModelAndView(
                "addRole",
                "addRoleForm",
                addRoleForm);
    }

    @PostMapping("/add/role")
    public String handleAddRole(@Valid @ModelAttribute("addRoleForm") AddRoleForm addRoleForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            addRoleForm.setAllRights(rightService.getAllRights());
            return "/addRole";
        }
        roleService.addRole(addRoleForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni rol başarıyla oluşturuldu.");
        return "redirect:/add/role";
    }

    @GetMapping("/list/role")
    public ModelAndView listRolesPage(){
        ModelAndView model = new ModelAndView("listRoles", "listRoles", roleService.getAllActiveRoles());
        model.addObject("idTransfer", new IDTransfer());
        return model;
    }
    
    @PostMapping("/list/role")
    public String handleDeactivateRole(@Valid @ModelAttribute("idTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
    	if (bindingResult.hasErrors())
            return null;
        roleService.deactivate(idTransfer);
        return "redirect:/list/role";
    }

}
