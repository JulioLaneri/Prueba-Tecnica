package com.api.msstock.daos;

import com.api.msstock.entitys.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<ProductEntity, String> {
}
