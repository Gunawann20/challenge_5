package org.binaracademy.challenge_5.service;

import org.binaracademy.challenge_5.entity.Merchant;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.binaracademy.challenge_5.response.entity.MerchantResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MerchantService {
    GeneralResponse createMerchant(String name, String location);
    GeneralResponse updateStatusMerchant(Long merchantCode, Boolean isOpen);
    Response<Page<MerchantResponse>> listMerchantOpen(int page);
    Response<List<Merchant>> listAllMerchant();

}
