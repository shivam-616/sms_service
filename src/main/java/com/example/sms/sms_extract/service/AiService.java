package com.example.sms.sms_extract.service;


import com.example.sms.sms_extract.dto.TransactionDetailsDTO;
import com.example.sms.sms_extract.model.Sms;
import com.example.sms.sms_extract.repository.AiRepository;
import com.example.sms.sms_extract.service.preprocessing.SmsPreprocessingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    private AiRepository aiRepository;
    private ChatClient chatClient;
    private SmsPreprocessingService smsPreprocessingService;

    // Standard constructor injection (Better than using both Lombok @AllArgs and @NoArgs)
    public AiService(AiRepository aiRepository, ChatClient chatClient, SmsPreprocessingService smsPreprocessingService) {
        this.aiRepository = aiRepository;
        this.chatClient = chatClient;
        this.smsPreprocessingService = smsPreprocessingService;
    }


    @Value("classpath:/templates/sms-extraction.st")
    private Resource resource;

    public TransactionDetailsDTO extract(String sms) {
        System.out.println(sms);
        String cleanedSms = smsPreprocessingService.process(sms);
        System.out.println(cleanedSms + "\n\n\n");
        if (cleanedSms == null) {
            throw new RuntimeException("Invalid or non-transaction SMS");
        }

        TransactionDetailsDTO result = chatClient.prompt()
                .user(u -> u.text(resource).param("sms_text", cleanedSms))
                .call().entity(TransactionDetailsDTO.class);

        return result;
    }

    public void savetodb(TransactionDetailsDTO tdto) {
        Sms sms = new Sms();
        sms.set_transaction(tdto.is_transaction());
        sms.setAmount(tdto.amount());
        sms.setCurrency(tdto.currency());
        sms.setMerchant(tdto.merchant());
        sms.setCategory(tdto.category());
        sms.setPayment_method(tdto.payment_method());
        sms.setBank(tdto.bank());
        sms.setAccount_last4(tdto.account_last4());
        sms.setReference_id(tdto.reference_id());
        sms.setTimestamp(tdto.timestamp());
        sms.setBalance(tdto.balance());
        sms.setStatus(sms.getStatus());
        sms.setRaw_sms(tdto.raw_sms());

        aiRepository.save(sms);


    }

}
