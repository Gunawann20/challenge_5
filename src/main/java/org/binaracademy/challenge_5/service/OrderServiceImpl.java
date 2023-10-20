package org.binaracademy.challenge_5.service;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.binaracademy.challenge_5.entity.Order;
import org.binaracademy.challenge_5.entity.OrderDetail;
import org.binaracademy.challenge_5.entity.Product;
import org.binaracademy.challenge_5.entity.User;
import org.binaracademy.challenge_5.repository.OrderRepository;
import org.binaracademy.challenge_5.repository.ProductRepository;
import org.binaracademy.challenge_5.repository.UserRepository;
import org.binaracademy.challenge_5.response.DetailOrderResponse;
import org.binaracademy.challenge_5.response.GeneralResponse;
import org.binaracademy.challenge_5.response.Response;
import org.binaracademy.challenge_5.response.entity.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public GeneralResponse createOrder(Long userId, Long productId, Integer quantity) {
        GeneralResponse response = new GeneralResponse();
        try {
            User user = userRepository.findById(userId).orElse(null);
            Product product = productRepository.findById(productId).orElse(null);
            if (user != null && product != null){
                Order order = new Order();
                order.setUser(user);
                order.setDestination(user.getAddress());
                order.setTime(LocalDateTime.now());
                order.setOrderDetails(new ArrayList<>());

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setOrder(order);
                orderDetail.setQuantity(quantity);
                orderDetail.setTotalPrice(((long) product.getPrice() * quantity));

                order.getOrderDetails().add(orderDetail);
                order.setIsCompleted(false);
                orderRepository.save(order);
                response.setError(false);
                response.setMessage("Berhasil membuat order");
            }else {
                response.setError(true);
                response.setMessage("user atau produk tidak ditemukan");
            }
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal membuat order");
        }
        return response;
    }

    @Override
    public GeneralResponse updateOrder(Long orderId, Long productId, Integer quantity) {
        GeneralResponse response = new GeneralResponse();
        try {
            Order order = orderRepository.findById(orderId).orElse(null);
            Product product = productRepository.findById(productId).orElse(null);
            if (order != null && product != null){
                OrderDetail orderDetail = new OrderDetail();

                orderDetail.setProduct(product);
                orderDetail.setOrder(order);
                orderDetail.setQuantity(quantity);
                orderDetail.setTotalPrice(((long) product.getPrice() * quantity));

                order.getOrderDetails().add(orderDetail);
                orderRepository.save(order);
                response.setError(false);
                response.setMessage("Berhasil update order");
            }else {
                response.setError(true);
                response.setMessage("Data order tidak ditemukan");
            }
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal update order");
        }
        return response;
    }

    @Override
    public Response<List<OrderResponse>> listOrder(Long userId) {
        Response<List<OrderResponse>> response = new Response<>();
        try {
            List<OrderResponse> orderResponses = orderRepository.findAllByUserIdAndIsCompleted(userId, false).stream().map(order -> {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setOrderId(order.getId());
                orderResponse.setDestination(order.getDestination());
                orderResponse.setTime(order.getTime());
                orderResponse.setIsCompleted(order.getIsCompleted());
                orderResponse.setOrderDetails(order.getOrderDetails());

                return orderResponse;
            }).collect(Collectors.toList());
            response.setError(false);
            response.setMessage("Success");
            response.setData(orderResponses);
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal mendapatkan data");
            response.setData(null);
        }
        return response;
    }

    @Override
    public Response<List<OrderResponse>> historyOrder(Long userId) {
        Response<List<OrderResponse>> response = new Response<>();
        try {
            List<OrderResponse> orderResponses = orderRepository.findAllByUserIdAndIsCompleted(userId, true).stream().map(order -> {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setOrderId(order.getId());
                orderResponse.setDestination(order.getDestination());
                orderResponse.setTime(order.getTime());
                orderResponse.setIsCompleted(order.getIsCompleted());
                orderResponse.setOrderDetails(order.getOrderDetails());

                return orderResponse;
            }).collect(Collectors.toList());
            response.setError(false);
            response.setMessage("Success");
            response.setData(orderResponses);
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal mendapatkan data");
            response.setData(null);
        }
        return response;
    }

    @Override
    public Response<byte []> makeOrder(Long orderId) {
        Response<byte []> response = new Response<>();
        Integer sumQty = 0;
        Long sumPrice = 0L;
        try {
            Order order = orderRepository.findByIdAndIsCompleted(orderId, false);

            if (order != null){
                List<DetailOrderResponse> responses = order.getOrderDetails().stream().map(orderDetail -> {
                    DetailOrderResponse detailOrderResponse = new DetailOrderResponse();
                    detailOrderResponse.setProductName(orderDetail.getProduct().getName());
                    detailOrderResponse.setQuantity(orderDetail.getQuantity());
                    detailOrderResponse.setTotalPrice(orderDetail.getTotalPrice());

                    return detailOrderResponse;
                }).collect(Collectors.toList());

                for (int i = 0; i < order.getOrderDetails().size(); i++){
                    sumQty += order.getOrderDetails().get(i).getQuantity();
                    sumPrice += order.getOrderDetails().get(i).getTotalPrice();
                }

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("noPesanan", order.getId());
                parameters.put("username", order.getUser().getUsername());
                parameters.put("destination", order.getDestination());
                parameters.put("totalQuantity", sumQty);
                parameters.put("sumTotalPrice", sumPrice);

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        JasperCompileManager.compileReport(ResourceUtils.getFile("binarinvoice.jrxml").getAbsolutePath()),
                        parameters,
                        new JRBeanCollectionDataSource(responses)
                );
                order.setIsCompleted(true);
                orderRepository.save(order);
                response.setError(false);
                response.setMessage("Success");
                response.setData(JasperExportManager.exportReportToPdf(jasperPrint));

            }else {
                response.setError(true);
                response.setMessage("Data order dengan id : "+orderId+"  tidak ditemukan");
                response.setData(null);
            }
            return response;
        }catch (Exception e){
            response.setError(true);
            response.setMessage("Gagal mencetak invoice order dengan id : "+orderId);
            response.setData(null);
            return response;
        }
    }
}
