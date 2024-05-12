package ru.iot.purchase.dto.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventDto {

    private UUID userId;
    private UUID orderId;
    private List<String> products;

}