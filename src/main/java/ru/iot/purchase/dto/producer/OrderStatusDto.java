package ru.iot.purchase.dto.producer;


import lombok.Data;
import ru.iot.purchase.domain.OrderStatus;

import java.util.UUID;

@Data
public class OrderStatusDto {

    private UUID orderId;
    private OrderStatus status;
    private String description;

}
