package com.dotd.product.controller;


import com.dotd.product.dto.ProductRegistDto;
import com.dotd.product.dto.ProductResponseDto;
import com.dotd.product.dto.ProductUpdateDto;
import com.dotd.product.service.product.ProductWriteBackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/productWriteBack")
@Slf4j
public class ProductWriteBackController {

    private final ProductWriteBackService productWriteBackService;


    // 상품 상세 조회 Read-Through
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findByIdReadThrough(@PathVariable Integer id) {
        ProductResponseDto result = productWriteBackService.findByIdWriteBack(id);
        return ResponseEntity.ok(result);
    }


    // 상품 등록 Write-Through
    @PostMapping("/regist")
    public ResponseEntity<?> registProductWriteThroguh(@RequestBody ProductRegistDto dto) {
        ProductResponseDto result = productWriteBackService.registProductWriteBack(dto);
        return ResponseEntity.ok(result);
    }


    // 상품 변경 Write-Through
    @PatchMapping("/update")
    public ResponseEntity<?> updateProductWriteThrough(@RequestBody ProductUpdateDto dto) {
        ProductResponseDto result = productWriteBackService.updateProductWriteBack(dto);
        return ResponseEntity.ok(result);
    }

    // 상품 삭제 write-through
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductWriteThrough(@PathVariable Integer id) {
        productWriteBackService.deleteProductWriteBack(id);
        return ResponseEntity.ok("ok");
    }


    // 지연 정보 실행
    @GetMapping("/processOperationList")
    public ResponseEntity<?> processOperationList() throws JsonProcessingException {
        productWriteBackService.processOperationList();
        return ResponseEntity.ok("ok");
    }





}
