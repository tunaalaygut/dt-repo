package com.alaygut.prototype.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;


import com.alaygut.prototype.dto.AddMeetingStatusForm;

import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MeetingStatusService;
import com.alaygut.prototype.service.ReasonService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetingStatusController {
	
	private MeetingStatusService meetingStatusService;
	private ReasonService reasonService;

	public MeetingStatusController(MeetingStatusService meetingStatusService, ReasonService reasonService) {
		this.meetingStatusService = meetingStatusService;
		this.reasonService = reasonService;
	}
	
	/**
     * Toplantı durumu ekleme sayfasını ekrana getirir
     */

	@GetMapping("/add/meetingStatus")
	public ModelAndView addMeetingStatusPage() {
		AddMeetingStatusForm addMeetingStatusForm = new AddMeetingStatusForm();
		addMeetingStatusForm.setAllReasons(reasonService.getAllReasons());
		ModelAndView modelAndView = new ModelAndView(
				"addMeetingStatus",
				"addMeetingStatusForm",
				addMeetingStatusForm
		);
		return modelAndView;
	}
	
	/**
     * Toplantı durumu ekleme butonun fonksiyonel olmasını sağlar
     * @param addMeetingStatusForm Toplantı durumu DTO'su
     * @param bindingResult
     * @param redirectAttributes Başarılı submit mesajı
     * @return Yeniden toplantı durumu ekleme formunu boş olarak döndürür
     */

	@PostMapping("/add/meetingStatus")
	public String handleAddMeetingStatus(@Valid @ModelAttribute("addMeetingStatusForm") AddMeetingStatusForm addMeetingStatusForm, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			addMeetingStatusForm.setAllReasons(reasonService.getAllActiveReasons());
			return "/addMeetingStatus";
		}
		meetingStatusService.addStatus(addMeetingStatusForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı durumu başarıyla oluşturuldu.");
		return "redirect:/add/meetingStatus";
	}
	
	/**
     * Eklenen ve state'i aktif olan toplantı durumlarının bir listesini gösterir
     */

	@GetMapping("/list/meetingStatus")
	public ModelAndView listMeetingStatusPage() {
		return new ModelAndView("listMeetingStatus", "listMeetingStatus", meetingStatusService.getAllActiveStatus());
	}
	
	/**
     * Listedeki her toplantı türünün yanındaki deactivate butonunun fonksiyonel olmasını sağlar
     * @param idTransfer Id DTO'su
     * @param bindingResult
     * @return Listeyi deaktive edilen toplantı türü eksik şekilde döndürür
     */

	@PostMapping("/list/meetingStatus")
	public String handleMeetingStatusDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		meetingStatusService.deactivate(idTransfer);
		return "redirect:/list/meetingStatus";
	}
	
	/**
     * Toplantı durumunun yanındaki edit butonunun aktif olmasını sağlar
     * @param id Editlenecek durumun unique Id'si
     * @return Id sahibi toplantı durumunun edit sayfasını ekrana veriri
     */

	@GetMapping("/edit/meetingStatus/{id}")
	public ModelAndView editMeetingStatusPage(@PathVariable Long id) {
		return new ModelAndView("editMeetingStatus", "addMeetingStatusForm", meetingStatusService.getEditForm(id));
	}
	
	/**
	 * Edit sayfasındaki submit butonunun aktif olmasını sağlar
	 * @param form
	 * @param bindingResult
	 * @return Başarılı bir editten sonra yeni hali ile listeyi döndürür
	 */

	@PostMapping("/edit/meetingStatus/{id}")
	public String submitReasonEdit(@Valid @ModelAttribute("addMeetingStatusForm") AddMeetingStatusForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
           meetingStatusService.fixForm(form);
            return "editMeetingStatus";
        }
		meetingStatusService.edit(form);
		redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/meetingStatus";
	}
}