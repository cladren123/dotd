package com.dotd.product.redis.service;


import com.dotd.product.dto.ProductRegistDto;
import com.dotd.product.dto.ProductResponseDto;
import com.dotd.product.entity.Product;
import com.dotd.product.mapper.ProductMapper;
import com.dotd.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/*

RedisTemplate 을 이용한 객체 저장, 조회

 */

@Service
@RequiredArgsConstructor
public class ProductRedisService {

    private final RedisTemplate<String, Product> redisTemplate;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Redis 저장
    public ProductResponseDto saveProduct(ProductRegistDto dto) {
        Product product = productMapper.productRegistDtoToProduct(dto);
        Product save = productRepository.save(product);

        // redis 저장
        redisTemplate.opsForValue().set("product" + save.getId(), save);


        ProductResponseDto result = productMapper.productToProductResponseDto(save);

        return result;
    }

    // Redis 조회
    public ProductResponseDto getProduct(String id) {
        Product product = (Product) redisTemplate.opsForValue().get("product" + id);
        ProductResponseDto dto = productMapper.productToProductResponseDto(product);
        return dto;
    }


}
