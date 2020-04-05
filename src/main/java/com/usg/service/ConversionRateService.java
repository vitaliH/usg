package com.usg.service;

import com.usg.dto.ConversionRequest;
import com.usg.dto.ConversionResponse;
import com.usg.service.apiprovider.ApiInvoker;
import com.usg.service.apiprovider.FirstAPIProvider;
import com.usg.service.apiprovider.SecondAPIProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class ConversionRateService {
    private final Random random = new Random();

    @Autowired
    private FirstAPIProvider firstAPIProvider;

    @Autowired
    private SecondAPIProvider secondAPIProvider;

    public Mono<ConversionResponse> convert(ConversionRequest conversionRequest) {
        log.info("Start convert currency {}:", conversionRequest);

        List<ApiInvoker> externalAPi = new ArrayList<>(List.of(firstAPIProvider, secondAPIProvider));
        try {
            return externalAPi.remove(random.nextInt(externalAPi.size())).invoke(conversionRequest.getFrom())
                    .onErrorResume(e -> externalAPi.remove(random.nextInt(externalAPi.size())).invoke(conversionRequest.getFrom()))
                    .onErrorResume(e -> Mono.error(() -> new RestClientException("No available currency provider")))
                    .map(response ->
                            ConversionResponse.conversionResponseBuilder()
                                    .from(conversionRequest.getFrom())
                                    .to(conversionRequest.getTo())
                                    .amount(conversionRequest.getAmount())
                                    .converted(BigDecimal.valueOf(conversionRequest.getAmount())
                                            .multiply(BigDecimal.valueOf(response.getRates().getOrDefault(conversionRequest.getTo(), 1.0))))
                                    .build());
        } catch (RestClientException e) {
            log.error("Error during currency provider call", e);
        }

        return Mono.empty();
    }
}
