package org.binaracademy.challenge_5.response.entity;

import lombok.Data;

@Data
public class ProductResponse {

    private String productName;
    private Integer quantity;
    private Long totalPrice;
}
