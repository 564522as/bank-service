package com.service.bank.service;

import com.service.bank.entity.BankAccount;

public interface BankAccountService {
    boolean move(String beneficiaryLogin, Double sum);
}
