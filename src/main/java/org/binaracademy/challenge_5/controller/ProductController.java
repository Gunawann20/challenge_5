package org.binaracademy.challenge_5.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.binaracademy.challenge_5.entity.Product;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.binaracademy.challenge_5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Menambahkan produk baru")
    @PostMapping(
            value = "/product"
    )
    public GeneralResponse createProduct(@RequestParam Long merchantCode, @RequestParam String productName, @RequestParam Integer productPrice){
        return productService.createProduct(merchantCode, productName, productPrice);
    }

    @Operation(summary = "Mengupdate detail produk")
    @PutMapping(
            value = "/product/{productCode}"
    )
    public GeneralResponse updateProduct(@PathVariable Long productCode, @RequestParam String productName, @RequestParam Integer productPrice){
        return productService.updateProduct(productCode, productName, productPrice);
    }

    @Operation(summary = "Menghapus produk")
    @DeleteMapping(
            value = "/product/{productCode}"
    )
    public GeneralResponse deleteProduct(@PathVariable Long productCode){
        return productService.deleteProduct(productCode);
    }

    @Operation(summary = "Menampilkan produk yang tersedia")
    @GetMapping(
            value = "/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<Page<Product>> listProductIsAvailable(@RequestParam int page){
        return productService.listProductIsAvailable(page);
    }
}
