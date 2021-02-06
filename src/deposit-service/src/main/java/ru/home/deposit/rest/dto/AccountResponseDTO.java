package ru.home.deposit.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {
    private Long accountId;
    private String name;
    private String phone;
    private String email;
    private OffsetDateTime dateUpdate;
    private OffsetDateTime dateInsert;
    private List<Long> bills;
}
