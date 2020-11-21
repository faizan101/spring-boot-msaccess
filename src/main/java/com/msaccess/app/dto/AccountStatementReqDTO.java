package com.msaccess.app.dto;

import com.msaccess.app.validator.AmountCheck;
import com.msaccess.app.validator.DateCheck;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AmountCheck.List({
        @AmountCheck(
                fromAmount = "fromAmount",
                toAmount = "toAmount",
                message = "Amount fields are incorrect!"
        )
})
@DateCheck.List({
        @DateCheck(
                fromDate = "fromDate",
                toDate = "toDate",
                message = "Date fields are incorrect!"
        )
})
public class AccountStatementReqDTO {

    @NotNull(message = "Provide Account Id")
    private Long accountId;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate fromDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate toDate;

    @Min(1)
    private Double fromAmount;

    @Min(1)
    private Double toAmount;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }
}
