package com.alaygut.prototype.controller;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.AddReasonForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.BuildingService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class BuildingController {

    private BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping("/add/building")
    public ModelAndView addBuildingPage(){
        return new ModelAndView("addBuilding", "addBuildingForm", new AddBuildingForm());
    }

    @PostMapping("/add/building")
    public String handleAddBuilding(@Valid @ModelAttribute("addBuildingForm") AddBuildingForm addBuildingForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors())
            return "/addBuilding";
        buildingService.addBuilding(addBuildingForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni bina başarıyla oluşturuldu.");
        return "redirect:/add/building";
    }

    @GetMapping("/list/building")
    public ModelAndView listBuildingsPage(){
        ModelAndView model = new ModelAndView("listBuildings", "listBuildings", buildingService.getAllActiveBuildings());
        model.addObject("idTransfer", new IDTransfer());
        return model;
    }

    @PostMapping("/list/building")
    public String handleDeactivateBuilding(@Valid @ModelAttribute("idTransfer") IDTransfer idTransfer, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return null;
        buildingService.deactivate(idTransfer);
        return "redirect:/list/building";
    }
    
    @PutMapping("/list/building")
	public ModelAndView editBuildingPage(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer) {
		return new ModelAndView("editBuilding", "addBuildingForm", buildingService.getEditForm(idTransfer.getRecordId()));
	}
	
	@PostMapping("/edit/building")
	public String submitBuildingEdit(@Valid @ModelAttribute("AddBuildingForm") AddBuildingForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		buildingService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/building";
	}

}
