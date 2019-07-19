package com.alaygut.prototype.controller;

import javax.validation.Valid;  
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MeetingRoomService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeetingRoomController {
	
	private MeetingRoomService meetingRoomService;

	public MeetingRoomController(MeetingRoomService meetingRoomService) {
		this.meetingRoomService = meetingRoomService;
	}
	
	/**
     * Toplantı Odası ekleme sayfasını ekrana getirir
     */

	@GetMapping("/add/meetingRoom")
	public ModelAndView addMeetingRoomPage() {
		return new ModelAndView(
				"addMeetingRoom",
				"addMeetingRoomForm",
				meetingRoomService.getAddMeetingRoomPage()
		);
	}
	
	/**
     * Oda ekleme butonun fonksiyonel olmasını sağlar
     * @param addMeetingRoomForm Toplantı Odası DTO'su
     * @param bindingResult
     * @param redirectAttributes Başarılı submit mesajı
     * @return Yeniden oda ekleme formunu boş olarak döndürür
     */

	@PostMapping("/add/meetingRoom")
	public String handleAddMeetingRoom(@Valid @ModelAttribute("addMeetingRoomForm") AddMeetingRoomForm addMeetingRoomForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			meetingRoomService.fixAddForm(addMeetingRoomForm);
			return "/addMeetingRoom";
		}
		meetingRoomService.addRoom(addMeetingRoomForm);
		redirectAttributes.addFlashAttribute("successMessage", "Yeni toplantı odası başarıyla oluşturuldu.");
		return "redirect:/add/meetingRoom";
	}	
	
	/**
     * Eklenen ve state'i aktif olan odaların bir listesini gösterir
     */

	@GetMapping("/list/meetingRoom")
	public ModelAndView listMeetingRoomsPage() {
		return new ModelAndView("listMeetingRooms", "listMeetingRooms", meetingRoomService.getAllActiveRooms());
	}
	
	/**
     * Listedeki her odanın yanındaki deactivate butonunun fonksiyonel olmasını sağlar
     * @param idTransfer Id DTO'su
     * @param bindingResult
     * @return Listeyi deaktive edilen oda eksik şekilde döndürür
     */

	@PostMapping("/list/meetingRoom")
	public String handleMeetingRoomDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
		meetingRoomService.deactivate(idTransfer);
		return "redirect:/list/meetingRoom";
	}

	/**
     * Toplantı odasının yanındaki edit butonunun aktif olmasını sağlar
     * @param id Editlenecek odanın unique Id'si
     * @return Id sahibi odanın edit sayfasını ekrana veriri
     */

	@GetMapping("/edit/meetingRoom/{id}")
	public ModelAndView editMeetingRoomPage(@PathVariable Long id) {
		return new ModelAndView("editMeetingRoom", "addMeetingRoomForm", meetingRoomService.getEditPage(id));
	}

	/**
	 * Edit sayfasındaki submit butonunun aktif olmasını sağlar
	 * @param form Toplantı odası DTO'su
	 * @param bindingResult
	 * @return Başarılı bir editten sonra yeni hali ile listeyi döndürür
	 */

	@PostMapping("/edit/meetingRoom/{id}")
	public String submitMeetingRoomEdit(@Valid @ModelAttribute("addMeetingRoomForm") AddMeetingRoomForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
			meetingRoomService.fixAddForm(form);
			return "editMeetingRoom";
		}

		if(meetingRoomService.edit(form))
			redirectAttributes.addFlashAttribute("successMessage", "Toplantı odası başarıyla değiştirildi.");
		return "redirect:/list/meetingRoom";
	}
}
