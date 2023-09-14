package com.dotd.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements Serializable {

    // 직렬화 버전
    private static final long serialVersionUID = 1L;


    private Integer id;
    private String sellerId;
    private Integer categoryId;
    private String code;
    private String name;
    private Integer price;
    private String description;
    private Integer viewCount = 0;
    private Integer orderCount = 0;
    private Integer likeCount = 0;
    private Integer reviewCount = 0;
    private LocalDateTime createdAt;


}
