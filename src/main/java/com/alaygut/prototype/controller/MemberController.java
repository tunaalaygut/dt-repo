package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddMemberForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.MemberService;
import com.alaygut.prototype.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MemberController {

    private MemberService memberService;
    private RoleService roleService;

    public MemberController(MemberService memberService, RoleService roleService) {
        this.memberService = memberService;
        this.roleService = roleService;
    }

    /**
     * Üye ekleme sayfasını ekrana getirir
     */

    @GetMapping("/add/member")
    public ModelAndView addMemberPage(){
        return new ModelAndView(
                "addMember",
                "addMemberForm",
                memberService.getAddMemberForm()
        );
    }

    /**
     * Üye ekleme butonun fonksiyonel olmasını sağlar
     * @param addMemberForm Üye DTO'su
     * @param bindingResult
     * @param redirectAttributes Başarılı submit mesajı
     * @return Yeniden üye ekleme formunu boş olarak döndürür
     */

    @PostMapping("/add/member")
    public String handleAddMember(@Valid @ModelAttribute("addMemberForm") AddMemberForm addMemberForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            memberService.fixForm(addMemberForm);
            return "/addMember";
        }
        memberService.addMember(addMemberForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni kullanıcı başarıyla oluşturuldu.");
        return "redirect:/add/member";
    }

    /**
     * Eklenen ve state'i aktif olan üyelerin bir listesini gösterir
     */

    @GetMapping("/list/member")
    public ModelAndView listMembersPage(){
        return new ModelAndView("listMembers", "listMembers", memberService.getAllActiveMembers());
    }
    
    /**
     * Listedeki her üyenin yanındaki deactivate butonunun fonksiyonel olmasını sağlar
     * @param idTransfer Id DTO'su
     * @param bindingResult
     * @return Listeyi deaktive edilen üye eksik şekilde döndürür
     */

    @PostMapping("/list/member")
    public String handleMemberDeactivate(@Valid @ModelAttribute("IDTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
    	memberService.deactivate(idTransfer);
    	return "redirect:/list/member";
    }

    /**
     * Üyenin yanındaki edit butonunun aktif olmasını sağlar
     * @param id Editlenecek üyenin unique Id'si
     * @return Id sahibi üyenin edit sayfasını ekrana veriri
     */

    @GetMapping("/edit/member/{id}")
    public ModelAndView editMemberPage(@PathVariable Long id) {
        return new ModelAndView(
                "editMember",
                "addMemberForm",
                memberService.getEditForm(id));
    }

    /**
	 * Edit sayfasındaki submit butonunun aktif olmasını sağlar
	 * @param form Üye DTO'su
	 * @param bindingResult
	 * @return Başarılı bir editten sonra yeni hali ile listeyi döndürür
	 */

    @PostMapping("/edit/member/{id}")
    public String submitMemberEdit(@Valid @ModelAttribute("addMemberForm")AddMemberForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            memberService.fixForm(form);
            return "editMember";
        }
        try{
            memberService.edit(form);
        }
        catch (Exception e){
            memberService.addErrors(form, bindingResult);
            memberService.fixForm(form);
            return "editMember";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Kullanıcı başarıyla değiştirildi.");
        return "redirect:/list/member";
	}

}
