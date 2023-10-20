package org.binaracademy.challenge_5.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.binaracademy.challenge_5.entity.Merchant;
import org.binaracademy.challenge_5.request.CreateMerchantRequest;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.binaracademy.challenge_5.response.entity.MerchantResponse;
import org.binaracademy.challenge_5.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Operation(summary = "Menambahkan merchant")
    @PostMapping(
            value = "merchant",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GeneralResponse createMerchant(@RequestBody CreateMerchantRequest request){
        return merchantService.createMerchant(request.getName(), request.getLocation());
    }

    @Operation(summary = "Edit status merchant buka/tutup")
    @PutMapping(
            value = "merchant/{merchantCode}"
    )
    public GeneralResponse updateStatusMerchant(@PathVariable Long merchantCode, @RequestParam Boolean isOpen){
        return merchantService.updateStatusMerchant(merchantCode, isOpen);
    }

    @Operation(summary = "Menampilkan merchant yang sedang buka")
    @GetMapping(
            value = "merchants",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<Page<MerchantResponse>> showMerchantOpen(@RequestParam int page){
        return merchantService.listMerchantOpen(page);
    }

    @Operation(summary = "Menampilkan semua merchant")
    @GetMapping(
            value = "merchants/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<List<Merchant>> showMerchantOpen(){
        return merchantService.listAllMerchant();
    }
}
