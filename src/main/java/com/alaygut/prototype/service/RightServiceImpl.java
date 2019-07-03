package com.alaygut.prototype.service;

import com.alaygut.prototype.domain.Right;
import com.alaygut.prototype.dto.AddRightForm;
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
}
