package com.yusufguc.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class ProductResponse {

    private  Long id;

    private  String name;

    private String description;

    private BigDecimal price;

    private  Integer stock;

    private  boolean active;
}
