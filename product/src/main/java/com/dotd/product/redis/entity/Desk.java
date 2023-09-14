package com.dotd.product.redis.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Data
@RedisHash("desk")
@AllArgsConstructor
@NoArgsConstructor
public class Desk {

    @Id
    private String id;
    private String name;
    private int cost;
    private Long expirationTime;

    @TimeToLive
    public Long getTimeToLive() {
        return expirationTime;
    }

}
