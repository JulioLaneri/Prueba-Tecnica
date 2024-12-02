package com.api.msventas.services;

import com.api.msventas.dtos.ProductDto;
import com.api.msventas.exceptions.BadRequestException;
import com.api.msventas.utils.rabbitmq.RabbitMqSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentaServiceImplTest {

    @Mock
    private RabbitMqSender rabbitMqSender;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VentaServiceImpl ventaService;

    private final String msStockBaseUrl = "http://ms-stock/api/products";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ventaService.msStockBaseUrl = msStockBaseUrl;
    }

    @Test
    void getAllProducts_ReturnsProductsList() {
        ProductDto[] mockProducts = {
                new ProductDto("1", "Producto 1", 10),
                new ProductDto("2", "Producto 2", 5)
        };

        when(restTemplate.getForEntity(msStockBaseUrl, ProductDto[].class))
                .thenReturn(new ResponseEntity<>(mockProducts, HttpStatus.OK));

        List<ProductDto> result = ventaService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Producto 1", result.get(0).getProductName());
        verify(restTemplate, times(1)).getForEntity(msStockBaseUrl, ProductDto[].class);
    }

    @Test
    void getAllProducts_ReturnsEmptyList() {
        when(restTemplate.getForEntity(msStockBaseUrl, ProductDto[].class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        List<ProductDto> result = ventaService.getAllProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(restTemplate, times(1)).getForEntity(msStockBaseUrl, ProductDto[].class);
    }

    @Test
    void getProductById_ReturnsProduct() {
        String productId = "1";
        ProductDto mockProduct = new ProductDto(productId, "Producto 1", 10);

        when(restTemplate.getForEntity(msStockBaseUrl + "/" + productId, ProductDto.class))
                .thenReturn(new ResponseEntity<>(mockProduct, HttpStatus.OK));

        ProductDto result = ventaService.getProductById(productId);

        assertNotNull(result);
        assertEquals("Producto 1", result.getProductName());
        verify(restTemplate, times(1)).getForEntity(msStockBaseUrl + "/" + productId, ProductDto.class);
    }

    @Test
    void procesarVenta_SuccessfulSale() {
        ProductDto inputProduct = new ProductDto("1", "Producto 1", 2);
        ProductDto existingProduct = new ProductDto("1", "Producto 1", 10);

        when(restTemplate.getForEntity(msStockBaseUrl + "/1", ProductDto.class))
                .thenReturn(new ResponseEntity<>(existingProduct, HttpStatus.OK));

        doNothing().when(rabbitMqSender).enviarVenta(inputProduct);

        String result = ventaService.procesarVenta(inputProduct);

        assertEquals("Se realizó la venta", result);
        verify(restTemplate, times(1)).getForEntity(msStockBaseUrl + "/1", ProductDto.class);
        verify(rabbitMqSender, times(1)).enviarVenta(inputProduct);
    }

    @Test
    void procesarVenta_InsufficientStock_ThrowsBadRequestException() {
        ProductDto inputProduct = new ProductDto("1", "Producto 1", 15);
        ProductDto existingProduct = new ProductDto("1", "Producto 1", 10);

        when(restTemplate.getForEntity(msStockBaseUrl + "/1", ProductDto.class))
                .thenReturn(new ResponseEntity<>(existingProduct, HttpStatus.OK));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> ventaService.procesarVenta(inputProduct));

        assertEquals("Stock insuficiente", exception.getMessage());
        verify(restTemplate, times(1)).getForEntity(msStockBaseUrl + "/1", ProductDto.class);
        verifyNoInteractions(rabbitMqSender);
    }
}
