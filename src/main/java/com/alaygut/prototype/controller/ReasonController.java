package com.alaygut.prototype.controller;

import java.util.List;  
import javax.validation.Valid;  
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alaygut.prototype.dto.AddReasonForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.ReasonService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReasonController {

	ReasonService reasonService;

	public ReasonController(ReasonService reasonService) {
		this.reasonService = reasonService;
	}

	/**
     * Sebep ekleme sayfasını ekrana getirir
     */

	@GetMapping("/add/reason")
	public ModelAndView addReasonPage() {
		return new ModelAndView("addReason","addReasonForm", new AddReasonForm());
	}
	
	/**
     * Sebep ekleme butonun fonksiyonel olmasını sağlar
     * @param form Sebep DTO'su
     * @param bindingResult
     * @param redirectAttributes Başarılı submit mesajı
     * @return Yeniden sebep ekleme formunu boş olarak döndürür
     */

	@PostMapping("/add/reason")
	public String handleAddReason(@Valid @ModelAttribute("addReasonForm") AddReasonForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors())
			return "/addReason";
		reasonService.addReason(form);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni sebep başarıyla oluşturuldu.");
		return "redirect:/add/reason";
	}
	
	/**
     * Eklenen ve state'i aktif olan sebeplerin bir listesini gösterir
     */

	@GetMapping("/list/reason")
	public ModelAndView listReasonsPage() {
		return new ModelAndView("listReasons", "listReasons", reasonService.getAllActiveReasons());
	}
	
	/**
     * Listedeki her sebebin yanındaki deactivate butonunun fonksiyonel olmasını sağlar
     * @param idTransfer Id DTO'su
     * @param bindingResult
     * @return Listeyi deaktive edilen sebep eksik şekilde döndürür
     */

	@PostMapping("/list/reason")
	public String handleReasonDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		reasonService.deactivate(idTransfer);
		return "redirect:/list/reason";
	}
	
	/**
     * Sebebin yanındaki edit butonunun aktif olmasını sağlar
     * @param id Editlenecek sebebin unique Id'si
     * @return Id sahibi sebebin edit sayfasını ekrana veriri
     */

	@GetMapping("/edit/reason/{id}")
	public ModelAndView editReasonPage(@PathVariable Long id) {
		return new ModelAndView("editReason", "addReasonForm", reasonService.getEditForm(id));
	}
	
	/**
	 * Edit sayfasındaki submit butonunun aktif olmasını sağlar
	 * @param form Sebep DTO'su
	 * @param bindingResult
	 * @return Başarılı bir editten sonra yeni hali ile listeyi döndürür
	 */

	@PostMapping("/edit/reason/{id}")
	public String submitReasonEdit(@Valid @ModelAttribute("addReasonForm") AddReasonForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
			//reasonService.fixAddForm(form);
			return "editReason";
		}

			if(reasonService.edit(form))
				redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
			return "redirect:/list/reason";
	}
}
