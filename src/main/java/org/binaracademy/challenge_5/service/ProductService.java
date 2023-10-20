package org.binaracademy.challenge_5.service;

import org.binaracademy.challenge_5.entity.Product;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.springframework.data.domain.Page;

public interface ProductService {
    GeneralResponse createProduct(Long merchantCode, String name, Integer price);
    GeneralResponse updateProduct(Long productCode, String name, Integer price);
    GeneralResponse deleteProduct(Long productCode);
    Response<Page<Product>> listProductIsAvailable(int page);
}
