package com.jyjun.simplemarket2.application.product.controller;

import com.jyjun.simplemarket2.application.product.dto.ProductReq;
import com.jyjun.simplemarket2.application.product.dto.ProductRes;
import com.jyjun.simplemarket2.application.product.dto.ProductUpdateReq;
import com.jyjun.simplemarket2.domain.product.usecase.ProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @GetMapping
    public ResponseEntity<List<ProductRes>> getProductAll() {
        return ResponseEntity.ok(productUseCase.getProductAll()
                        .parallelStream()
                        .map(ProductRes::of)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRes> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ProductRes.of(productUseCase.getProductById(id))
        );
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductReq req) {
        productUseCase.createProduct(req);
        return ResponseEntity.ok("상품 생성 완료");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductInfo(@PathVariable Long id, @RequestBody @Valid ProductUpdateReq req) {
        productUseCase.updateProductInfo(id, req);
        return ResponseEntity.ok("상품 정보 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.ok("상품 삭제 완료");
    }

}
