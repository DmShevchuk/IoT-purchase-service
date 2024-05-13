package ru.iot.purchase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.iot.purchase.domain.OrderStatus;
import ru.iot.purchase.dto.consumer.OrderEventDto;
import ru.iot.purchase.dto.producer.OrderStatusDto;
import ru.iot.purchase.service.producer.KafkaSender;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final DeliveryIntegrationService deliveryService;
    private final CardIntegrationService cardIntegrationService;

    @Value("${mock-api-url.ozon}")
    private String OZON_API_URL;

    @Value("${mock-api-url.yandex}")
    private String YANDEX_API_URL;

    @Value("${mock-api-url.sber}")
    private String SBER_API_URL;

    @Value("${mock-api-url.samokat}")
    private String SAMOKAT_API_URL;

    private final KafkaSender kafkaSender;

    public void processOrder(OrderEventDto orderEventDto) {
        List<String> apis = List.of(OZON_API_URL, YANDEX_API_URL, SBER_API_URL, SAMOKAT_API_URL);
        double price = -1.0;
        int idx = 0;
        for (String url : apis) {
            double currentPrice = deliveryService.getProductCost(orderEventDto.getProducts(), url);
            if (currentPrice <= price) {
                price = currentPrice;
            }
        }

        boolean isSuccessPaid = cardIntegrationService.payForOrder(orderEventDto.getUserId(), -price);
        OrderStatusDto orderStatusDto = new OrderStatusDto();
        orderStatusDto.setStatus(isSuccessPaid ? OrderStatus.ACCEPTED : OrderStatus.DECLINED);
        orderStatusDto.setOrderId(orderEventDto.getOrderId());
        kafkaSender.sendOrderStatus(orderStatusDto);
    }

}
