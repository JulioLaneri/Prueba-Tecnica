package com.api.msstock.entitys;

import com.api.msstock.abstracts.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.UniqueElements;

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
