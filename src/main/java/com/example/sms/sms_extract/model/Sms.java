package com.example.sms.sms_extract.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int sms_id;

    String name;

    Double amount;

    Date created_at;

    Long total_amount;
}
