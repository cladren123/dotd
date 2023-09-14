package com.dotd.product.service.product;


import com.dotd.product.dto.ProductRegistDto;
import com.dotd.product.dto.ProductResponseDto;
import com.dotd.product.dto.ProductUpdateDto;
import com.dotd.product.entity.Product;
import com.dotd.product.mapper.ProductMapper;
import com.dotd.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RedisTemplate<String, Product> redisTemplate;




    // 상품 등록
    @Override
    public ProductResponseDto regist(ProductRegistDto dto) {
        Product product = productMapper.productRegistDtoToProduct(dto);
        Product save = productRepository.save(product);
        return productMapper.productToProductResponseDto(save);

    }

    // 상품 상세 조회
    @Override
    public ProductResponseDto findById(Integer id) {
        Product product = productRepository.findById(id).get();
        return productMapper.productToProductResponseDto(product);
    }




    // 상품 상세 조회 Cache Aside 사용
    @Override
    public ProductResponseDto findByIdCacheAside(Integer id) {

        // 캐시 확인
        Product productRedis = redisTemplate.opsForValue().get("product" + id);
        if(productRedis != null) {
            return productMapper.productToProductResponseDto(productRedis);
        }
        else {
            Product productMySQL = productRepository.findById(id).get();

            // redis 에 캐싱
            redisTemplate.opsForValue().set("product" + productMySQL.getId(), productMySQL);

            return productMapper.productToProductResponseDto(productMySQL);
        }
    }



    // 상품 상세 조회 Read-Through
    // @Cacheable은 return을 캐시로 저장한다.
    @Override
    @Cacheable(value = "productResponseDto", key = "#id")
    public ProductResponseDto findByIdReadThrough(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        ProductResponseDto dto = productMapper.productToProductResponseDto(product);
        return dto;
    }


    // 상품 변경
    public ProductResponseDto updateProduct(ProductUpdateDto dto) {
        Product product = productRepository.findById(dto.getId()).orElse(null);

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        ProductResponseDto result = productMapper.productToProductResponseDto(product);
        return result;
    }

    // 상품 등록 wrtie-through
    public ProductResponseDto registProductWriteThroguh(ProductRegistDto dto) {
        Product product = productMapper.productRegistDtoToProduct(dto);

        // MySQL에 저장
        Product save = productRepository.save(product);

        // Redis에 저장
        redisTemplate.opsForValue().set("product" + save.getId(), save);

        ProductResponseDto result = productMapper.productToProductResponseDto(save);
        return result;
    }

    // 상품 변경 write-through
    public ProductResponseDto updateProductWriteThrough(ProductUpdateDto dto) {

        // MySQL Product 조회
        Product product = productRepository.findById(dto.getId()).orElseThrow(
                ()-> new RuntimeException("Product 조회 실패")
        );

        // Product 수정
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        Product save = productRepository.save(product);

        // 캐시 수정
        redisTemplate.opsForValue().set("product" + product.getId(), save);

        ProductResponseDto result = productMapper.productToProductResponseDto(product);

        return result;
    }


    // 상품 삭제 write-through
    public void deleteProductWriteThrough(Integer id) {

        // MySQL에서 Product 삭제
        productRepository.deleteById(id);

        // Redis에서 Product 삭제
        redisTemplate.delete("product" + id);
    }








}
