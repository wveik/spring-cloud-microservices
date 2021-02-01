package ru.home.bill.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long billId;
    private Long accountId;
    private BigDecimal amount;
    private Boolean isDefault;
    private Boolean overdraftEnabled;
    private OffsetDateTime dateUpdate;
    private OffsetDateTime dateInsert;

    public Bill(Long accountId, BigDecimal amount, Boolean isDefault, Boolean overdraftEnabled, OffsetDateTime dateUpdate, OffsetDateTime dateInsert) {
        this.accountId = accountId;
        this.amount = amount;
        this.isDefault = isDefault;
        this.overdraftEnabled = overdraftEnabled;
        this.dateUpdate = dateUpdate;
        this.dateInsert = dateInsert;
    }
}
