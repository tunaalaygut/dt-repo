package com.alaygut.prototype.controller;


import com.alaygut.prototype.dto.AddRightForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.RightService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RightController {

    RightService rightService;

    public RightController(RightService rightService) {
        this.rightService = rightService;
    }

    @GetMapping("/add/right")
    public ModelAndView addRightPage(){
        return new ModelAndView("addRight", "addRightForm", new AddRightForm());
    }

    @PostMapping("/add/right")
    public String handleAddRight(@Valid @ModelAttribute("addRightForm") AddRightForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors())
            return "/addRight";
        rightService.addRight(form);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni yetki başarıyla oluşturuldu.");
        return "redirect:/add/right";
    }

    @GetMapping("/list/right")
    public ModelAndView listRightsPage(){
        return new ModelAndView("listRights", "listRights", rightService.getAllActiveRights());
    }

    @PostMapping("/list/right")
    public String handleRightDeactivate(@Valid @ModelAttribute("idTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
    	rightService.deactivate(idTransfer);
    	return "redirect:/list/right";
    }
    @GetMapping("/edit/right/{id}")
    public ModelAndView editRightPage(@PathVariable Long id) {
        return new ModelAndView("editRight", "addRightForm", rightService.getEditForm(id));
    }

    @PostMapping("/edit/right/{id}")
	public String submitRightEdit(@Valid @ModelAttribute("addRightForm") AddRightForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){

            return "editRight";
        }
        try{
            rightService.edit(form);
        }
        catch (Exception e){
            return "editRight";
        }

        return "redirect:/list/right";
    }
    
}
