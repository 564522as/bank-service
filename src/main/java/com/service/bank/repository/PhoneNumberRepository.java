package com.service.bank.repository;

import com.service.bank.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    Optional<PhoneNumber> findByUserId(long id);

    Optional<PhoneNumber> findByValue(String value);
}
