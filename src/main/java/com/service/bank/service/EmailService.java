package com.service.bank.service;

import com.service.bank.entity.Email;

import java.util.Optional;

public interface EmailService {
    Email add(Email email);
    void remove();
    Optional<Email> show(String email);
}
