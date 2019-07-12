package com.alaygut.prototype.annotation;

import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String > {


    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(memberRepository==null) {
            return false;
        }
        return memberRepository.findByEmail(email) == null;
    }

}
