package com.msaccess.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountStatementRspDTO {

    private String accountType;
    private Integer accountNumber;

    List<AccountStatement> accountStatements;
}
