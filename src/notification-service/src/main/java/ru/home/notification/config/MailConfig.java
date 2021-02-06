package ru.home.notification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail-props.properties")
public class MailConfig {
    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smpt.gmail.com");
        sender.setPort(587);

        sender.setUsername(environment.getProperty("mail.username"));
        sender.setPassword(environment.getProperty("mail.password"));

        Properties properties = sender.getJavaMailProperties();
        properties.put("mail.transport.protocol", environment.getProperty("mail.transport.protocol"));
        properties.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.debug", environment.getProperty("mail.debug"));

        return sender;
    }
}
