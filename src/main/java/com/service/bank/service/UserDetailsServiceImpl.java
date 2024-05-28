package com.service.bank.service;

import com.service.bank.entity.User;
import com.service.bank.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional1 = this.userRepository.findByEmailValue(username);
        Optional<User> userOptional2 = this.userRepository.findByPhoneNumberValue(username);
        if (userOptional1.isPresent()) {
            return userOptional1.get();
        } else if (userOptional2.isPresent()) {
            return userOptional2.get();
        } else {
            throw new UsernameNotFoundException("User with this login is not registered");
        }
    }
}
