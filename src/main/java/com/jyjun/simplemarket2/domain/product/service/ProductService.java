package com.jyjun.simplemarket2.domain.product.service;

import com.jyjun.simplemarket2.application.product.dto.ProductReq;
import com.jyjun.simplemarket2.application.product.dto.ProductUpdateReq;
import com.jyjun.simplemarket2.domain.product.entity.Product;
import com.jyjun.simplemarket2.domain.product.exception.ProductNotFoundException;
import com.jyjun.simplemarket2.domain.product.usecase.ProductUseCase;
import com.jyjun.simplemarket2.persistence.product.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepo;

    @Override
    public List<Product> getProductAll() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void createProduct(ProductReq req) {
        productRepo.save(Product.of(req));
    }

    @Override
    public void updateProductInfo(Long id, ProductUpdateReq req) {
        Product product = getProductById(id);
        product.updateInfo(req.name(), req.price());
        productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.delete(getProductById(id));
    }
}
