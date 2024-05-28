package com.service.bank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class RegistrationDTO {
    @NotBlank(message = "Full name must be pointed")
    @Length(max = 75, min = 5)
    private String fullName;
    @NotBlank(message = "Birthdate must be pointed")
    private String birthdate;
    @Email
    private String email;
    @Length(min = 12, max = 12)
    private String phone;
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long count;
    @NotBlank(message = "Password must be pointed")
    @Length(max = 25, min = 5)
    private String password;

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "name='" + fullName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", count=" + count +
                ", password='" + password + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
