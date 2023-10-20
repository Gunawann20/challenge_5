package org.binaracademy.challenge_5.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.binaracademy.challenge_5.response.entity.OrderResponse;
import org.binaracademy.challenge_5.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Membuat pesananan")
    @PostMapping(
            value = "order"
    )
    public GeneralResponse createOrder(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity){
        return orderService.createOrder(userId, productId, quantity);
    }

    @Operation(summary = "Menambahkan produk pada pesanan")
    @PutMapping(
            value = "order/{orderId}"
    )
    public GeneralResponse updateOrder(@PathVariable Long orderId, @RequestParam Long productId, @RequestParam Integer quantity){
        return orderService.updateOrder(orderId, productId, quantity);
    }

    @Operation(summary = "Menampilkan pesanan yang belum dibayar / belum completed")
    @GetMapping(
            value = "orders/{userId}"
    )
    public Response<List<OrderResponse>> getListOrder(@PathVariable Long userId){
        return orderService.listOrder(userId);
    }

    @Operation(summary = "Menampilkan history pesanan / yang sudah completed / yang sudah cetak invoice")
    @GetMapping(
            value = "orders/history/{userId}"
    )
    public Response<List<OrderResponse>> getHistoryOrder(@PathVariable Long userId){
        return orderService.historyOrder(userId);
    }

    @Operation(summary = "Mencetak invoice", description = "Hanya bisa mencetak pesanan yang statusnya belum sukses atau belum completed dan kemudian akan mengubah status pesanan menjadi completed")
    @GetMapping(
            value = "/order/print-invoice/{orderId}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )

    public byte[] printInvoice(@PathVariable Long orderId){

        return orderService.makeOrder(orderId).getData();
    }
}
