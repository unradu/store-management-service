package com.demo.store.unit;

import com.demo.store.dto.ProductPriceChangeDTO;
import com.demo.store.dto.ProductResultDTO;
import com.demo.store.dto.mapper.ProductMapper;
import com.demo.store.exception.custom.ResourceNotFoundException;
import com.demo.store.model.Product;
import com.demo.store.model.ProductState;
import com.demo.store.repository.ProductRepository;
import com.demo.store.service.ProductPricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductPricingServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductPricingService productPricingService;

    private Product existingProduct;

    @BeforeEach
    void setUpExistingProduct() {
        existingProduct = Product.builder()
                .id(1L)
                .price(new BigDecimal("50.00"))
                .name("test product")
                .description("some description...")
                .quantity(10)
                .state(ProductState.AVAILABLE)
                .build();
    }

    @Test
    void changePriceWithDiscount_works() {
        BigDecimal newPrice = new BigDecimal("20.57");
        Integer newDiscount = 20;
        BigDecimal newDiscountedPrice = new BigDecimal("16.46");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(productMapper.productToProductResultDto(existingProduct))
                .thenReturn(createProductResultDto(existingProduct, newPrice, newDiscountedPrice));

        ProductResultDTO result = productPricingService.changePrice(
                1L, createPriceChangeDto(newPrice, newDiscount));

        verify(productRepository).save(existingProduct);
        assertNotNull(result);
        assertEquals(new BigDecimal("20.57"), result.getPrice());
        assertEquals(new BigDecimal("16.46"), result.getDiscountedPrice());
    }

    @Test
    void changePriceWithoutDiscount_works() {
        BigDecimal newPrice = new BigDecimal("20.57");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(productMapper.productToProductResultDto(existingProduct))
                .thenReturn(createProductResultDto(existingProduct, newPrice, null));

        ProductResultDTO result = productPricingService.changePrice(
                1L, createPriceChangeDto(newPrice, null));

        verify(productRepository).save(existingProduct);
        assertNotNull(result);
        assertEquals(new BigDecimal("20.57"), result.getPrice());
        assertNull(result.getDiscountedPrice());
    }

    @Test
    void changePrice_ProductNotFound() {
        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productPricingService.changePrice(10L,
                createPriceChangeDto(new BigDecimal("10.15"), 10)));
    }

    @Test
    void computeDiscountedPrice() throws Exception {
        BigDecimal fullPrice = new BigDecimal("20.57");
        Integer discount = 20;

        // We need to use reflection to test the private method
        java.lang.reflect.Method method = ProductPricingService.class.getDeclaredMethod("computeDiscountedPrice",
                BigDecimal.class, Integer.class);
        method.setAccessible(true);
        BigDecimal result = (BigDecimal) method.invoke(productPricingService, fullPrice, discount);

        assertEquals(new BigDecimal("16.46"), result);
    }

    private ProductPriceChangeDTO createPriceChangeDto(BigDecimal price, Integer discount) {
        ProductPriceChangeDTO productPriceChangeDto = new ProductPriceChangeDTO();
        productPriceChangeDto.setPrice(price);
        productPriceChangeDto.setDiscount(discount);
        return productPriceChangeDto;
    }

    private ProductResultDTO createProductResultDto(Product product, BigDecimal newPrice, BigDecimal newDiscountedPrice) {
        ProductResultDTO productResultDTO = new ProductResultDTO();
        productResultDTO.setId(product.getId());
        productResultDTO.setName(product.getName());
        productResultDTO.setDescription(product.getDescription());
        productResultDTO.setPrice(newPrice);
        productResultDTO.setDiscountedPrice(newDiscountedPrice);
        productResultDTO.setQuantity(product.getQuantity());
        productResultDTO.setState(product.getState());
        productResultDTO.setVersion(product.getVersion());
        return productResultDTO;
    }
}
