package org.binaracademy.challenge_5.repository;

import org.binaracademy.challenge_5.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Page<Merchant> findAllByIsOpen(Boolean isOpen, Pageable pageable);
}
