package com.service.bank.dto;

import jakarta.validation.constraints.NotBlank;

public class PhoneNumberDTO {
    @NotBlank(message = "Phone number must not be empty")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PhoneNumberDTO{" +
                "value='" + value + '\'' +
                '}';
    }
}
