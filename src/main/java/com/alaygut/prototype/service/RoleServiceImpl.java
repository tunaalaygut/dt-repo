package com.alaygut.prototype.service;


import com.alaygut.prototype.domain.RecordState; 
import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddRoleForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.MemberRepository;
import com.alaygut.prototype.repository.RightRepository;
import com.alaygut.prototype.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;
    private RightRepository rightRepository;
    private MemberRepository memberRepository;
    private RightService rightService;

    public RoleServiceImpl(RoleRepository roleRepository, RightRepository rightRepository, MemberRepository memberRepository, RightService rightService) {
        this.roleRepository = roleRepository;
        this.rightRepository = rightRepository;
        this.memberRepository = memberRepository;
        this.rightService = rightService;
    }

    @Override
    public void addRole(AddRoleForm form) {
        Iterable<Right> selectedRights = rightRepository.findAllById(form.getRightIds());
        Set<Right> rights = new HashSet<>();

        for (Right right:selectedRights){
            rights.add((rightRepository.findById(right.getRightId())).orElse(null) );
        }

        Role role = new Role(
                form.getRoleName(),
                form.getDescription(),
                rights
        );
        if (form.getCreatorId() != null)
            role.setCreator(memberRepository.findById(form.getCreatorId()).orElse(null));

        roleRepository.save(role);
    }

    @Override
    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    @Override
    public Iterable<Role> getAllActiveRoles() {
    	return roleRepository.findAllByStateEquals(RecordState.ACTIVE);
    }
    
    @Override
    public Role getRole(Long roleId) {
    	return roleRepository.findById(roleId).orElse(null);
    }
    
    @Override
    public void deactivate(IDTransfer idTransfer) {
        Role role = roleRepository.findById(idTransfer.getRecordId()).orElse(null);
        role.setState(RecordState.NONACTIVE);
        roleRepository.save(role);
    }
    
    @Override
    public void edit(AddRoleForm addRoleForm) {
    	Role role = roleRepository.findById(addRoleForm.getRecordId()).orElse(null);	
    	role.setRoleName(addRoleForm.getRoleName());
    	role.setDescription(addRoleForm.getDescription());

        Iterable<Right> selectedRights = rightRepository.findAllById(addRoleForm.getRightIds());
        Set<Right> rights = new HashSet<>();

        for (Right right:selectedRights){
            rights.add((rightRepository.findById(right.getRightId())).orElse(null) );
        }

    	role.setRights(rights);
        role.setUpdater(memberRepository.findById(addRoleForm.getUpdaterId()).orElse(null));
    	
    	roleRepository.save(role);
    }

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

    public Iterable<Right> getAllRights(Long roleId){
        Role role = getRole(roleId);
        return rightRepository.findAllByRoles(role);
    }

    @Override
    public void fixForm(AddRoleForm form) {
        form.setAllRights(rightService.getAllActiveRights());
        form.setRoleRights(this.getAllRights(form.getRecordId()));
    }

    @Override
    public AddRoleForm getAddRoleForm() {
        AddRoleForm addRoleForm = new AddRoleForm();
        addRoleForm.setAllRights(rightService.getAllActiveRights());
        return addRoleForm;
    }
}
