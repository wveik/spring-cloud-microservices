package ru.home.bill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.bill.entity.Bill;
import ru.home.bill.exception.BillNotFoundException;
import ru.home.bill.repository.BillRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BillService {
    private final BillRepository repository;

    @Autowired
    public BillService(BillRepository repository) {
        this.repository = repository;
    }

    public Bill getBillById(Long billId) {
        return repository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException("BillService exception. Unable to find Bill by id: " + billId));
    }

    public Long createBill(Long	accountId,
                           BigDecimal amount,
                           Boolean	isDefault,
                           Boolean	overdraftEnabled) {
        Bill Bill = new Bill(accountId, amount, isDefault, overdraftEnabled, OffsetDateTime.now(), OffsetDateTime.now());

        return repository.save(Bill).getBillId();
    }

    public Bill updateBill(Long billId,
                           Long	accountId,
                           BigDecimal amount,
                           Boolean	isDefault,
                           Boolean	overdraftEnabled) {
        Bill bill = getBillById(billId);
        bill.setAccountId(accountId);
        bill.setAmount(amount);
        bill.setIsDefault(isDefault);
        bill.setOverdraftEnabled(overdraftEnabled);
        bill.setDateUpdate(OffsetDateTime.now());
        return repository.save(bill);
    }

    public Bill deleteBill(Long billId) {
        Bill Bill = getBillById(billId);

        repository.delete(Bill);

        return Bill;
    }

    public List<Bill> getBillsByAccountId(Long accountId) {
        return repository.getBillsByAccountId(accountId);
    }
}
