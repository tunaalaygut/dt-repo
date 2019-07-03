package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.RecordState; 
import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.domain.Role;
import com.alaygut.prototype.dto.AddRoleForm;
import com.alaygut.prototype.repository.RightRepository;
import com.alaygut.prototype.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;
    private RightRepository rightRepository;

    public RoleServiceImpl(RoleRepository roleRepository, RightRepository rightRepository) {
        this.roleRepository = roleRepository;
        this.rightRepository = rightRepository;
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
}
