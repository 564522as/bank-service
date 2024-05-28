package com.service.bank.util;

import com.service.bank.entity.PhoneNumber;
import com.service.bank.service.PhoneNumberService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PhoneNumberValidator implements Validator {
    private final PhoneNumberService phoneNumberService;

    public PhoneNumberValidator(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PhoneNumber.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PhoneNumber phoneNumber = (PhoneNumber) target;
        if (this.phoneNumberService.show(phoneNumber.getValue()).isPresent())
            errors.rejectValue("value", "", "This phone number already taken");
    }
}
