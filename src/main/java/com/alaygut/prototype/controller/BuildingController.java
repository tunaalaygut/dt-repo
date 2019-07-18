package com.alaygut.prototype.controller;


import com.alaygut.prototype.dto.AddBuildingForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.BuildingService;


import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
public class BuildingController  {

    private BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    /**
     * Bina ekleme sayfasını ekrana getirir
     */
    @GetMapping("/add/building")
    public ModelAndView addBuildingPage(){
        return new ModelAndView("addBuilding", "addBuildingForm", new AddBuildingForm());
    }

    /**
     * Bina ekleme butonun fonksiyonel olmasını sağlar
     * @param addBuildingForm Bina DTO'su
     * @param bindingResult
     * @param redirectAttributes Başarılı ekleme mesajı için
     * @return Yeniden bina ekleme formunu boş olarak döndürür
     */
    @PostMapping("/add/building")
    public String handleAddBuilding(@Valid @ModelAttribute("addBuildingForm") AddBuildingForm addBuildingForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors())
            return "/addBuilding";
        buildingService.addBuilding(addBuildingForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni bina başarıyla oluşturuldu.");
        return "redirect:/add/building";
    }

    /**
     * Eklenen ve state'i aktif olan binaların bir listesini gösterir
     */
    @GetMapping("/list/building")
    public ModelAndView listBuildingsPage(){
        ModelAndView model = new ModelAndView("listBuildings", "listBuildings", buildingService.getAllActiveBuildings());
        model.addObject("idTransfer", new IDTransfer());
        return model;
    }

    /**
     * Listedeki her binanın yanındaki deactivate butonunun fonksiyonel olmasını sağlar
     * @param idTransfer Id transfer DTO'su
     * @param bindingResult
     * @return Listeyi deaktive edilen bina eksik şekilde döndürür
     */
    @PostMapping("/list/building")
    public String handleDeactivateBuilding(@Valid @ModelAttribute("idTransfer") IDTransfer idTransfer, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return null;
        buildingService.deactivate(idTransfer);
        return "redirect:/list/building";
    }

    /**
     * Binanın yanındaki edit butonunun aktif olmasını sağlar
     * @param id editlenecek binanın unique Id'si
     * @return Id sahibi binanın edit sayfasını ekrana veriri
     */
	@GetMapping("/edit/building/{id}")
    public ModelAndView editBuildingPage(@PathVariable Long id) {
        return new ModelAndView("editBuilding", "addBuildingForm", buildingService.getEditForm(id));
    }

	/**
	 * Edit sayfasındaki submit butonunun aktif olmasını sağlar
	 * @param form building DTO
	 * @param bindingResult
	 * @return Başarılı bir editten sonra yeni hali ile listeyi döndürür
	 */
    @PostMapping("/edit/building/{id}")
    public String submitBuildingEdit(@Valid @ModelAttribute("addBuildingForm") AddBuildingForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "editBuilding";
        }
        try{
            buildingService.edit(form);
        }
        catch (Exception e){
            bindingResult.addError(new FieldError("addBuildingForm", "buildingName", "Bina adı mevcut."));
            return "editBuilding";
        }

        return "redirect:/list/building";
    }
}
