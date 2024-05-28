package com.service.bank.controller;

import com.service.bank.dto.SendCashDTO;
import com.service.bank.exception.TransactionException;
import com.service.bank.service.BankAccountService;
import com.service.bank.util.TransactionValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Контроллер обработки транзакций",
        description = "Позволяет управляеть счетом пользователя")
public class TransactionController {
    private final BankAccountService bankAccountService;
    private final TransactionValidator transactionValidator;

    public TransactionController(BankAccountService bankAccountService, TransactionValidator transactionValidator) {
        this.bankAccountService = bankAccountService;
        this.transactionValidator = transactionValidator;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Перевод денежных средств",
            description = "Переводит средства со счета текущего пользователя на счет пользователя по email или номеру телефона")
    public HttpEntity<String> send(@RequestBody @Valid SendCashDTO sendCashDTO,
                                   BindingResult bindingResult) {
        this.transactionValidator.validate(sendCashDTO, bindingResult);
        String errorMessage = AuthenticationController.checkBindingResultOnErrors(bindingResult);
        if (!errorMessage.isEmpty())
            throw new TransactionException(errorMessage);
        if (sendCashDTO.getEmail() != null) {
            bankAccountService.move(sendCashDTO.getEmail(), sendCashDTO.getSum());
        } else {
            bankAccountService.move(sendCashDTO.getPhoneNumber(), sendCashDTO.getSum());
        }
        return new HttpEntity<>("Sent successfully");
    }
}
