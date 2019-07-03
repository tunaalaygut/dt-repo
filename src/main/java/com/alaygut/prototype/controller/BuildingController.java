package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddBuildingForm; 
import com.alaygut.prototype.service.BuildingService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class BuildingController {

    private BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping("/addBuilding")
    public ModelAndView addBuildingPage(){
        return new ModelAndView("addBuilding", "addBuildingForm", new AddBuildingForm());
    }

    @PostMapping("/addBuilding")
    public String handleAddBuilding(@Valid @ModelAttribute("addBuildingForm") AddBuildingForm addBuildingForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors())
            return null;
        buildingService.addBuilding(addBuildingForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni bina başarıyla oluşturuldu.");
        return "redirect:/addBuilding";
    }

    @GetMapping("/listBuildings")
    public ModelAndView listBuildingsPage(){
        return new ModelAndView("listBuildings", "listBuildings", buildingService.getAllBuildings());
    }

}
