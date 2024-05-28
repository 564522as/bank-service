package com.service.bank.controller;

import com.service.bank.dto.AuthorizationDTO;
import com.service.bank.dto.RegistrationDTO;
import com.service.bank.entity.User;
import com.service.bank.exception.InvalidRegistrationDataException;
import com.service.bank.mapper.UserMapper;
import com.service.bank.security.JWTUtil;
import com.service.bank.service.UserService;
import com.service.bank.util.RegistrationValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "Контроллер для аутентификации и регистрации",
    description = "Обрабатывает запросы на аутентификацию и регистрацию пользователей")
public class AuthenticationController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RegistrationValidator registrationValidator;

    public AuthenticationController(UserMapper userMapper, UserService userService,
                                    JWTUtil jwtUtil, AuthenticationManager authenticationManager,
                                    RegistrationValidator registrationValidator) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.registrationValidator = registrationValidator;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Регистрация пользователя",
    description = "Получает данные пользователя такие как пароль, email, номер телефона")
    public HttpEntity<String> registration(@RequestBody @Valid RegistrationDTO registrationDTO,
                                           BindingResult bindingResult) {
        registrationValidator.validate(registrationDTO, bindingResult);
        String errorMessage = checkBindingResultOnErrors(bindingResult);
        if (!errorMessage.isEmpty()) {
            throw new InvalidRegistrationDataException(errorMessage);
        }

        User user = this.userMapper.toUser(registrationDTO);
        this.userService.add(user);

        String token;
        if (user.getEmail() != null)
            token = jwtUtil.generate(user.getEmail().getValue());
        else
            token = jwtUtil.generate(user.getPhoneNumber().getValue());

        return new HttpEntity<>(token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Аутентификация пользователя",
            description = "Передавая валидные email и пароль пользователя возвращается JWT токен")
    public HttpEntity<String> authentication(@RequestBody AuthorizationDTO authorizationDTO) {
        UsernamePasswordAuthenticationToken authenticationToken;
        String login;
        try {
            if (authorizationDTO.getEmail() != null) {
                login = authorizationDTO.getEmail();
            } else {
                login = authorizationDTO.getPhone();
            }
            authenticationToken = new UsernamePasswordAuthenticationToken(
                    login, authorizationDTO.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new AuthenticationCredentialsNotFoundException("Bad credentials");
        }
        return new HttpEntity<>(jwtUtil.generate(login));
    }

    public static String checkBindingResultOnErrors(BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            StringBuilder message = new StringBuilder();
            message.append("message: ");
            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getField());
                message.append(" - ");
                message.append(error.getDefaultMessage());
                message.append("; ");
            }
            return message.toString();
        }

        return "";
    }
}
