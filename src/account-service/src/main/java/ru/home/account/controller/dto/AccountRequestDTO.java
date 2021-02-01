package ru.home.account.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class AccountRequestDTO {
    private String name;
    private String phone;
    private String email;
    private List<Long> bills;
}
