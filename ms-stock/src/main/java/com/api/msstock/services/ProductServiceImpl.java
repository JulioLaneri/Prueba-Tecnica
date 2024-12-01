package com.api.msstock.services;

import com.api.msstock.daos.ProductDao;
import com.api.msstock.dtos.ProductDto;
import com.api.msstock.entitys.ProductEntity;
import com.api.msstock.exceptions.BadRequestException;
import com.api.msstock.exceptions.NotFoundException;
import com.api.msstock.interfaces.IProductService;
import com.api.msstock.utils.mappers.ProductMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {
    private final ProductDao productDao;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ProductMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto product) {
        productDao.save(productMapper.toEntity(product));
        return product;
    }

    @Override
    public ProductDto getProduct(String productId) {
        Optional<ProductEntity> product = productDao.findById(productId);
        if (product.isPresent()) {
            return productMapper.toDto(product.get());
        } else {
            throw new NotFoundException("Producto no encontrado");
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductEntity> products = productDao.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }


    @Override
    public ProductDto updateStock(String productId, Integer quantity) {
        ProductDto product = getProduct(productId);
        if (product.getQuantity() < quantity){
            throw new BadRequestException("Stock insuficiente");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productDao.save(productMapper.toEntity(product));
        return product;
    }
}
