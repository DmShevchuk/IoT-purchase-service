package ru.iot.purchase.service.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.iot.purchase.dto.producer.OrderStatusDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaSender kafkaSender;

    public void sendOrderStatusEvent(OrderStatusDto orderStatusDto) {
        kafkaSender.sendOrderStatus(orderStatusDto);
        log.info("Отправка сообщения {}", orderStatusDto);
    }

}
