package ru.home.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.account.controller.dto.AccountRequestDTO;
import ru.home.account.controller.dto.AccountResponseDTO;
import ru.home.account.entity.Account;
import ru.home.account.service.AccountService;

@RestController
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{accountId}")
    public AccountResponseDTO getAccount(@PathVariable Long accountId) {
        final Account account = service.getAccountById(accountId);

        return new AccountResponseDTO(account);
    }

    @PostMapping()
    public Long createAccount(@RequestBody AccountRequestDTO dto) {
        return service.createAccount(dto.getName(), dto.getPhone(), dto.getEmail(), dto.getBills());
    }

    @PutMapping("/{accountId}")
    public AccountResponseDTO updateAccount(@PathVariable Long accountId, @RequestBody AccountRequestDTO dto) {
        final Account account = service.updateAccount(accountId, dto.getName(), dto.getPhone(), dto.getEmail(), dto.getBills());

        return new AccountResponseDTO(account);
    }

    @DeleteMapping("/{accountId}")
    public AccountResponseDTO deleteAccount(@PathVariable Long accountId) {
        final Account account = service.deleteAccount(accountId);

        return new AccountResponseDTO(account);
    }
}
