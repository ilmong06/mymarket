package com.cod.mymarket.order.controller;

import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.order.entity.Order;
import com.cod.mymarket.order.entity.OrderItem;
import com.cod.mymarket.order.service.OrderService;
import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    @Value("${custom.paymentSecretKey}")
    private String paymentSecretKey;
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/success")
    public String paymentResult(
            Model model,
            @RequestParam(value = "orderId") String orderId,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "paymentKey") String paymentKey,
            @RequestParam(value = "orderName") String orderName, @RequestParam(value = "productIds") String productIds) throws Exception {

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(paymentSecretKey.getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);
        String username = paymentSecretKey;
        String password = "";  // 비밀번호가 필요 없다면 빈 문자열로 둡니다.
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        URL url = new URL("https://api.tosspayments.com/v1/payments/" + paymentKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;
        model.addAttribute("isSuccess", isSuccess);

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();
        model.addAttribute("responseStr", jsonObject.toJSONString());
        System.out.println(jsonObject.toJSONString());

        String method = (String) jsonObject.get("method");

        model.addAttribute("method", method);
        model.addAttribute("orderName", orderName);
        model.addAttribute("productIds", productIds);

        if (method != null) {
            switch (method) {
                case "카드":
                    model.addAttribute("cardNumber", (String) ((JSONObject) jsonObject.get("card")).get("number"));
                    break;
                case "가상계좌":
                    model.addAttribute("accountNumber", (String) ((JSONObject) jsonObject.get("virtualAccount")).get("accountNumber"));
                    break;
                case "계좌이체":
                    model.addAttribute("bank", (String) ((JSONObject) jsonObject.get("transfer")).get("bank"));
                    break;
                case "휴대폰":
                    model.addAttribute("customerMobilePhone", (String) ((JSONObject) jsonObject.get("mobilePhone")).get("customerMobilePhone"));
                    break;
            }
        } else {
            model.addAttribute("code", (String) jsonObject.get("code"));
            model.addAttribute("message", (String) jsonObject.get("message"));
        }

        // 주문 정보 저장 로직
        Order order = new Order();
        order.setOrderId(orderId);
        order.setAmount(amount);
        order.setPaymentKey(paymentKey);
        order.setOrderName(orderName);
        order.setProductIds(productIds);// Set orderName
        orderService.save(order);

        List<Long> productIdList = List.of(productIds.split(",")).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // 상품 정보 조회
        List<Product> products = productService.getProductsByIds(productIdList);

        // OrderItem 리스트 생성
        List<OrderItem> orderItems = products.stream()
                .map(product -> {
                    OrderItem orderItem = OrderItem.builder()
                            .product(product)
                            .payDate(LocalDateTime.now())
                            .title(product.getTitle())
                            .price(product.getPrice())
                            .thumbnailImg(product.getThumbnailImg())
                            .option(product.getOption())
                            .build();
                    orderItem.setOrder(order); // 올바르게 Order 객체를 설정
                    return orderItem;
                })
                .collect(Collectors.toList());

        // 모델에 OrderItem 추가
        model.addAttribute("orderItems", orderItems);

        // OrderItem을 저장하는 서비스 메소드 호출
        orderService.saveAll(orderItems); // OrderItemService를 통해 저장

        return "order/success";
    }

    @GetMapping("/fail")
    public String paymentResult(
            Model model,
            @RequestParam("message") String message,
            @RequestParam("code") String code
    ) throws Exception {

        model.addAttribute("code", code);
        model.addAttribute("message", message);

        return "order/fail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ordercheck/{id}")
    public String orderCheck(@PathVariable("id") Long id, Principal principal, Model model) {
        // 주문 정보 조회
        Order order = orderService.getOrderById(id);

        // 모델에 주문 정보 추가
        model.addAttribute("order", order);

        // 주문 아이템 정보 추가
        List<OrderItem> orderItems = order.getOrderItems(); // Order의 getOrderItems() 메소드 사용
        model.addAttribute("orderItems", orderItems);

        return "order/ordercheck"; // 실제 HTML 파일 경로
    }

}