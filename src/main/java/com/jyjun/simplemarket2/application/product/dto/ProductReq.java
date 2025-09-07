package com.jyjun.simplemarket2.application.product.dto;

public record ProductReq(
        String name,
        Long price,
        Long stock
) {
}
