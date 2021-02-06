package ru.home.bill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.bill.controller.dto.BillRequestDTO;
import ru.home.bill.controller.dto.BillResponseDTO;
import ru.home.bill.entity.Bill;
import ru.home.bill.service.BillService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BillController {
    private final BillService service;

    @Autowired
    public BillController(BillService service) {
        this.service = service;
    }

    @GetMapping("/{BillId}")
    public BillResponseDTO getBill(@PathVariable Long BillId) {
        final Bill Bill = service.getBillById(BillId);

        return new BillResponseDTO(Bill);
    }

    @PostMapping()
    public Long createBill(@RequestBody BillRequestDTO dto) {
        return service.createBill(dto.getAccountId(), dto.getAmount(), dto.getIsDefault(), dto.getOverdraftEnabled());
    }

    @PutMapping("/{billId}")
    public BillResponseDTO updateBill(@PathVariable Long billId, @RequestBody BillRequestDTO dto) {
        final Bill Bill = service.updateBill(billId, dto.getAccountId(), dto.getAmount(), dto.getIsDefault(), dto.getOverdraftEnabled());

        return new BillResponseDTO(Bill);
    }

    @DeleteMapping("/{billId}")
    public BillResponseDTO deleteBill(@PathVariable Long billId) {
        final Bill Bill = service.deleteBill(billId);

        return new BillResponseDTO(Bill);
    }

    @GetMapping("/account/{accountId}")
    public List<BillResponseDTO> getBillsByAccountId(@PathVariable Long accountId) {
        return service.getBillsByAccountId(accountId).stream()
                .map(BillResponseDTO::new)
                .collect(Collectors.toList());
    }
}
