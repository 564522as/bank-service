package com.service.bank.service;

import com.service.bank.entity.PhoneNumber;
import com.service.bank.entity.User;
import com.service.bank.exception.DeleteLoginException;
import com.service.bank.repository.PhoneNumberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PhoneNumberImpl implements PhoneNumberService{
    private final PhoneNumberRepository phoneNumberRepository;
    private final UserDetailsService userDetailsService;

    public PhoneNumberImpl(PhoneNumberRepository phoneNumberRepository, UserDetailsService userDetailsService) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @Transactional
    public PhoneNumber add(PhoneNumber phoneNumber) {
        String currentUserLogin =
                SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userDetailsService.loadUserByUsername(currentUserLogin);
        if (user.getPhoneNumber() != null)
            user.getPhoneNumber().setValue(phoneNumber.getValue());
        else user.setPhoneNumber(phoneNumber);
        return this.phoneNumberRepository.save(user.getPhoneNumber());
    }

    @Override
    public Optional<PhoneNumber> show(String phoneNumber) {
        return this.phoneNumberRepository.findByValue(phoneNumber);
    }

    @Override
    @Transactional
    public void remove() {
        String currentUserLogin =
                SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userDetailsService.loadUserByUsername(currentUserLogin);

        if (user.getEmail() == null) {
            throw new DeleteLoginException("User must have email or phone number");
        }

        PhoneNumber phoneNumber = user.getPhoneNumber();
        if (user.getPhoneNumber() != null) {
            user.setPhoneNumber(null);
            phoneNumber.setUser(null);
            this.phoneNumberRepository.delete(phoneNumber);
        }
    }
}
