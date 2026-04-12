package com.sportsbooking.emailapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EmailResponse implements Serializable {
    private boolean success;
    private String message;
}
