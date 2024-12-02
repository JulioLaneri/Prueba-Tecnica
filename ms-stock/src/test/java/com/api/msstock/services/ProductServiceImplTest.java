package com.api.msstock.services;

import com.api.msstock.daos.ProductDao;
import com.api.msstock.dtos.ProductDto;
import com.api.msstock.entitys.ProductEntity;
import com.api.msstock.exceptions.BadRequestException;
import com.api.msstock.exceptions.NotFoundException;
import com.api.msstock.utils.mappers.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId("1");
        productDto.setProductName("Producto 1");
        productDto.setQuantity(10);

        ProductEntity productEntity = new ProductEntity();

        when(productMapper.toEntity(productDto)).thenReturn(productEntity);

        productService.createProduct(productDto);

        verify(productDao, times(1)).save(productEntity);
    }

    @Test
    void testGetProductFound() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId("1");

        ProductDto productDto = new ProductDto();
        productDto.setProductId("1");

        when(productDao.findById("1")).thenReturn(Optional.of(productEntity));
        when(productMapper.toDto(productEntity)).thenReturn(productDto);

        ProductDto result = productService.getProduct("1");

        assertNotNull(result);
        assertEquals("1", result.getProductId());
    }

    @Test
    void testGetProductNotFound() {
        when(productDao.findById("1")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getProduct("1"));
    }

    @Test
    void testGetAllProductsFound() {
        ProductEntity productEntity = new ProductEntity();
        ProductDto productDto = new ProductDto();

        when(productDao.findAll()).thenReturn(List.of(productEntity));
        when(productMapper.toDto(productEntity)).thenReturn(productDto);

        List<ProductDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllProductsNotFound() {
        when(productDao.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, productService::getAllProducts);
    }

    @Test
    void testUpdateStockSufficient() {
        ProductEntity productEntity = new ProductEntity();
        ProductDto existingProductDto = new ProductDto();
        existingProductDto.setProductId("1");
        existingProductDto.setQuantity(10);

        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto.setProductId("1");
        updatedProductDto.setQuantity(5);

        when(productDao.findById("1")).thenReturn(Optional.of(productEntity));
        when(productMapper.toDto(productEntity)).thenReturn(existingProductDto);
        when(productMapper.toEntity(updatedProductDto)).thenReturn(productEntity);

        ProductDto result = productService.updateStock(updatedProductDto);

        assertNotNull(result);
        assertEquals(5, result.getQuantity());
        verify(productDao, times(1)).save(productEntity);
    }

    @Test
    void testUpdateStockInsufficient() {
        ProductEntity productEntity = new ProductEntity();
        ProductDto existingProductDto = new ProductDto();
        existingProductDto.setProductId("1");
        existingProductDto.setQuantity(10);

        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto.setProductId("1");
        updatedProductDto.setQuantity(15);

        when(productDao.findById("1")).thenReturn(Optional.of(productEntity));
        when(productMapper.toDto(productEntity)).thenReturn(existingProductDto);

        assertThrows(BadRequestException.class, () -> productService.updateStock(updatedProductDto));
    }
}