package org.binaracademy.challenge_5.service;

import org.binaracademy.challenge_5.entity.Merchant;
import org.binaracademy.challenge_5.entity.Product;
import org.binaracademy.challenge_5.repository.MerchantRepository;
import org.binaracademy.challenge_5.repository.ProductRepository;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Override
    public GeneralResponse createProduct(Long merchantCode, String name, Integer price) {
        GeneralResponse response = new GeneralResponse();
        try {
            Merchant merchant = merchantRepository.findById(merchantCode).orElse(null);
            if (merchant != null){
                Product product = new Product();
                product.setMerchant(merchant);
                product.setName(name);
                product.setPrice(price);
                productRepository.save(product);
                response.setError(false);
                response.setMessage("Berhasil menambahkan produk baru");
            }else {
                response.setError(true);
                response.setMessage("Merchant tidak ditemukan");
            }
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal menambahkan produk baru");
        }
        return response;
    }

    @Override
    public GeneralResponse updateProduct(Long productCode, String name, Integer price) {
        GeneralResponse response = new GeneralResponse();
        try {
            Product product = productRepository.findById(productCode).orElse(null);
            if (product != null){
                product.setName(name);
                product.setPrice(price);
                productRepository.save(product);
                response.setError(false);
                response.setMessage("Berhasil update data produk");
            }else {
                response.setError(true);
                response.setMessage("Gagal update produk. Data produk tidak ditemukan");
            }
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal mengupdate data produk");
        }

        return response;
    }

    @Override
    public GeneralResponse deleteProduct(Long productCode) {
        GeneralResponse response = new GeneralResponse();
        try {
            productRepository.deleteById(productCode);
            response.setError(false);
            response.setMessage("Berhasil menghapus produk");
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal menghapus produk");
        }

        return response;
    }

    @Override
    public Response<Page<Product>> listProductIsAvailable(int page) {
        Response<Page<Product>> response = new Response<>();
        Pageable pageable = PageRequest.of(page, 10);
        try{
            Page<Product> products = productRepository.findAllByMerchant_IsOpen(true, pageable);
            response.setError(false);
            response.setMessage("Success");
            response.setData(products);
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal mendapatkan data");
            response.setData(null);
        }
        return response;
    }
}
