package com.jyjun.simplemarket2.domain.product.entity;

import com.jyjun.simplemarket2.application.product.dto.ProductReq;
import com.jyjun.simplemarket2.core.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    public String name;

    public Long price;

    public Long stock;

    public static Product of(ProductReq req) {
        return Product.builder()
                .name(req.name())
                .price(req.price())
                .stock(req.stock())
                .build();
    }

    public void updateInfo(String name, Long price) {
        this.name = name;
        this.price = price;
    }

}