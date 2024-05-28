package com.service.bank.util;

import com.service.bank.dto.SendCashDTO;
import com.service.bank.entity.User;
import com.service.bank.service.EmailService;
import com.service.bank.service.PhoneNumberService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TransactionValidator implements Validator {
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;
    private final PhoneNumberService phoneNumberService;

    public TransactionValidator(UserDetailsService userDetailsService, EmailService emailService, PhoneNumberService phoneNumberService) {
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
        this.phoneNumberService = phoneNumberService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SendCashDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SendCashDTO sendCashDTO = (SendCashDTO) target;
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = (User) this.userDetailsService.loadUserByUsername(login);
        if (sender.getAccount().getCount() < sendCashDTO.getSum())
            errors.rejectValue("sum", "", "The sum is more than count");
        else if (sendCashDTO.getEmail() != null || sendCashDTO.getPhoneNumber() != null){
            if (emailService.show(sendCashDTO.getEmail()).isEmpty()
                    && phoneNumberService.show(sendCashDTO.getPhoneNumber()).isEmpty()) {
                errors.rejectValue("email", "", "No user with this login");
            }
        } else if (sendCashDTO.getEmail() != null && sendCashDTO.getPhoneNumber() != null)
            errors.rejectValue("email", "", "Beneficiary must be pointed");
    }
}
