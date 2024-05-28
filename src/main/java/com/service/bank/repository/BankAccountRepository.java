package com.service.bank.repository;

import com.service.bank.entity.BankAccount;
import com.service.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, User> {
}
