package com.dotd.product.redis.repository;

import com.dotd.product.redis.entity.Desk;
import org.springframework.data.repository.CrudRepository;

public interface DeskRedisRepository extends CrudRepository<Desk, String> {
}
