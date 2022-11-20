package com.hcmute.myanime.controller;

import com.hcmute.myanime.service.PaypalService;
import com.hcmute.myanime.beans.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("payment/paypal")
public class PaypalController {
    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @Autowired
    private PaypalService paypalService;

    @PostMapping("/pay")
    public ResponseEntity<?> payment(@RequestBody Order order, HttpServletRequest httpServletRequest) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(httpServletRequest)
                .replacePath(null)
                .build()
                .toUriString();

        baseUrl += "/payment/paypal/";

        try {
            Payment payment = paypalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), baseUrl + CANCEL_URL,
                    baseUrl + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    //return "redirect:"+link.getHref();
                    return ResponseEntity.ok(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("error");
    }

    @GetMapping(value = CANCEL_URL)
    public void cancelPay(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    @GetMapping(value = SUCCESS_URL)
    public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok("Payment success");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.badRequest().body("Payment error");
    }
}

