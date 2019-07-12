package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddRoomFeatureForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MeetingRoomService;
import com.alaygut.prototype.service.RoomFeatureService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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
        roomFeatureService.deactivate(idTransfer);
        return "redirect:/list/roomFeature";
    }
    @GetMapping("/edit/roomFeature/{id}")
    public ModelAndView editRoomFeaturePage(@PathVariable Long id) {
        return new ModelAndView("editRoomFeature", "addRoomFeatureForm",roomFeatureService.getEditForm(id));
    }

    @PostMapping("/edit/roomFeature/{id}")
	public String submitRoomFeatureEdit(@Valid @ModelAttribute("addRoomFeatureForm") AddRoomFeatureForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<ObjectError> error = bindingResult.getAllErrors();
            for (ObjectError e : error){
                System.out.println(e.toString());
            }
            return "editRoomFeature";
        }
        try{
            roomFeatureService.edit(form);
        }
        catch (Exception e){
            return "editRoomFeature";
        }
        return "redirect:/list/roomFeature";
    }
}
