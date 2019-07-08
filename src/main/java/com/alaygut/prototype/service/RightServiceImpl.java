package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.RecordState;
import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.dto.AddRightForm;
import com.alaygut.prototype.dto.IDTransfer;
import com.alaygut.prototype.repository.RightRepository;
import org.springframework.stereotype.Service;

@Service
public class RightServiceImpl implements RightService{
    private RightRepository rightRepository;

    public RightServiceImpl(RightRepository rightRepository) {
        this.rightRepository = rightRepository;
    }

    @Override
    public void addRight(AddRightForm form) {
        Right right = new Right(
                form.getRightName(),
                form.getDescription()
        );
        rightRepository.save(right);
    }

    @Override
    public Iterable<Right> getAllRights() {
        return rightRepository.findAll();
    }
    
    @Override
    public Iterable<Right> getAllActiveRights() {
        return rightRepository.findAllByStateEquals(RecordState.ACTIVE);
    }
    
    @Override
    public Right getRight(Long rightId) {
    	return rightRepository.findById(rightId).orElse(null); 	
    }
    
    @Override
    public void deactivate(IDTransfer idTransfer) {
    	Right right = rightRepository.findById(idTransfer.getRecordId()).orElse(null);
    	right.setState(RecordState.NONACTIVE);
    	rightRepository.save(right);
    }
    
    @Override
    public void edit(AddRightForm addRightForm) {
    	Right right = rightRepository.findById(addRightForm.getRecordId()).orElse(null);
    	right.setRightName(addRightForm.getRightName());
    	right.setDescription(addRightForm.getDescription());
    	
    	rightRepository.save(right);
    }
}
