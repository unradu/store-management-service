package com.demo.store.integration;

import com.demo.store.dto.ProductResultDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ProductControllerIT extends BaseIT {
    @Value("${test.users.admin.username}")
    private String adminUsername;

    @Value("${test.users.admin.password}")
    private String adminPassword;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getProduct_success() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/1")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(adminUsername, adminPassword))
                        .contentType("application/json"))
                        .andExpect(status().isOk())
                        .andReturn();

        ProductResultDTO resultDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                ProductResultDTO.class);
        assertNotNull(resultDTO);
        assertEquals(1L, resultDTO.getId());
    }
}
