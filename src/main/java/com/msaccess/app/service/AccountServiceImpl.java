package com.msaccess.app.service;

import com.msaccess.app.dto.AccountStatement;
import com.msaccess.app.dto.AccountStatementReqDTO;
import com.msaccess.app.dto.AccountStatementRspDTO;
import com.msaccess.app.entity.StatementEntity;
import com.msaccess.app.repository.StatementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private StatementRepository statementRepository;

    @Override
    public AccountStatementRspDTO fetchAccountStatement(AccountStatementReqDTO requestDTO) {

        AccountStatementRspDTO accountStatementRspDTO = new AccountStatementRspDTO();

        List<String> searchList = getSearchDates(requestDTO.getFromDate(), requestDTO.getToDate());

        List<StatementEntity> statementEntities = statementRepository.findByAccountEntityIdAndDateFieldIn(requestDTO.getAccountId(), searchList);

        if (!statementEntities.isEmpty()) {
            //filter for amount
            if (null != requestDTO.getFromAmount() || null != requestDTO.getToAmount()) {
                statementEntities = statementEntities.stream().filter(s -> s.getAmount().compareTo(
                        requestDTO.getFromAmount()) >= 0 && s.getAmount().compareTo(requestDTO.getToAmount()) <= 0).collect(Collectors.toList());
            }
            if (!statementEntities.isEmpty()) {
                accountStatementRspDTO.setAccountNumber(statementEntities.get(0).getAccountEntity().getAccountNumber().hashCode());
                accountStatementRspDTO.setAccountType(statementEntities.get(0).getAccountEntity().getAccountType());
                List<AccountStatement> accountStatements = statementEntities.stream()
                        .map(s -> new AccountStatement(s.getDateField(), s.getAmount())).collect(Collectors.toList());
                accountStatementRspDTO.setAccountStatements(accountStatements);
            }
        }
        return accountStatementRspDTO;

    }

    List<String> getSearchDates(LocalDate fromDate, LocalDate toDate) {

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        fromDate = fromDate == null ? LocalDate.now() : fromDate;
        toDate = toDate == null ? LocalDate.now().plusMonths(3) : toDate;

        Stream<LocalDate> dates = fromDate.datesUntil(toDate.plusDays(1));

        List<String> list = dates.map(date -> date.format(formatters)).collect(Collectors.toList());
        return list;
    }

}
