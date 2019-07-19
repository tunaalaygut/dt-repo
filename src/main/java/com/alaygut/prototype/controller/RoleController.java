package com.alaygut.prototype.controller;

import com.alaygut.prototype.dto.AddRoleForm; 
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.service.RightService;
import com.alaygut.prototype.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
public class RoleController {

    RoleService roleService;
    RightService rightService;

    public RoleController(RoleService roleService, RightService rightService) {
        this.roleService = roleService;
        this.rightService = rightService;
    }

    /**
     * Rol ekleme sayfasını ekrana getirir
     */

    @GetMapping("/add/role")
    public ModelAndView addRolePage(){
        return new ModelAndView(
                "addRole",
                "addRoleForm",
                roleService.getAddRoleForm());
    }

    /**
     * Rol ekleme butonun fonksiyonel olmasını sağlar
     * @param addRoleForm Rol DTO'su
     * @param bindingResult
     * @param redirectAttributes Başarılı submitte 
     * @return Yeniden rol ekleme formunu boş olarak döndürür
     */

    @PostMapping("/add/role")
    public String handleAddRole(@Valid @ModelAttribute("addRoleForm") AddRoleForm addRoleForm, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            roleService.fixForm(addRoleForm);
            return "/addRole";
        }
        roleService.addRole(addRoleForm);
        redirectAttributes.addFlashAttribute("successMessage", "Yeni rol başarıyla oluşturuldu.");
        return "redirect:/add/role";
    }

    /**
     * Eklenen ve state'i aktif olan rollerin bir listesini gösterir
     */

    @GetMapping("/list/role")
    public ModelAndView listRolesPage(){
        ModelAndView model = new ModelAndView("listRoles", "listRoles", roleService.getAllActiveRoles());
        model.addObject("idTransfer", new IDTransfer());
        return model;
    }
    
    /**
     * Listedeki her rolün yanındaki deactivate butonunun fonksiyonel olmasını sağlar
     * @param idTransfer Id DTO'su
     * @param bindingResult
     * @return Listeyi deaktive edilen rol eksik şekilde döndürür
     */

    @PostMapping("/list/role")
    public String handleDeactivateRole(@Valid @ModelAttribute("idTransfer") IDTransfer idTransfer, BindingResult bindingResult) {
        roleService.deactivate(idTransfer);
        return "redirect:/list/role";
    }

    /**
     * Rolün yanındaki edit butonunun aktif olmasını sağlar
     * @param id Editlenecek rolün unique Id'si
     * @return Id sahibi rolün edit sayfasını ekrana veriri
     */

    @GetMapping("/edit/role/{id}")
    public ModelAndView editRolePage(@PathVariable Long id) {
        return new ModelAndView("editRole", "addRoleForm", roleService.getEditPage(id));
    }
	
    /**
	 * Edit sayfasındaki submit butonunun aktif olmasını sağlar
	 * @param form Role DTO'su
	 * @param bindingResult
	 * @return Başarılı bir editten sonra yeni hali ile listeyi döndürür
	 */

	@PostMapping("/edit/role/{id}")
	public String submitRoleEdit(@Valid @ModelAttribute("addRoleForm") AddRoleForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            roleService.fixForm(form);
            return "editRole";
        }
        try{
            roleService.edit(form);

        }
        catch (Exception e){
            return "editRole";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Rol başarıyla değiştirildi.");
        return "redirect:/list/role";
	}

}
