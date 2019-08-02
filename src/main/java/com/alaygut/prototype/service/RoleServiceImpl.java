package com.alaygut.prototype.service;


import com.alaygut.prototype.domain.RecordState;  
import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddRoleForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;
    private RightService rightService;
    private MemberService memberService;

    public RoleServiceImpl(RoleRepository roleRepository, RightService rightService, MemberService memberService) {
        this.roleRepository=roleRepository;
        this.rightService = rightService;
        this.memberService = memberService;
    }

    /**
     * Database'e rol ekleme
     * @param form role DTO
     */

    @Override
    @Transactional(readOnly = false)
    public void addRole(AddRoleForm form) {
        Iterable<Right> selectedRights = rightService.getRights(form.getRightIds());
        Set<Right> rights = new HashSet<>();

        for (Right right:selectedRights){
            rights.add((rightService.getRight(right.getRightId())));
        }

        Role role = new Role(
                form.getRoleName(),
                form.getDescription(),
                rights
        );
        if (form.getCreatorId() != null)
            role.setCreator(memberService.getMember(form.getCreatorId()));

        roleRepository.save(role);
    }

    @Override
    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    /**
     * Stateleri Aktif(1) olan rolleri döndürür
     */

    @Override
    public Iterable<Role> getAllActiveRoles() {
    	return roleRepository.findAllByStateEquals(RecordState.ACTIVE);
    }
    
    /**
     * Spesifik bir rol döndürür
     * @param roleId rolün unique id'si    
     */

    @Override
    public Role getRole(Long roleId) {
    	return roleRepository.findById(roleId).orElse(null);
    }
    
    /**
     * State'i Aktiften(1) Deaktife(0) alır
     * @param idTransfer id transfer objesi
     */

    @Override
    @Transactional(readOnly = false)
    public void deactivate(IDTransfer idTransfer) {
        Role role = roleRepository.findById(idTransfer.getRecordId()).orElse(null);
        role.setState(RecordState.NONACTIVE);
        roleRepository.save(role);
    }
    
    @Override
    @Transactional(readOnly = false)
    public void edit(AddRoleForm addRoleForm) {
    	Role role = roleRepository.findById(addRoleForm.getRecordId()).orElse(null);	
    	role.setRoleName(addRoleForm.getRoleName());
    	role.setDescription(addRoleForm.getDescription());

        Iterable<Right> selectedRights = rightService.getRights(addRoleForm.getRightIds());
        Set<Right> rights = new HashSet<>();

        for (Right right:selectedRights){
            rights.add((rightService.getRight(right.getRightId())));
        }

    	role.setRights(rights);
        role.setUpdater(memberService.getMember(addRoleForm.getUpdaterId()));
    	
    	roleRepository.save(role);
    }

    /**
     * Editlenecek rolün formunu dolu halde getirir
     * @param roleId editlenen rolün Id'si
     * @return dolu role DTO'su
     */

    @Override
    public AddRoleForm getEditPage(Long roleId) {
        AddRoleForm addRoleForm = new AddRoleForm();

        Role role = this.getRole(roleId);
        addRoleForm.setRoleName(role.getRoleName());
        addRoleForm.setDescription(role.getDescription());
        addRoleForm.setRoleRights(this.getAllRights(roleId));
        addRoleForm.setRecordId(role.getRoleId());
        addRoleForm.setAllRights(rightService.getAllActiveRights());

        return addRoleForm;
    }

    /**
     * Bir rolün sahip olduğu tüm hakları döndürü
     * @param roleId aranan rolün unique Id'si
     */
    public Iterable<Right> getAllRights(Long roleId){
        Role role = getRole(roleId);
        return rightService.getAllByRoles(role);
    }

    @Override
    public void fixForm(AddRoleForm form) {
        form.setAllRights(rightService.getAllActiveRights());
        if(form.getRecordId() != null)
            form.setRoleRights(this.getAllRights(form.getRecordId()));
    }

    @Override
    public AddRoleForm getAddRoleForm() {
        AddRoleForm addRoleForm = new AddRoleForm();
        addRoleForm.setAllRights(rightService.getAllActiveRights());
        return addRoleForm;
    }

    @Override
    public Role getRole(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
