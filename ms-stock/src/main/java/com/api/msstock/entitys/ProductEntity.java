package com.api.msstock.entitys;

import com.api.msstock.abstracts.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "products")
public class ProductEntity extends AbstractEntity {
    @Id
    private String productId;
    private String productName;
    private Integer quantity;


}
