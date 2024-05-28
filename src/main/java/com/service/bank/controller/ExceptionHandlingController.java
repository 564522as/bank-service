package com.service.bank.controller;

import com.service.bank.dto.ErrorBody;
import com.service.bank.exception.DeleteLoginException;
import com.service.bank.exception.InvalidRegistrationDataException;
import com.service.bank.exception.LoginAlreadyExistException;
import com.service.bank.exception.TransactionException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Tag(name = "Контроллер обработки ошибок",
        description = "Возвращает пользователю описание появляющихся ошибок")
public class ExceptionHandlingController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleLoginExceptions(DeleteLoginException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleRegistrationExceptions(InvalidRegistrationDataException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleLoginExistExceptions(LoginAlreadyExistException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleTransactionExceptions(TransactionException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<ErrorBody> handleAuthenticationExceptions(AuthenticationException e) {
        ErrorBody errorBody = new ErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }
}
