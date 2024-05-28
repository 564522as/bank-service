package com.service.bank.repository;

import com.service.bank.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Optional<Email> findByUserId(long id);
    Optional<Email> findByValue(String value);
}
