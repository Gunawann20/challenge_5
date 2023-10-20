package org.binaracademy.challenge_5.service;

import org.binaracademy.challenge_5.entity.Merchant;
import org.binaracademy.challenge_5.repository.MerchantRepository;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.binaracademy.challenge_5.response.entity.MerchantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;
    @Override
    public GeneralResponse createMerchant(String name, String location) {
        GeneralResponse response = new GeneralResponse();
        try {
            Merchant merchant = new Merchant();
            merchant.setName(name);
            merchant.setLocation(location);
            merchant.setIsOpen(false);

            merchantRepository.save(merchant);
            response.setError(false);
            response.setMessage("Berhasil membuat merchant");
            return response;
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal membuat merchant");
            return response;
        }
    }

    @Override
    public GeneralResponse updateStatusMerchant(Long merchantCode, Boolean isOpen) {
        GeneralResponse response = new GeneralResponse();
        try {
            Merchant merchant = merchantRepository.findById(merchantCode).orElse(null);
            if (merchant == null){
                response.setError(true);
                response.setMessage("Merchant tidak ditemukan");
            }else {
                merchant.setIsOpen(isOpen);
                merchantRepository.save(merchant);

                response.setError(false);
                response.setMessage("Berhasil update merchant");
            }
            return response;
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal update merchant status");
            return response;
        }
    }

    @Override
    public Response<Page<MerchantResponse>> listMerchantOpen(int page) {
        Response<Page<MerchantResponse>> response = new Response<>();
        Pageable pageable = PageRequest.of(page, 10);
        try {
            Page<MerchantResponse> responses = merchantRepository.findAllByIsOpen(true, pageable).stream().map(merchant -> {
                MerchantResponse merchantResponse = new MerchantResponse();
                merchantResponse.setCode(merchant.getCode());
                merchantResponse.setName(merchant.getName());
                merchantResponse.setLocation(merchant.getLocation());
                return merchantResponse;
            }).collect(Collectors.collectingAndThen(Collectors.toList(), list -> new PageImpl<>(list, pageable, list.size())));

            response.setError(false);
            response.setMessage("Berhasil mendapatkan data");
            response.setData(responses);

            return response;
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal mendapatkan data");
            response.setData(null);
            return response;
        }
    }

    @Override
    public Response<List<Merchant>> listAllMerchant() {
        Response<List<Merchant>> response = new Response<>();
        try {
            List<Merchant> merchants = merchantRepository.findAll();
            response.setError(false);
            response.setMessage("success");
            response.setData(merchants);
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal mendapatkan data list merchant");
            response.setData(null);
        }
        return response;
    }
}
