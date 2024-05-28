package com.service.bank.service;

import com.service.bank.entity.Email;
import com.service.bank.entity.User;
import com.service.bank.exception.DeleteLoginException;
import com.service.bank.repository.EmailRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService{
    private final EmailRepository emailRepository;
    private final UserDetailsService userDetailsService;

    public EmailServiceImpl(EmailRepository emailRepository, UserDetailsService userDetailsService) {
        this.emailRepository = emailRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @Transactional
    public Email add(Email email) {
        String currentUserLogin =
                SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userDetailsService.loadUserByUsername(currentUserLogin);
        if (user.getEmail() != null)
            user.getEmail().setValue(email.getValue());
        else user.setEmail(email);
        return this.emailRepository.save(user.getEmail());
    }

    @Override
    @Transactional
    public void remove() {
        String currentUserLogin =
                SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userDetailsService.loadUserByUsername(currentUserLogin);

        if (user.getPhoneNumber() == null)
            throw new DeleteLoginException("User must have email or phone number");

        Email email = user.getEmail();
        if (user.getEmail() != null) {
            user.setEmail(null);
            email.setUser(null);
            this.emailRepository.delete(email);
        }
    }

    @Override
    public Optional<Email> show(String email) {
        return this.emailRepository.findByValue(email);
    }
}
