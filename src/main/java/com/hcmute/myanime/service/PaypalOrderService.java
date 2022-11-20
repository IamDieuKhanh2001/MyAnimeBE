package com.hcmute.myanime.service;

import com.hcmute.myanime.model.OrderPremiumEntity;
import com.hcmute.myanime.model.PaypalOrderEntity;
import com.hcmute.myanime.repository.OrderPremiumRepository;
import com.hcmute.myanime.repository.PaypalOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaypalOrderService {
    @Autowired
    private PaypalOrderRepository paypalOrderRepository;
    @Autowired
    private OrderPremiumRepository orderPremiumRepository;

    public boolean Create(OrderPremiumEntity orderPremiumEntity, String token)
    {
        try {
            PaypalOrderEntity paypalOrderEntityOfOrderPremium = orderPremiumEntity.getPaypalOrderEntity();
            if (paypalOrderEntityOfOrderPremium == null) {
                PaypalOrderEntity paypalOrderEntity = new PaypalOrderEntity();
                paypalOrderEntity.setCurrency("USD");
                paypalOrderEntity.setIntent("sale");
                paypalOrderEntity.setDescription(orderPremiumEntity.getDescription());
                paypalOrderEntity.setPrice(orderPremiumEntity.getSubscriptionPackageById().getPrice());
                paypalOrderEntity.setStatus("pending");
                paypalOrderEntity.setToken(token);
                paypalOrderEntity.setMethod("paypal");
                paypalOrderEntity = paypalOrderRepository.save(paypalOrderEntity);

                orderPremiumEntity.setPaypalOrderEntity(paypalOrderEntity);
                orderPremiumRepository.save(orderPremiumEntity);

                return true;
            } else {
                paypalOrderEntityOfOrderPremium.setToken(token);
                paypalOrderRepository.save(paypalOrderEntityOfOrderPremium);

                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
