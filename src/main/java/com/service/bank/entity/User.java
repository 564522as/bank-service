package com.service.bank.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name must be pointed")
    @Length(max = 25, min = 5)
    private String fullName;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthdate;
    @NotBlank(message = "Password must be pointed")
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private BankAccount account;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Email email;
    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private PhoneNumber phoneNumber;

    private User(UserBuilder userBuilder) {
        this.fullName = userBuilder.fullName;
        this.password = userBuilder.password;
        this.account = userBuilder.account;
        this.email = userBuilder.email;
        this.phoneNumber = userBuilder.phoneNumber;
        this.birthdate = userBuilder.birthdate;
        this.account.setUser(this);
        if (this.email != null) {
            this.email.setUser(this);
        }
        if (this.phoneNumber != null) {
            this.phoneNumber.setUser(this);
        }
    }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        if (this.email != null) {
            return this.email.getValue();
        } else {
            return this.phoneNumber.getValue();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static class UserBuilder {
        private String fullName;
        private String password;
        private BankAccount account;
        private Email email;
        private Date birthdate;
        private PhoneNumber phoneNumber;
        public UserBuilder fullName(String name) {
            this.fullName = name;
            return this;
        }
        public UserBuilder bankAccount(double count) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setCount(count);
            this.account = bankAccount;
            return this;
        }
        public UserBuilder email(String value) {
            if (value != null) {
                Email email = new Email();
                email.setValue(value);
                this.email = email;
            }
            return this;
        }
        public UserBuilder phoneNumber(String value) {
            if (value != null) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setValue(value);
                this.phoneNumber = phoneNumber;
            }
            return this;
        }
        public UserBuilder birthDate(Date birthdate) {
            this.birthdate = birthdate;
            return this;
        }
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }
        public User build() {
            return new User(this);
        }


    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthDate) {
        this.birthdate = birthDate;
    }
}
