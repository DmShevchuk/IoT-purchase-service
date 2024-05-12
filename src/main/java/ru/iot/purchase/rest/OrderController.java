package ru.iot.purchase.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iot.purchase.dto.producer.OrderStatusDto;
import ru.iot.purchase.service.producer.KafkaProducer;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("orders")
public class OrderController {
    private final KafkaProducer producer;

    @PostMapping
    public void sendOrder(@RequestBody OrderStatusDto order) {
        producer.sendOrderStatusEvent(order);
    }

}