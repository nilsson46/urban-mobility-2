package com.example.demo.controller;

import com.example.demo.dto.AccountUpdateRequest;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.websocket.server.UriTemplate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerEndToEndTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    private Account account;

    /*@BeforeEach
    public void setUp() {
        // Create and save a test account to the database
        account = Account.builder()
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .isPaymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
                .build();
        accountRepository.save(account);
    }
 */

    @AfterEach
    void cleanUp(){
        accountRepository.deleteAll();
    }
    @Test
    void Should_CreateAccount_ReturnAccount() throws  Exception{

        Account account = Account.builder()
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .isPaymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAccount = objectMapper.writeValueAsString(account);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAccount)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.username", Matchers.is("kuro")))
                .andExpect(jsonPath("$.email", Matchers.is("kuro@gmail.com")));
    }

    @Test
    void Should_UpdateAccount_ReturnAccount() throws  Exception{

        Account account = Account.builder()
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .isPaymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
                .build();

        Account createdAccount = accountService.createAccount(account);

        AccountUpdateRequest updateRequest = new AccountUpdateRequest();
        updateRequest.setUsername("Simon");
        updateRequest.setEmail("simon@gmail.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUpdateRequest = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/account/{id}", createdAccount.getId()) // Use the ID of the created account
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account.id").value(createdAccount.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account.username").value("Simon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account.email").value("simon@gmail.com"));
    }




}