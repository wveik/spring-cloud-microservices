package ru.home.account.controller.dto;

import lombok.Getter;
import lombok.ToString;
import ru.home.account.entity.Account;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@ToString
public class AccountResponseDTO {
    private Long accountId;
    private String name;
    private String phone;
    private String email;
    private OffsetDateTime dateUpdate;
    private OffsetDateTime dateInsert;
    private List<Long> bills;

    public AccountResponseDTO(Account account) {
        this.accountId = account.getAccountId();
        this.name = account.getName();
        this.phone = account.getPhone();
        this.email = account.getEmail();
        this.dateUpdate = account.getDateUpdate();
        this.dateInsert = account.getDateInsert();
        this.bills = account.getBills();
    }
}
