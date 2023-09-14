package com.dotd.product.service.product;

import com.dotd.product.dto.ProductRegistDto;
import com.dotd.product.dto.ProductResponseDto;
import com.dotd.product.dto.ProductUpdateDto;
import com.dotd.product.entity.Product;

public interface ProductService {

    // 상품 등록
    public ProductResponseDto regist(ProductRegistDto dto);

    // 상품 조회
    public ProductResponseDto findById(Integer id);



    // 상품 상세 조회 Cache-Aside(Lazy Loading)
    public ProductResponseDto findByIdCacheAside(Integer id);

    // 상품 상세 조회 Read-Through
    public ProductResponseDto findByIdReadThrough(Integer id);

    // 상품 변경
    public ProductResponseDto updateProduct(ProductUpdateDto dto);

    // 상품 등록 wrtie-through
    public ProductResponseDto registProductWriteThroguh(ProductRegistDto dto);

    // 상품 변경 write-through
    public ProductResponseDto updateProductWriteThrough(ProductUpdateDto dto);

    // 상품 삭제 write-through
    public void deleteProductWriteThrough(Integer id);



}
