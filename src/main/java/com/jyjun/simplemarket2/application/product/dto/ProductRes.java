package com.jyjun.simplemarket2.application.product.dto;

import com.jyjun.simplemarket2.domain.product.entity.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record ProductRes(
        Long id,
        String name,
        Long price,
        Long stock
) {
    public static ProductRes of(Product product) {
        return new ProductRes(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}
