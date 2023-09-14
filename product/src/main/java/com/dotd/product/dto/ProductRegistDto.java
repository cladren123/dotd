package com.dotd.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegistDto {

    private String sellerId;

    private Integer categoryId;

    private String code;
    private String name;
    private Integer price;
    private String description;

}
