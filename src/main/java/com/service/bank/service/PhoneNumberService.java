package com.service.bank.service;

import com.service.bank.entity.PhoneNumber;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PhoneNumberService {
    PhoneNumber add(PhoneNumber phoneNumber);
    Optional<PhoneNumber> show(String phoneNumber);
    void remove();
}
