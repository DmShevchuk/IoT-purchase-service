package ru.iot.purchase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryIntegrationService {

    private final ObjectMapper objectMapper;

    public Double getProductCost(List<String> products, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("login", "YWRtaW4=");
        headers.set("password", "cXdlcnR5JDQ=");

        String requestBody;
        try {
            requestBody = "{\"product_names\": "+ objectMapper.writeValueAsString(products) +" }";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<CalculationResult> responseEntity = restTemplate.exchange(url,
                HttpMethod.PUT, requestEntity, CalculationResult.class);

        CalculationResult result = responseEntity.getBody();

        return result.getTotalCost();
    }


    static class CalculationResult {
        private Double total_cost;

        public Double getTotalCost() {
            return total_cost;
        }

        public void setTotalCost(double totalCost) {
            this.total_cost = totalCost;
        }
    }
}
