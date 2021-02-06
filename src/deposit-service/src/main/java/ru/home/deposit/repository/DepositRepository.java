package ru.home.deposit.repository;

import org.springframework.data.repository.CrudRepository;
import ru.home.deposit.entity.Deposit;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
}
