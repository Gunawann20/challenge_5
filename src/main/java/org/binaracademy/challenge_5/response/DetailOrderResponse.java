package org.binaracademy.challenge_5.response;

import lombok.Data;

@Data
public class DetailOrderResponse {

    private String productName;
    private Integer quantity;
    private Long totalPrice;

}
