package ru.home.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.deposit.controller.dto.DepositResponseDTO;
import ru.home.deposit.entity.Deposit;
import ru.home.deposit.exception.DepositNotFoundException;
import ru.home.deposit.exception.DepositServiceException;
import ru.home.deposit.repository.DepositRepository;
import ru.home.deposit.rest.AccountServiceClient;
import ru.home.deposit.rest.BillServiceClient;
import ru.home.deposit.rest.dto.AccountResponseDTO;
import ru.home.deposit.rest.dto.BillRequestDTO;
import ru.home.deposit.rest.dto.BillResponseDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {
    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository repository;
    private final AccountServiceClient accountServiceClient;
    private final BillServiceClient billServiceClient;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DepositService(DepositRepository repository, AccountServiceClient accountServiceClient, BillServiceClient billServiceClient, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.accountServiceClient = accountServiceClient;
        this.billServiceClient = billServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public DepositResponseDTO deposit(Long accountId,
                                      Long billId,
                                      BigDecimal amount) {

        if (accountId == null && billId == null) {
            throw new DepositServiceException("Account is null and Bill is null");
        }

        if (billId != null) {
            BillResponseDTO billResponseDTO = billServiceClient.getBillById(billId);
            BillRequestDTO billRequestDTO = new BillRequestDTO(billResponseDTO.getAccountId()
                                                , billResponseDTO.getAmount().add(amount)
                                                , billResponseDTO.getIsDefault()
                                                , billResponseDTO.getOverdraftEnabled());

            billServiceClient.update(billId, billRequestDTO);

            AccountResponseDTO accountResponseDTO = accountServiceClient.getAccountById(billResponseDTO.getAccountId());
            createDeposit(amount, billId, accountResponseDTO.getEmail());

            return createDepositResponseDto(amount, accountResponseDTO);
        }

        BillResponseDTO defaultBill = getDefaultBill(accountId);
        BillRequestDTO billRequestDTO = new BillRequestDTO(defaultBill.getAccountId()
                , defaultBill.getAmount().add(amount)
                , defaultBill.getIsDefault()
                , defaultBill.getOverdraftEnabled());

        billServiceClient.update(defaultBill.getBillId(), billRequestDTO);
        AccountResponseDTO accountResponseDTO = accountServiceClient.getAccountById(accountId);
        createDeposit(amount, defaultBill.getBillId(), accountResponseDTO.getEmail());

        return createDepositResponseDto(amount, accountResponseDTO);
    }

    private DepositResponseDTO createDepositResponseDto(BigDecimal amount, AccountResponseDTO accountResponseDTO) {
        DepositResponseDTO response = new DepositResponseDTO(amount, accountResponseDTO.getEmail());

        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT,
                    ROUTING_KEY_DEPOSIT,
                    new ObjectMapper().writeValueAsString(response));
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            throw new DepositServiceException("Can't send message to RabbitMQ.");
        }

        return response;
    }

    private BillResponseDTO getDefaultBill(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId).stream()
                .filter(BillResponseDTO::getIsDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("Unable to find default bill for account: " + accountId));
    }

    public Deposit getDepositById(Long DepositId) {
        return repository.findById(DepositId)
                .orElseThrow(() -> new DepositNotFoundException("DepositService exception. Unable to find Deposit by id: " + DepositId));
    }

    public Long createDeposit(BigDecimal amount,
                              Long	billId,
                              String email) {
        Deposit Deposit = new Deposit(amount, billId, email, OffsetDateTime.now(), OffsetDateTime.now());

        return repository.save(Deposit).getDepositId();
    }

    public Deposit updateDeposit(Long depositId,
                                 BigDecimal amount,
                                 Long	billId,
                                 String email) {
        Deposit deposit = getDepositById(depositId);
        deposit.setAmount(amount);
        deposit.setBillId(billId);
        deposit.setEmail(email);
        deposit.setDateUpdate(OffsetDateTime.now());
        return repository.save(deposit);
    }

    public Deposit deleteDeposit(Long DepositId) {
        Deposit Deposit = getDepositById(DepositId);

        repository.delete(Deposit);

        return Deposit;
    }
}
