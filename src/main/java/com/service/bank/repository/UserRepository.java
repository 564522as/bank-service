package com.service.bank.repository;

import com.service.bank.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailValue(String value);
    Optional<User> findByPhoneNumberValue(String value);
    List<User> findByFullNameStartsWith(String fullName);
    List<User> findByFullNameStartsWith(String fullName, Pageable pageable);
    @Query("from User u where u.birthdate >= :birthDate")
    List<User> findByBirthDateWithPagination(Date birthDate, Pageable pageable);
    @Query("from User u where u.birthdate >= :birthDate")
    List<User> findByBirthDate(Date birthDate);
}
