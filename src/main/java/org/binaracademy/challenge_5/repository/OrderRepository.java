package org.binaracademy.challenge_5.repository;

import org.binaracademy.challenge_5.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdAndIsCompleted(Long userId, Boolean isCompleted);
    Order findByIdAndIsCompleted(Long orderId, Boolean isCompleted);

}
