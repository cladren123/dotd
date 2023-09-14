package com.dotd.product.mapper;


import com.dotd.product.dto.ProductRegistDto;
import com.dotd.product.dto.ProductResponseDto;
import com.dotd.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product productRegistDtoToProduct(ProductRegistDto dto) {
        return Product.builder()
                .sellerId(dto.getSellerId())
                .categoryId(dto.getCategoryId())
                .code(dto.getCode())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }

    public ProductResponseDto productToProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .sellerId(product.getSellerId())
                .categoryId(product.getCategoryId())
                .code(product.getCode())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .viewCount(product.getViewCount())
                .orderCount(product.getOrderCount())
                .likeCount(product.getLikeCount())
                .reviewCount(product.getReviewCount())
                .createdAt(product.getCreatedAt())
                .build();
    }

}
