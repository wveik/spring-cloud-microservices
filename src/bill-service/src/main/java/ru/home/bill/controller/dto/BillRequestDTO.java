package ru.home.bill.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@ToString
public class BillRequestDTO {
    private Long accountId;
    private BigDecimal amount;
    private Boolean isDefault;
    private Boolean overdraftEnabled;
}
