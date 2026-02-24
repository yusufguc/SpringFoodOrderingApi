package com.yusufguc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private  Long id;

    private  String name;

    private String description;

    private BigDecimal price;

    private  Integer stock;

    private  boolean active;
}
