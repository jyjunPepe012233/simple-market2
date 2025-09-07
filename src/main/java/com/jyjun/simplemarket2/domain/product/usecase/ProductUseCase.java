package com.jyjun.simplemarket2.domain.product.usecase;

import com.jyjun.simplemarket2.application.product.dto.ProductReq;
import com.jyjun.simplemarket2.application.product.dto.ProductUpdateReq;
import com.jyjun.simplemarket2.domain.product.entity.Product;
import java.util.List;

public interface ProductUseCase {

    List<Product> getProductAll();

    Product getProductById(Long id);

    void createProduct(ProductReq req);

    void updateProductInfo(Long id, ProductUpdateReq req);

    void deleteProduct(Long id);

}
