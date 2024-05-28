package com.service.bank.mapper;

import com.service.bank.dto.EmailDTO;
import com.service.bank.entity.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailMapper {
    public Email toEmail(EmailDTO emailDTO) {
        Email email = new Email();
        email.setValue(emailDTO.getValue());
        return email;
    }
}
