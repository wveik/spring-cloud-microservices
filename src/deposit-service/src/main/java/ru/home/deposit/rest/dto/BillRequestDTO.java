package ru.home.deposit.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BillRequestDTO {
    private Long accountId;
    private BigDecimal amount;
    private Boolean isDefault;
    private Boolean overdraftEnabled;
}
