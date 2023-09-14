package com.dotd.product.redis.controller;


import com.dotd.product.dto.ProductRegistDto;
import com.dotd.product.dto.ProductResponseDto;
import com.dotd.product.entity.Product;
import com.dotd.product.redis.entity.Desk;
import com.dotd.product.redis.service.DeskRedisService;
import com.dotd.product.redis.service.ProductRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    private final ProductRedisService productRedisService;
    private final DeskRedisService deskRedisService;


    // 상품 등록
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductRegistDto dto) {

        System.out.println(dto.toString());

        ProductResponseDto result = productRedisService.saveProduct(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam(name = "id") String id) {

        System.out.println(id);

        ProductResponseDto dto = productRedisService.getProduct(id);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/desk/add")
    public ResponseEntity<?> deskAdd(@RequestBody Desk desk) {
        deskRedisService.addDesk(desk);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/desk/get")
    public ResponseEntity<?> deskGet(@RequestParam(name = "id") String id) {
        Desk desk = deskRedisService.getDesk(id);
        return ResponseEntity.ok(desk);
    }

}
