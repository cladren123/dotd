package com.dotd.product.redis.service;


import com.dotd.product.redis.entity.Desk;
import com.dotd.product.redis.repository.DeskRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeskRedisService {

    private final DeskRedisRepository deskRedisRepository;

    public void addDesk(Desk desk) {
        deskRedisRepository.save(desk);
    }

    public Desk getDesk(String id) {
        return deskRedisRepository.findById(id).orElse(null);
    }
}
