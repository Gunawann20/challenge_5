package org.binaracademy.challenge_5.repository;

import org.binaracademy.challenge_5.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByMerchant_IsOpen(Boolean isOpen, Pageable pageable);
}
