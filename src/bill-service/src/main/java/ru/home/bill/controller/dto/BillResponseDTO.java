package ru.home.bill.controller.dto;

import lombok.Getter;
import lombok.ToString;
import ru.home.bill.entity.Bill;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@ToString
public class BillResponseDTO {
    private Long billId;
    private Long accountId;
    private BigDecimal amount;
    private Boolean isDefault;
    private Boolean overdraftEnabled;
    private OffsetDateTime dateUpdate;
    private OffsetDateTime dateInsert;

    public BillResponseDTO(Bill bill) {
        this.billId = bill.getBillId();
        this.accountId = bill.getAccountId();
        this.amount = bill.getAmount();
        this.isDefault = bill.getIsDefault();
        this.overdraftEnabled = bill.getOverdraftEnabled();
        this.dateUpdate = bill.getDateUpdate();
        this.dateInsert = bill.getDateInsert();
    }
}
