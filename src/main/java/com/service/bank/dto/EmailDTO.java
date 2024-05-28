package com.service.bank.dto;

import jakarta.validation.constraints.NotBlank;

public class EmailDTO {
    @jakarta.validation.constraints.Email
    @NotBlank(message = "Name of email must not be empty")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "value='" + value + '\'' +
                '}';
    }
}
