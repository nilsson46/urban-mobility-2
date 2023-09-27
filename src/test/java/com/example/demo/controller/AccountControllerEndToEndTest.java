package com.example.demo.controller;

import com.example.demo.dto.AccountDto;
import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountControllerEndToEndTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    private Account account;
    private String jsonAccount;

    @BeforeEach
    public void setUp() throws JsonProcessingException {

        accountRepository.deleteAll();
        // Create and save a test account to the database
        account = Account.builder()
                .id(1L)
                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .paymentConfirmed(true)
                .paymentHistory(0)
                //.activeOrders(0)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        jsonAccount = objectMapper.writeValueAsString(account);
    }

    //More tests and check the status code.
    @Test
    void Should_CreateAccount_ReturnAccount() throws  Exception{

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
    void Should_UpdateAccount_ReturnAccount() throws Exception {

        accountRepository.save(account);
        // Update the account's username and email
        account.setUsername("Simon");
        account.setEmail("simon@gmail.com");

        // Convert the updated account to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String updatedJsonAccount = objectMapper.writeValueAsString(account);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/account/{id}", account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJsonAccount) // Use the JSON data with updated values
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account.id").value(account.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account.username").value("Simon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account.email").value("simon@gmail.com"));
    }
    @Test
    public void Should_DeleteAccount() throws Exception{
        accountRepository.save(account);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/account/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Account was deleted successfully"));
    }
}