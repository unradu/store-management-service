package com.demo.store.integration;

import com.demo.store.dto.GenerateOrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class OrderControllerIT extends BaseIT {
	@Value("${test.users.salesAssociate.username}")
	private String salesAssociateUsername;

	@Value("${test.users.salesAssociate.password}")
	private String salesAssociatePassword;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void generateOrder_success() throws Exception {
		GenerateOrderDTO generateOrderDTO = new GenerateOrderDTO();
		generateOrderDTO.setProductId(1L);
		generateOrderDTO.setQuantity(2);
		generateOrderDTO.setBuyerEmail("test@addr.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
				.with(SecurityMockMvcRequestPostProcessors.httpBasic(salesAssociateUsername, salesAssociatePassword))
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(generateOrderDTO)))
				.andExpect(status().isOk());
	}

	@Test
	void generateOrder_unauthorized() throws Exception {
		GenerateOrderDTO generateOrderDTO = new GenerateOrderDTO();
		generateOrderDTO.setProductId(1L);
		generateOrderDTO.setQuantity(2);
		generateOrderDTO.setBuyerEmail("test@addr.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(generateOrderDTO)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void generateOrder_notFound() throws Exception {
		GenerateOrderDTO generateOrderDTO = new GenerateOrderDTO();
		generateOrderDTO.setProductId(999L);
		generateOrderDTO.setQuantity(2);
		generateOrderDTO.setBuyerEmail("test@addr.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
						.with(SecurityMockMvcRequestPostProcessors.httpBasic(salesAssociateUsername, salesAssociatePassword))
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(generateOrderDTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	void generateOrderForUnavailableProduct_badRequest() throws Exception {
		GenerateOrderDTO generateOrderDTO = new GenerateOrderDTO();
		generateOrderDTO.setProductId(2L);
		generateOrderDTO.setQuantity(2);
		generateOrderDTO.setBuyerEmail("test@addr.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
						.with(SecurityMockMvcRequestPostProcessors.httpBasic(salesAssociateUsername, salesAssociatePassword))
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(generateOrderDTO)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void generateOrderForOutOfStockProduct_badRequest() throws Exception {
		GenerateOrderDTO generateOrderDTO = new GenerateOrderDTO();
		generateOrderDTO.setProductId(3L);
		generateOrderDTO.setQuantity(2);
		generateOrderDTO.setBuyerEmail("test@addr.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
						.with(SecurityMockMvcRequestPostProcessors.httpBasic(salesAssociateUsername, salesAssociatePassword))
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(generateOrderDTO)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void generateOrderForInsufficientStock_badRequest() throws Exception {
		GenerateOrderDTO generateOrderDTO = new GenerateOrderDTO();
		generateOrderDTO.setProductId(1L);
		generateOrderDTO.setQuantity(50);
		generateOrderDTO.setBuyerEmail("test@addr.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
						.with(SecurityMockMvcRequestPostProcessors.httpBasic(salesAssociateUsername, salesAssociatePassword))
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(generateOrderDTO)))
				.andExpect(status().isBadRequest());
	}

}
