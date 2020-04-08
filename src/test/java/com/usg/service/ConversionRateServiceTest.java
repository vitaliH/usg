package com.usg.service;

import com.usg.dto.ConversionRequest;
import com.usg.dto.ConversionResponse;
import com.usg.dto.ExternalCurrencyProviderResponseDTO;
import com.usg.service.apiprovider.FirstAPIProvider;
import com.usg.service.apiprovider.SecondAPIProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ConversionRateServiceTest {

    private static final String CURRENCY = "EUR";

    @MockBean
    private FirstAPIProvider firstAPIProvider;

    @MockBean
    private SecondAPIProvider secondAPIProvider;

    @Autowired
    private ConversionRateService conversionRateService;

    @Test
    void testConvert() {

        when(firstAPIProvider.invoke(CURRENCY)).thenReturn(Mono.just(createExternalProviderResponse(0.5)));
        when(secondAPIProvider.invoke(CURRENCY)).thenReturn(Mono.just(createExternalProviderResponse(1.0)));

        Mono<ConversionResponse> result = conversionRateService.convert(createConversionRequest());

        verify(firstAPIProvider, atMostOnce()).invoke(CURRENCY);
        verify(secondAPIProvider, atMostOnce()).invoke(CURRENCY);

        ConversionResponse conversionResponse = result.block();
        assertTrue(conversionResponse.getConverted().doubleValue() == 5.0 ||
                conversionResponse.getConverted().doubleValue() == 10.0);

    }

    private ConversionRequest createConversionRequest() {
        return ConversionRequest.builder().amount(10.0).from("EUR").to("USD").build();
    }

    private ExternalCurrencyProviderResponseDTO createExternalProviderResponse(Double usdRate) {
        return ExternalCurrencyProviderResponseDTO.builder()
                .base("EUR")
                .rates(Collections.singletonMap("USD", usdRate))
                .build();
    }
}
