package com.service.bank.mapper;

import com.service.bank.dto.PhoneNumberDTO;
import com.service.bank.entity.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapper {
    PhoneNumber toPhoneNumber(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setValue(phoneNumber.getValue());
        return phoneNumber;
    }
}
