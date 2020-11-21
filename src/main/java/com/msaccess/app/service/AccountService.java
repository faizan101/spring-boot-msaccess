package com.msaccess.app.service;


import com.msaccess.app.dto.AccountStatementReqDTO;
import com.msaccess.app.dto.AccountStatementRspDTO;

public interface AccountService {

    AccountStatementRspDTO fetchAccountStatement(AccountStatementReqDTO requestDTO);
}
