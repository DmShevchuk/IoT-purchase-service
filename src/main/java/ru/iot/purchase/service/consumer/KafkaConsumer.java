package ru.iot.purchase.service.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.iot.purchase.dto.consumer.OrderEventDto;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumer {

    private final String orderTopic = "${kafka-topics.receive-order}";
    private final String consumerGroupId = "${spring.kafka.consumer.group-id}";

    @KafkaListener(
            topics = orderTopic,
            groupId = consumerGroupId,
            properties = "${kafka-consumer-properties}"
    )
    public void acceptOrderEvent(OrderEventDto orderEvent) {
        log.info("Сообщение получено {}", orderEvent);
    }

}