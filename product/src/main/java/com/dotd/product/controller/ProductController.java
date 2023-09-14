package com.dotd.product.controller;


import com.dotd.product.dto.ProductRegistDto;
import com.dotd.product.dto.ProductResponseDto;
import com.dotd.product.dto.ProductUpdateDto;
import com.dotd.product.entity.Product;
import com.dotd.product.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody ProductRegistDto dto) {
        ProductResponseDto result = productService.regist(dto);
        return ResponseEntity.ok(result);
    }

    // 상품 조회
    @GetMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam(name = "id") Integer id) {
        ProductResponseDto result = productService.findById(id);
        return ResponseEntity.ok(result);
    }

    // 상품 상세 조회 Cache Aside 사용
    @GetMapping("/findByIdCacheAside")
    public ResponseEntity<?> findByIdCacheAside(@RequestParam(name = "id") Integer id) {
        ProductResponseDto result = productService.findByIdCacheAside(id);
        return ResponseEntity.ok(result);
    }

    // 상품 상세 조회 Read-Through
    @GetMapping("/findByIdReadThrough")
    public ResponseEntity<?> findByIdReadThrough(@RequestParam(name = "id") Integer id) {
        ProductResponseDto result = productService.findByIdReadThrough(id);
        return ResponseEntity.ok(result);
    }


    // 상품 변경
    @PatchMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateDto dto) {
        ProductResponseDto result = productService.updateProduct(dto);
        return ResponseEntity.ok(result);
    }

    // 상품 등록 Write-Through
    @PostMapping("/registProductWriteThroguh")
    public ResponseEntity<?> registProductWriteThroguh(@RequestBody ProductRegistDto dto) {
        ProductResponseDto result = productService.registProductWriteThroguh(dto);
        return ResponseEntity.ok(result);
    }


    // 상품 변경 Write-Through
    @PatchMapping("/updateProductWriteThrough")
    public ResponseEntity<?> updateProductWriteThrough(@RequestBody ProductUpdateDto dto) {
        ProductResponseDto result = productService.updateProductWriteThrough(dto);
        return ResponseEntity.ok(result);
    }

    // 상품 삭제 write-through
    @DeleteMapping("/deleteProductWriteThrough/{id}")
    public ResponseEntity<?> deleteProductWriteThrough(@PathVariable Integer id) {
        productService.deleteProductWriteThrough(id);
        return ResponseEntity.ok("ok");
    }






}
