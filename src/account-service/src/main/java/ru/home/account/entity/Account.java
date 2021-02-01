package ru.home.account.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    private String name;
    private String phone;
    private String email;
    private OffsetDateTime dateUpdate;
    private OffsetDateTime dateInsert;

    public Account(String name, String phone, String email, List<Long> bills, OffsetDateTime dateUpdate, OffsetDateTime dateInsert) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.dateUpdate = dateUpdate;
        this.dateInsert = dateInsert;
        this.bills = bills;
    }

    @ElementCollection
    private List<Long> bills;
}
