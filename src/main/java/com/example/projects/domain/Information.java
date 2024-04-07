package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Information {
    private Object qrCode;
    private String trakheesiNumber;
    private Date dateOfExpiration;
    private Object insertFileHere;


    public Information(Object qrCode, String trakheesiNumber, Object insertFileHere) {
        this.qrCode = qrCode;
        this.trakheesiNumber = trakheesiNumber;
        this.insertFileHere = insertFileHere;
    }
}
