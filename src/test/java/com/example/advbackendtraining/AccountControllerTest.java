package com.example.advbackendtraining;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    void shouldCreateAccount() throws Exception {

        Account acc = new Account();
        acc.setId(1L);
        acc.setName("Navtej");
        acc.setBalance(3000);

        when(accountService.save(any())).thenReturn(acc);

        mockMvc.perform(post("/accounts")
                        .contentType("application/json")
                        .content("""
                        {
                          "name": "Navtej",
                          "balance": 3000
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Navtej"))
                .andExpect(jsonPath("$.balance").value(3000));
    }
}