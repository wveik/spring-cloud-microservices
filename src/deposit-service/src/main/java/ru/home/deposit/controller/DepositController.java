package ru.home.deposit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.home.deposit.controller.dto.DepositRequestDTO;
import ru.home.deposit.controller.dto.DepositResponseDTO;
import ru.home.deposit.service.DepositService;

@RestController
public class DepositController {
    private final DepositService service;

    @Autowired
    public DepositController(DepositService service) {
        this.service = service;
    }

    @PostMapping("/deposits")
    public DepositResponseDTO deposit(@RequestBody DepositRequestDTO requestDTO) {
        return service.deposit(requestDTO.getAccountId(),
                requestDTO.getBillId(),
                requestDTO.getAmount());
    }
}
