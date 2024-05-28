package com.service.bank.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double count;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public BankAccount(double count) {
        this.count = count;
    }
    public BankAccount() {
    }

    public double addCash(double cash) {
        this.count += cash;
        return this.count;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
