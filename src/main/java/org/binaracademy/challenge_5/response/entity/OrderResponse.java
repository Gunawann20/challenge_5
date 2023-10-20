package org.binaracademy.challenge_5.response.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.binaracademy.challenge_5.entity.OrderDetail;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long orderId;
    private String destination;
    private LocalDateTime time;
    private Boolean isCompleted;
    private List<OrderDetail> orderDetails;
}
