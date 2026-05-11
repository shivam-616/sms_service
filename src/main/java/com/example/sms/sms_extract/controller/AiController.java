package com.example.sms.sms_extract.controller;


import com.example.sms.sms_extract.dto.TransactionDetailsDTO;
import com.example.sms.sms_extract.service.AiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/sms")
@RestController
@AllArgsConstructor
public class AiController {


    private AiService aiservice;

    @GetMapping
    public TransactionDetailsDTO sms(@RequestParam String sms) {
      return aiservice.extract(sms);
    }
}
