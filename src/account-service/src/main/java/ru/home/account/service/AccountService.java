package ru.home.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.account.entity.Account;
import ru.home.account.exception.AccountNotFoundException;
import ru.home.account.repository.AccountRepository;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getAccountById(Long accountId) {
        return repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("AccountService exception. Unable to find account by id: " + accountId));
    }

    public Long createAccount(String name, String phone, String email, List<Long> bills) {
        Account account = new Account(name, phone, email, bills, OffsetDateTime.now(), OffsetDateTime.now());

        return repository.save(account).getAccountId();
    }

    public Account updateAccount(Long accountId, String name, String phone, String email, List<Long> bills) {
        Account account = getAccountById(accountId);
        account.setName(name);
        account.setPhone(phone);
        account.setEmail(email);
        account.setBills(bills);
        account.setDateUpdate(OffsetDateTime.now());

        return repository.save(account);
    }

    public Account deleteAccount(Long accountId) {
        Account account = getAccountById(accountId);

        repository.delete(account);

        return account;
    }
}
