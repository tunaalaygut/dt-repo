package com.alaygut.prototype.annotation;

import com.alaygut.prototype.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator <UniqueUsername, String > {


    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(memberRepository==null) {

            return false;
        }
        return memberRepository.findByLoginUsername(username) == null;
    }
}
