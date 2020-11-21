package com.msaccess.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountStatement {
    public String transactionDate;
    public Double transactionAmount;

}
