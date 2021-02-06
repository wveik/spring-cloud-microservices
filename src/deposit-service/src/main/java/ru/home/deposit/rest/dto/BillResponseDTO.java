package ru.home.deposit.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BillResponseDTO {
    private Long billId;
    private Long accountId;
    private BigDecimal amount;
    private Boolean isDefault;
    private Boolean overdraftEnabled;
    private OffsetDateTime dateUpdate;
    private OffsetDateTime dateInsert;
}
