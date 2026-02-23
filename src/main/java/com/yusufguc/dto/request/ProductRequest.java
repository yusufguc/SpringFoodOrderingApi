package com.yusufguc.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
public class ProductRequest {


    private  String name;

    private String description;

    private BigDecimal price;

    private  Integer stock;

    private  boolean active;
}
