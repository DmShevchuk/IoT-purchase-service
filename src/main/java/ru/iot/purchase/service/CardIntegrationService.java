package ru.iot.purchase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.iot.purchase.dto.BalanceDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardIntegrationService {

    public boolean payForOrder(UUID clientId, Double sum) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://182.20.0.24:8088/card/api/v1/cards";

        BalanceDto balanceDto = new BalanceDto();
        balanceDto.setClientId(clientId);
        balanceDto.setSum(sum);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BalanceDto> requestEntity = new HttpEntity<>(balanceDto, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);

        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
}
