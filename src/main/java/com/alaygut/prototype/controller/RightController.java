package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddRightForm; 
import com.alaygut.prototype.service.RightService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RightController {

    RightService rightService;

    public RightController(RightService rightService) {
        this.rightService = rightService;
    }

    @GetMapping("/addRight")
    public ModelAndView addRightPage(){
        return new ModelAndView("addRight", "addRightForm", new AddRightForm());
    }

    @PostMapping("/addRight")
    public String handleAddRight(@Valid @ModelAttribute("addRightForm") AddRightForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors())
            return null;
        rightService.addRight(form);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni yetki başarıyla oluşturuldu.");
        return "redirect:/addRight";
    }

    @GetMapping("listRights")
    public ModelAndView listRightsPage(){
        return new ModelAndView("listRights", "listRights", rightService.getAllRights());
    }



}