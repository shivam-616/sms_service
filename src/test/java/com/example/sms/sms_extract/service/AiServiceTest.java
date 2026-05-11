package com.example.sms.sms_extract.service;

import com.example.sms.sms_extract.dto.TransactionDetailsDTO;
import com.example.sms.sms_extract.service.preprocessing.SmsPreprocessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiServiceTest {

    @Autowired
    private ChatClient.Builder chatClientBuilder;
    private ChatClient chatClient;

    @Autowired
    private SmsPreprocessingService smsPreprocessingService;


    @BeforeEach
    void setUp() {
        // Build the real client from the autoconfigured builder
        this.chatClient = chatClientBuilder.build();
    }

    @Test
    void sms_extract() {
        // act
        String resource = "Extract transaction details from the following SMS: {sms_text}";
        String sms = "Dear Customer, Rs 5,500.00 was debited from A/c **X1234 on 10-May-26. Through phonepay. Avl Bal: INR 45,000.00. - Indian bank";

        //arrange
        String smsre  = smsPreprocessingService.process(sms);
        System.out.println(smsre);
        TransactionDetailsDTO result = chatClient.prompt()
                .user(u -> u.text(resource).param("sms_text", smsre))
                .call()
                .entity(TransactionDetailsDTO.class);

        // assert

    }
}