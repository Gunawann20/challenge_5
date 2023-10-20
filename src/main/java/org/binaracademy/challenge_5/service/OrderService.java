package org.binaracademy.challenge_5.service;

import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.binaracademy.challenge_5.response.entity.OrderResponse;

import java.util.List;

public interface OrderService {
    GeneralResponse createOrder(Long userId, Long productId, Integer quantity);
    GeneralResponse updateOrder(Long orderId, Long productId, Integer quantity);
    Response<List<OrderResponse>> listOrder(Long userId);
    Response<List<OrderResponse>> historyOrder(Long userId);
    Response<byte []> makeOrder(Long orderId);
}
