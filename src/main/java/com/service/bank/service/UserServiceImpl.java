package com.service.bank.service;

import com.service.bank.entity.User;
import com.service.bank.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User add(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public List<User> searchByBirthdate(Date birthdate, Integer page, Integer items) {
        if (page == null && items == null)
            return this.userRepository.findByBirthDate(birthdate);
        else if (page == null)
            return this.userRepository.findByBirthDateWithPagination(birthdate, PageRequest.of(1, items));
        else if (items == null)
            return this.userRepository.findByBirthDateWithPagination(birthdate, PageRequest.of(page, 10));
        return this.userRepository.findByBirthDateWithPagination(birthdate, PageRequest.of(page, items));
    }

    @Override
    public Optional<User> searchByEmail(String email) {
        return this.userRepository.findByEmailValue(email);
    }

    @Override
    public Optional<User> searchByPhone(String phoneNumber) {
        return this.userRepository.findByPhoneNumberValue(phoneNumber);
    }

    @Override
    public List<User> searchByFullName(String fullName, Integer page, Integer items) {
        if (page == null && items == null)
            return this.userRepository.findByFullNameStartsWith(fullName);
        else if (page == null)
            return this.userRepository.findByFullNameStartsWith(fullName, PageRequest.of(1, items));
        else if (items == null)
            return this.userRepository.findByFullNameStartsWith(fullName, PageRequest.of(page, 10));
        return this.userRepository.findByFullNameStartsWith(fullName, PageRequest.of(page, items));
    }
}
