package com.service.bank.util;

import com.service.bank.entity.Email;
import com.service.bank.service.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmailValidator implements Validator {
    private final EmailService emailService;

    public EmailValidator(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Email.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Email email = (Email) target;
        if (this.emailService.show(email.getValue()).isPresent())
            errors.rejectValue("value", "", "This email already taken");
    }
}
