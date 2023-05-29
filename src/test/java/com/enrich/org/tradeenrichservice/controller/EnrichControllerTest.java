package com.enrich.org.tradeenrichservice.controller;

import com.enrich.org.tradeenrichservice.controller.EnrichController;
import com.enrich.org.tradeenrichservice.service.EnrichService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrichController.class)
public class EnrichControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrichService enrichService;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(enrichService.enrichTradeData(anyString())).thenReturn("enriched");
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/enrich")
                        .content("20200102,2,USD,15.2")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("enriched")));
    }
}
