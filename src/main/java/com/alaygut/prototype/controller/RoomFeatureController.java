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

    /**
     * Oda özelliği ekleme sayfasını ekrana getirir
     */

    @GetMapping("/add/roomFeature")
    public ModelAndView addRoomFeaturePage() {
        return new ModelAndView(
                "addRoomFeature",
                "addRoomFeatureForm",
                new AddRoomFeatureForm()
        );
    }

    /**
     * Oda özelliği ekleme butonun fonksiyonel olmasını sağlar
     * @param addRoomFeatureForm Oda özelliği DTO'su
     * @param bindingResult
     * @param redirectAttributes Başarılı submit mesajı
     * @return Yeniden oda özelliği ekleme formunu boş olarak döndürür
     */

    @PostMapping("/add/roomFeature")
    public String handleAddRoomFeature(@Valid @ModelAttribute("addRoomFeatureForm") AddRoomFeatureForm addRoomFeatureForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors())
            return "/addRoomFeature";
        roomFeatureService.addRoomFeature(addRoomFeatureForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni özellik başarıyla oluşturuldu.");
        return "redirect:/add/roomFeature";
    }

    /**
     * Eklenen ve state'i aktif olan oda özelliklerinin bir listesini gösterir
     */

    @GetMapping("/list/roomFeature")
    public ModelAndView listRoomFeaturesPage(){
        return new ModelAndView("listRoomFeatures", "listRoomFeatures", roomFeatureService.getAllActiveRoomFeatures());
    }

    /**
     * Listedeki her özelliğin yanındaki deactivate butonunun fonksiyonel olmasını sağlar
     * @param idTransfer Id DTO'su
     * @param bindingResult
     * @return Listeyi deaktive edilen özellik eksik şekilde döndürür
     */

    @PostMapping("/list/roomFeature")
    public String handleRoomFeatureDeactivate(@Valid @ModelAttribute("idTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
        roomFeatureService.deactivate(idTransfer);
        return "redirect:/list/roomFeature";
    }
    
    /**
     * Özelliğin yanındaki edit butonunun aktif olmasını sağlar
     * @param id Editlenecek oda özelliğinin unique Id'si
     * @return Id sahibi özelliğin edit sayfasını ekrana veriri
     */

    @GetMapping("/edit/roomFeature/{id}")
    public ModelAndView editRoomFeaturePage(@PathVariable Long id) {
        return new ModelAndView("editRoomFeature", "addRoomFeatureForm",roomFeatureService.getEditForm(id));
    }

    /**
	 * Edit sayfasındaki submit butonunun aktif olmasını sağlar
	 * @param form Oda özelliği DTO'su
	 * @param bindingResult
	 * @return Başarılı bir editten sonra yeni hali ile listeyi döndürür
	 */

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
