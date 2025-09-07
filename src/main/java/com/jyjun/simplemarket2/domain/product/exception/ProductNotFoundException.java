package com.jyjun.simplemarket2.domain.product.exception;

import com.jyjun.simplemarket2.core.exception.CustomException;

public class ProductNotFoundException extends CustomException {

    public ProductNotFoundException() {
        super(ProductExceptionCode.PRODUCT_NOT_FOUND);
    }   

}
