package com.dotd.product.service.product;


import com.dotd.product.dto.ProductRegistDto;
import com.dotd.product.dto.ProductResponseDto;
import com.dotd.product.dto.ProductUpdateDto;
import com.dotd.product.entity.Product;
import com.dotd.product.mapper.ProductMapper;
import com.dotd.product.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductWriteBackService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RedisTemplate<String, Product> redisTemplate;


    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper; // Jackson의 ObjectMapper
    @PersistenceContext  // EntityManager를 스프링 컨테이너에서 주입 받을 때 사용
    private final EntityManager entityManager;




    // 상품 상세 조회
    public ProductResponseDto findByIdWriteBack(Integer id) {

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

    // 상품 등록
    public ProductResponseDto registProductWriteBack(ProductRegistDto dto) {
        Product product = productMapper.productRegistDtoToProduct(dto);

        // MySQL에 저장
        Product save = productRepository.save(product);

        // Redis에 저장
        redisTemplate.opsForValue().set("product" + save.getId(), save);

        ProductResponseDto result = productMapper.productToProductResponseDto(save);
        return result;
    }

    // 상품 수정
    public ProductResponseDto updateProductWriteBack(ProductUpdateDto dto) {

        // MySQL Product 조회
        Product product = productRepository.findById(dto.getId()).orElseThrow(
                ()-> new RuntimeException("Product 조회 실패")
        );

        // Product 수정 전 detach -> DB에 반영 안됨
        entityManager.detach(product);

        // Product 수정
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());


        // 캐시 수정
        redisTemplate.opsForValue().set("product" + product.getId(), product);

        // 지연 정보 추가
        JsonNode productJsonNode = objectMapper.valueToTree(product);
        Operation operation = new Operation("UPDATE", productJsonNode);
        String serializedData = operationSerialize(operation);
        stringRedisTemplate.opsForList().rightPush("productOperationList", serializedData);



        // 응답
        ProductResponseDto result = productMapper.productToProductResponseDto(product);

        return result;
    }


    // 상품 삭제
    public void deleteProductWriteBack(Integer id) {
        // Redis에서 Product 삭제
        redisTemplate.delete("product" + id);

        // 지연 정보 추가
        JsonNode idJsonNode = objectMapper.valueToTree(id);
        Operation operation = new Operation("DELETE", idJsonNode);
        String serializedData = operationSerialize(operation);
        stringRedisTemplate.opsForList().rightPush("productOperationList", serializedData);
    }


    // 지연 정보 처리 메소드 - 5분 마다 실행
    @Scheduled(fixedRate = 300000)
    public void processOperationList() throws JsonProcessingException {
        while (true) {
            String serializedData = stringRedisTemplate.opsForList().leftPop("productOperationList");

            if(serializedData == null) {
                break;
            }

            Operation operation = operationDeserialize(serializedData);

            if (operation.getType().equals("UPDATE")) {
                Product data = objectMapper.treeToValue(operation.getData(), Product.class);
                Product product = productRepository.findById(data.getId()).orElse(null);
                product.setName(data.getName());
                product.setDescription(data.getDescription());
                product.setPrice(data.getPrice());
                productRepository.save(product);
            }
            else if (operation.getType().equals("DELETE")) {
                Integer data =  operation.getData().asInt();
                productRepository.deleteById(data);
            }
        }
    }





    // Redis에 지연 정보를 저장할 Operation 클래스
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Operation {
        private String type;
        private JsonNode data;


    }


    // Operation 직렬화
    public String operationSerialize(Operation operation) {
        try {
            return objectMapper.writeValueAsString(operation);
        } catch (Exception e) {
            throw new RuntimeException("직렬화 실패", e);
        }
    }

    // Operation 역직렬화
    public Operation operationDeserialize(String serializedData) {
        try {
            return objectMapper.readValue(serializedData, Operation.class);
        } catch (Exception e) {
            throw new RuntimeException("역직렬화 실패", e);
        }
    }









}
