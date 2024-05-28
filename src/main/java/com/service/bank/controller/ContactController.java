package com.service.bank.controller;

import com.service.bank.dto.EmailDTO;
import com.service.bank.dto.PhoneNumberDTO;
import com.service.bank.entity.Email;
import com.service.bank.entity.PhoneNumber;
import com.service.bank.exception.LoginAlreadyExistException;
import com.service.bank.service.EmailService;
import com.service.bank.service.PhoneNumberService;
import com.service.bank.util.EmailValidator;
import com.service.bank.util.PhoneNumberValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@Tag(name = "Контроллер управления почтой и номером телефона",
        description = "Обрабатывает запросы на добавление и удаление телефонного номера и почты")
public class ContactController {
    private final EmailService emailService;
    private final PhoneNumberService phoneNumberService;
    private final ModelMapper modelMapper;
    private final EmailValidator emailValidator;
    private final PhoneNumberValidator phoneNumberValidator;

    public ContactController(EmailService emailService,
                             PhoneNumberService phoneNumberService, ModelMapper modelMapper,
                             EmailValidator emailValidator, PhoneNumberValidator phoneNumberValidator) {
        this.emailService = emailService;
        this.phoneNumberService = phoneNumberService;
        this.modelMapper = modelMapper;
        this.emailValidator = emailValidator;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    @PostMapping("/email/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление email",
            description = "Добавление или обновление электронной почты")
    public HttpEntity<String> addEmail(@RequestBody @Valid EmailDTO emailDTO,
                                       BindingResult bindingResult) {
        Email email = modelMapper.map(emailDTO, Email.class);
        this.emailValidator.validate(email, bindingResult);
        String errorMessage = AuthenticationController.checkBindingResultOnErrors(bindingResult);
        if (!errorMessage.isEmpty())
            throw new LoginAlreadyExistException("This email already taken");
        this.emailService.add(email);
        return new HttpEntity<>("Email was added");
    }

    @PostMapping("/phone/add")
    @Operation(summary = "Добавление номера телефона",
            description = "Добавление или обновление номера телефона")
    public HttpEntity<String> addPhoneNumber(@RequestBody @Valid PhoneNumberDTO phoneNumberDTO,
                                             BindingResult bindingResult) {
        PhoneNumber phoneNumber = modelMapper.map(phoneNumberDTO, PhoneNumber.class);
        this.phoneNumberValidator.validate(phoneNumber, bindingResult);
        String errorMessage = AuthenticationController.checkBindingResultOnErrors(bindingResult);
        if (!errorMessage.isEmpty())
            throw new LoginAlreadyExistException("This phone number already taken");
        this.phoneNumberService.add(phoneNumber);
        return new HttpEntity<>("Phone number was added");
    }

    @DeleteMapping("/email")
    @Operation(summary = "Удаление электронной почты",
            description = "Удаляет электронную почту если есть номер телефона")
    public HttpEntity<String> deleteEmail() {
        this.emailService.remove();
        return new HttpEntity<>("Email wad deleted");
    }

    @DeleteMapping("/phone")
    @Operation(summary = "Добавление номера телефона",
            description = "Удаление номера телефона если имеется электронная почта")
    public HttpEntity<String> deletePhoneNumber() {
        this.phoneNumberService.remove();
        return new HttpEntity<>("Phone number wad deleted");
    }

}
