package ru.iot.purchase.service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.iot.purchase.dto.producer.OrderStatusDto;

@Service
@RequiredArgsConstructor
public class KafkaSender {

    @Value("${kafka-topics.send-order-status}")
    private String sendOrderStatusTopic;

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public void sendOrderStatus(OrderStatusDto orderStatusDto) {
        kafkaTemplate.send(sendOrderStatusTopic, orderStatusDto.getOrderId().toString(), orderStatusDto);
    }

}