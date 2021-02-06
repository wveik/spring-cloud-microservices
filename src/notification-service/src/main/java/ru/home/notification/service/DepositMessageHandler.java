package ru.home.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;
import ru.home.notification.config.RabbitMQConfig;
import ru.home.notification.dto.DepositResponseDTO;

@Service
public class DepositMessageHandler {
    private final JavaMailSender javaMailSender;
    private static final String TEST_MAIL = "test@test.ru";

    @Autowired
    public DepositMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) throws JsonProcessingException {
        System.out.println("message = " + message);

        byte[] body = message.getBody();
        String json = new String(body);

        ObjectMapper mapper = new ObjectMapper();
        DepositResponseDTO dto = mapper.readValue(json, DepositResponseDTO.class);
        System.out.println("dto = " + dto);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(dto.getMail());
        mailMessage.setFrom(TEST_MAIL);

        mailMessage.setSubject("Deposit");
        mailMessage.setText("Make deposit, sum: " + dto.getAmount());

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception ex) {
            System.out.println("ex.getMessage() = " + ex.getMessage());
        }
    }
}
