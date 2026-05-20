package com.example.sms.sms_extract.controller;


import com.example.sms.sms_extract.dto.TransactionDetailsDTO;
import com.example.sms.sms_extract.service.AiService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/sms")
@RestController
@AllArgsConstructor
public class AiController {


    private AiService aiservice;

    @PostMapping
    public ResponseEntity sms(@RequestHeader("X-User-Id") String userId, @RequestBody String sms) {
         aiservice.extract(userId , sms);
         return ResponseEntity.accepted().body(Map.of("status", "Accepted", "message", "SMS queued for expense processing"));
    }
}
