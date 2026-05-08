package com.example.sms.sms_extract.service;


import com.example.sms.sms_extract.repository.AiRepository;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AiService {
    private AiRepository aiRepository;
    private ChatClient chatClient;


    public String extract(String sms) {
        if(isValidsms(sms)){

        }
        return sms;
    }




    public boolean isValidsms(String sms){
            return (sms.contains("A/c") && (sms.contains("debited") || sms.contains("credited") || sms.contains("spent")));
        }
}
