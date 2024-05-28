package com.service.bank.service;

import com.service.bank.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public User add(User user);
    public List<User> searchByBirthdate(Date birthdate, Integer page, Integer items);
    Optional<User> searchByEmail(String email);
    Optional<User> searchByPhone(String phoneNumber);
    List<User> searchByFullName(String fullName, Integer page, Integer items);
}
