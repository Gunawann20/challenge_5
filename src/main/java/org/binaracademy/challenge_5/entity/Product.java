package org.binaracademy.challenge_5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    @ManyToOne
    @JoinColumn(name = "merchant_code", referencedColumnName = "code")
    private Merchant merchant;
    private String name;
    private Integer price;
}
