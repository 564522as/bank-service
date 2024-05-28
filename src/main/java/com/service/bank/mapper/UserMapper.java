package com.service.bank.mapper;

import com.service.bank.dto.RegistrationDTO;
import com.service.bank.entity.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserMapper {

    public User toUser(RegistrationDTO registrationDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date birthDate;
        try {
            birthDate = sdf.parse(registrationDTO.getBirthdate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(registrationDTO.getPhone());
        return new User.UserBuilder()
                .fullName(registrationDTO.getFullName())
                .email(registrationDTO.getEmail())
                .bankAccount(registrationDTO.getCount())
                .phoneNumber(registrationDTO.getPhone())
                .birthDate(birthDate)
                .password(registrationDTO.getPassword())
                .build();
    }

}
