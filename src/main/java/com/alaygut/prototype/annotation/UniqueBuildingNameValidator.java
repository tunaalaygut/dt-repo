package com.alaygut.prototype.annotation;

import com.alaygut.prototype.domain.Building;
import com.alaygut.prototype.repository.BuildingRepository;
import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueBuildingNameValidator implements ConstraintValidator<UniqueBuildingName, String > {

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public void initialize(UniqueBuildingName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String buildingName, ConstraintValidatorContext constraintValidatorContext) {
        if(buildingRepository==null) {
            return false;
        }
        boolean valid = (buildingRepository.findByBuildingName(buildingName) == null);
        return valid;

    }
}
