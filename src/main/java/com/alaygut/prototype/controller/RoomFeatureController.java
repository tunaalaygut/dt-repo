package com.alaygut.prototype.controller;


import com.alaygut.prototype.domain.MeetingRoom;
import com.alaygut.prototype.domain.Reason;
import com.alaygut.prototype.domain.RoomFeature;
import com.alaygut.prototype.dto.AddMeetingRoomForm;
import com.alaygut.prototype.dto.AddReasonForm;
import com.alaygut.prototype.dto.AddRoomFeatureForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MeetingRoomService;
import com.alaygut.prototype.service.RoomFeatureService;
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
public class RoomFeatureController {
    private RoomFeatureService roomFeatureService;
    private MeetingRoomService meetingRoomService;


    public RoomFeatureController(RoomFeatureService roomFeatureService, MeetingRoomService meetingRoomService) {
        this.roomFeatureService = roomFeatureService;
        this.meetingRoomService = meetingRoomService;
    }

    @GetMapping("/add/roomFeature")
    public ModelAndView addRoomFeaturePage() {
        return new ModelAndView(
                "addRoomFeature",
                "addRoomFeatureForm",
                new AddRoomFeatureForm()
        );
    }

    @PostMapping("/add/roomFeature")
    public String handleAddRoomFeature(@Valid @ModelAttribute("addRoomFeatureForm") AddRoomFeatureForm addRoomFeatureForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors())
            return "/addRoomFeature";
        roomFeatureService.addRoomFeature(addRoomFeatureForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni özellik başarıyla oluşturuldu.");
        return "redirect:/add/roomFeature";
    }

    @GetMapping("/list/roomFeature")
    public ModelAndView listRoomFeaturesPage(){
        return new ModelAndView("listRoomFeatures", "listRoomFeatures", roomFeatureService.getAllActiveRoomFeatures());
    }


    @PostMapping("/list/roomFeature")
    public String handleRoomFeatureDeactivate(@Valid @ModelAttribute("idTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return null;
        roomFeatureService.deactivate(idTransfer);
        return "redirect:/list/roomFeature";
    }
    
    @PutMapping("/list/roomFeature")
	public ModelAndView editRoomFeaturePage(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer) {
		return new ModelAndView("editRoomFeature", "addRoomFeatureForm", roomFeatureService.getEditForm(idTransfer.getRecordId()));
	}
	
	@PostMapping("/edit/roomFeature")
	public String submitRoomFeatureEdit(@Valid @ModelAttribute("AddRoomFeatureForm") AddRoomFeatureForm form, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return null;
		roomFeatureService.edit(form);
		//redirectAttributes.addFlashAttribute("successMessage", "Sebep başarıyla değiştirildi.");
		return "redirect:/list/roomFeature";
	}
}
