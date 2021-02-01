package ru.home.account.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.home.account.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
