package com.alaygut.prototype.controller;


import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.BuildingService;
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
public class BuildingController  {

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

	@GetMapping("/edit/building/{id}")
    public ModelAndView editBuildingPage(@PathVariable Long id) {
        return new ModelAndView("editBuilding", "addBuildingForm", buildingService.getEditForm(id));
    }

    @PostMapping("/edit/building/{id}")
    public String submitBuildingEdit(@Valid @ModelAttribute("addBuildingForm") AddBuildingForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "editBuilding";
        }
        try{
            buildingService.edit(form);
            redirectAttributes.addFlashAttribute("successMessage", "Bina başarıyla değiştirildi.");
            return "redirect:/list/building";
        }
        catch (Exception e){
            bindingResult.addError(new FieldError("addBuildingForm", "buildingName", "{buildingName.not.unique}"));
            return "editBuilding";
        }
    }
}
