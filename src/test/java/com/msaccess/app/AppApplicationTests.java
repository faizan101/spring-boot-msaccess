package com.msaccess.app;

import com.msaccess.app.dto.AccountStatementReqDTO;
import com.msaccess.app.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    AccountStatementReqDTO accountStatementReqDTO;

    @BeforeEach
    public void setUp() {

        accountStatementReqDTO = new AccountStatementReqDTO();
        accountStatementReqDTO.setAccountId(1l);
        accountStatementReqDTO.setFromAmount(300d);
        accountStatementReqDTO.setToAmount(700d);
        accountStatementReqDTO.setFromDate(LocalDate.now());
        accountStatementReqDTO.setToDate(LocalDate.now());

    }

    @Test
    public void givenNoPrivileges_whenFetchStatement_thenRedirectToLogin() throws Exception {
        mockMvc.perform(get("/accountStatement/fetch").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(value = "testadmin")
    @Test
    public void notGivenMandatoryParamAccountIdWithPrivileges_whenFetchStatement_then400() throws Exception {
        mockMvc.perform(get("/accountStatement/fetch").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @WithMockUser(value = "testadmin")
    @Test
    public void givenMandatoryParamAccountIdWithPrivileges_whenFetchStatement_thenOk() throws Exception {
        mockMvc.perform(
                get("/accountStatement/fetch")
                        .param("accountId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "testadmin")
    @Test
    public void givenCorrectParamsWithPrivileges_whenFetchStatement_thenOk() throws Exception {
        mockMvc.perform(
                get("/accountStatement/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("accountId", "1")
                        .param("fromDate", "03/03/2012")
                        .param("toDate", "03/03/2012")
                        .param("fromAmount", "300")
                        .param("toAmount", "700"))
                .andExpect(status().isOk());

    }

    @WithMockUser(value = "testadmin")
    @Test
    public void givenIncorrectParamsWithPrivileges_whenFetchStatement_then400() throws Exception {
        mockMvc.perform(
                get("/accountStatement/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("accountId", "1")
                        .param("fromDate", "03/04/2012")
                        .param("toDate", "03/03/2012")
                        .param("fromAmount", "300")
                        .param("toAmount", "700"))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser(value = "testuser")
    @Test
    public void givenCorrectParamsWithLessPrivileges_whenFetchStatement_then401() throws Exception {
        mockMvc.perform(
                get("/accountStatement/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("accountId", "1")
                        .param("fromDate", "03/03/2012")
                        .param("toDate", "03/03/2012")
                        .param("fromAmount", "300")
                        .param("toAmount", "700"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "testuser")
    @Test
    public void givenCorrectParamsWithLessPrivileges_whenFetchStatement_thenOk() throws Exception {
        mockMvc.perform(
                get("/accountStatement/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("accountId", "1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "testadmin")
    @Test
    public void givenCorrectParamsWithPrivilegesInternalServerError_whenFetchStatement_then500() throws Exception {
        when(accountService.fetchAccountStatement(accountStatementReqDTO)).thenThrow(new RuntimeException());
        mockMvc.perform(
                get("/accountStatement/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("accountId", "1")
                        .param("fromDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .param("toDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .param("fromAmount", "300")
                        .param("toAmount", "700"))
                .andExpect(status().isInternalServerError());

    }

}
