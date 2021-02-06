package ru.home.deposit.entity;

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
@NoArgsConstructor
@ToString
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long depositId;
    private BigDecimal amount;
    private Long billId;
    private String email;
    private OffsetDateTime dateUpdate;
    private OffsetDateTime dateInsert;

    public Deposit(BigDecimal amount, Long billId, String email, OffsetDateTime dateUpdate, OffsetDateTime dateInsert) {
        this.amount = amount;
        this.billId = billId;
        this.email = email;
        this.dateUpdate = dateUpdate;
        this.dateInsert = dateInsert;
    }
}
