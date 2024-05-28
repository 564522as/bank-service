package com.service.bank.util;

import com.service.bank.dto.RegistrationDTO;

import com.service.bank.entity.PhoneNumber;
import com.service.bank.service.EmailService;
import com.service.bank.service.PhoneNumberService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class RegistrationValidator implements Validator {
    private final EmailService emailService;
    private final PhoneNumberService phoneNumberService;

    public RegistrationValidator(EmailService emailService, PhoneNumberService phoneNumberService) {
        this.emailService = emailService;
        this.phoneNumberService = phoneNumberService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDTO registrationDTO = (RegistrationDTO) target;
        if (registrationDTO.getEmail() != null)
            if (this.emailService.show(registrationDTO.getEmail()).isPresent())
                errors.rejectValue("email", "", "This email already taken");
        if (registrationDTO.getPhone() != null) {
            if (phoneNumberService.show(registrationDTO.getPhone()).isPresent())
                errors.rejectValue("phone", "", "This phone number already taken");
            return;
        }

        errors.rejectValue("email", "", "Must be pointed email or phone number");
    }
}
