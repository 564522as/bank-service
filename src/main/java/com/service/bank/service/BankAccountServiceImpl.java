package com.service.bank.service;

import com.service.bank.entity.User;
import com.service.bank.repository.BankAccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserDetailsService userDetailsService;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserDetailsService userDetailsService) {
        this.bankAccountRepository = bankAccountRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean move(String beneficiaryLogin, Double sum) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) this.userDetailsService.loadUserByUsername(login);
        user.getAccount().addCash(-sum);
        User beneficiary = (User) this.userDetailsService.loadUserByUsername(beneficiaryLogin);
        beneficiary.getAccount().addCash(sum);
        this.bankAccountRepository.save(user.getAccount());
        this.bankAccountRepository.save(beneficiary.getAccount());
        return true;
    }
}
