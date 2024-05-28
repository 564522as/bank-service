package com.service.bank.controller;

import com.service.bank.dto.UserResponseDTO;
import com.service.bank.entity.User;
import com.service.bank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/search")
@Tag(name = "Контроллер поиска пользователей",
        description = "Возвращает пользователя или пользователей по заданным параметрам")
public class SearchController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public SearchController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/by-birthdate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение списка пользователей по дате рождения",
            description = "Возвращает пользователей, дни рождения которых позже указанного")
    public HttpEntity<List<UserResponseDTO>> searchByBirthDate(
            @RequestParam("birthdate") @DateTimeFormat(pattern = "dd.MM.yyyy")
            @Parameter(description = "Отсчетная дата для поиска") Date bithdate,
            @RequestParam(value = "page", required = false) @Parameter(description = "Номер страницы") Integer page,
            @RequestParam(value = "items", required = false) @Parameter(description = "Размер одной страницы") Integer items) {
        List<User> users = this.userService.searchByBirthdate(bithdate, page, items);
        List<UserResponseDTO> responseDTOList = new ArrayList<>();
        for (User user: users) {
            responseDTOList.add(this.modelMapper.map(user, UserResponseDTO.class));
        }
        return new HttpEntity<>(responseDTOList);
    }

    @GetMapping("/by-phone")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение пользователя по номеру телефона",
            description = "Возвращает пользователя с данным номером телефона")
    public HttpEntity<UserResponseDTO> searchByPhone(
            @RequestParam @Parameter(description = "Номер телефона искомого пользователя")String phoneNumber) {
        Optional<User> user = this.userService.searchByPhone(phoneNumber);
        if (user.isPresent()) {
            return new HttpEntity<>(this.modelMapper.map(user, UserResponseDTO.class));
        } else {
            throw new UsernameNotFoundException("User with this phone number not registered");
        }
    }

    @GetMapping("/by-email")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение пользователя по email",
            description = "Возвращает пользователя с данным email")
    public HttpEntity<UserResponseDTO> searchByEmail(
            @RequestParam @Parameter(description = "Email пользователя") String email) {
        Optional<User> user = this.userService.searchByEmail(email);
        if (user.isPresent()) {
            return new HttpEntity<>(this.modelMapper.map(user, UserResponseDTO.class));
        } else {
            throw new UsernameNotFoundException("User with this email not registered");
        }
    }

    @GetMapping("/by-full-name")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Получение пользователей по ФИО",
            description = "Возвращает пользователе, ФИО которых начинается с заданной строки")
    public HttpEntity<List<UserResponseDTO>> searchByName(@RequestParam @Parameter(description = "ФИО пользователя")String fullName,
            @RequestParam(value = "page", required = false) @Parameter(description = "Номер страницы") Integer page,
            @RequestParam(value = "items", required = false) @Parameter(description = "Размер одной страницы") Integer items) {
        List<User> users = this.userService.searchByFullName(fullName, page, items);
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        for (User user: users) {
            userResponseDTOList.add(this.modelMapper.map(user, UserResponseDTO.class));
        }
        return new HttpEntity<>(userResponseDTOList);
    }
}
