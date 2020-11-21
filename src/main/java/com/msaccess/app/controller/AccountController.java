package com.msaccess.app.controller;

import com.msaccess.app.dto.AccountStatementReqDTO;
import com.msaccess.app.dto.AccountStatementRspDTO;
import com.msaccess.app.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "accountStatement")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private AccountService accountService;

    //    @PostMapping(path = "/fetch", consumes = "application/json", produces = "application/json")
    @GetMapping(path = "/fetch")
    public AccountStatementRspDTO fetch(@Valid AccountStatementReqDTO requestDTO) {

        return accountService.fetchAccountStatement(requestDTO);
    }


}