package com.alaygut.prototype.controller;


import com.alaygut.prototype.dto.AddRightForm; 
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.RightService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class RightController {

    RightService rightService;

    public RightController(RightService rightService) {
        this.rightService = rightService;
    }

    /**
     * Yetki ekleme sayfasını ekrana getirir
     */

    @GetMapping("/add/right")
    public ModelAndView addRightPage(){
        return new ModelAndView("addRight", "addRightForm", new AddRightForm());
    }

    /**
     * Yetki ekleme butonun fonksiyonel olmasını sağlar
     * @param form Yetki DTO'su
     * @param bindingResult
     * @param redirectAttributes Başarılı submit mesajı
     * @return Yeniden yetki ekleme formunu boş olarak döndürür
     */

    @PostMapping("/add/right")
    public String handleAddRight(@Valid @ModelAttribute("addRightForm") AddRightForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors())
            return "/addRight";
        rightService.addRight(form);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni yetki başarıyla oluşturuldu.");
        return "redirect:/add/right";
    }

    /**
     * Eklenen ve state'i aktif olan yetkilerin bir listesini gösterir
     */

    @GetMapping("/list/right")
    public ModelAndView listRightsPage(){
        return new ModelAndView("listRights", "listRights", rightService.getAllActiveRights());
    }

    /**
     * Listedeki her yetkinin yanındaki deactivate butonunun fonksiyonel olmasını sağlar
     * @param idTransfer Id DTO'su
     * @param bindingResult
     * @return Listeyi deaktive edilen yetki eksik şekilde döndürür
     */

    @PostMapping("/list/right")
    public String handleRightDeactivate(@Valid @ModelAttribute("idTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
    	rightService.deactivate(idTransfer);
    	return "redirect:/list/right";
    }
    
    /**
     * Yetkinin yanındaki edit butonunun aktif olmasını sağlar
     * @param id Editlenecek yetkinin unique Id'si
     * @return Id sahibi yetkinin edit sayfasını ekrana veriri
     */

    @GetMapping("/edit/right/{id}")
    public ModelAndView editRightPage(@PathVariable Long id) {
        return new ModelAndView("editRight", "addRightForm", rightService.getEditForm(id));
    }

    /**
	 * Edit sayfasındaki submit butonunun aktif olmasını sağlar
	 * @param form Yetki DTO'su
	 * @param bindingResult
	 * @return Başarılı bir editten sonra yeni hali ile listeyi döndürür
	 */

    @PostMapping("/edit/right/{id}")
	public String submitRightEdit(@Valid @ModelAttribute("addRightForm") AddRightForm form, BindingResult bindingResult,  RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){

            return "editRight";
        }
        try{
            rightService.edit(form);
        }
        catch (Exception e){
            return "editRight";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Yetki başarıyla değiştirildi.");
        return "redirect:/list/right";
    }
    
}
