package com.usg.web.rest;

import com.usg.TestUtils;
import com.usg.dto.ConversionResponse;
import com.usg.service.apiprovider.FirstAPIProvider;
import com.usg.service.apiprovider.SecondAPIProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static com.usg.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConversionResourceIntTest {

    @MockBean
    private FirstAPIProvider firstAPIProvider;

    @MockBean
    private SecondAPIProvider secondAPIProvider;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testConvertCurrency() {

        when(firstAPIProvider.invoke(BASE_CURRENCY)).thenReturn(Mono.just(TestUtils.createExternalProviderResponse(USD_RATE_FIRST_PROVIDER)));
        when(secondAPIProvider.invoke(BASE_CURRENCY)).thenReturn(Mono.just(TestUtils.createExternalProviderResponse(TestUtils.USD_RATE_SECOND_PROVIDER)));

        FluxExchangeResult<ConversionResponse> result = webClient.post()
                .uri("api/currency/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestUtils.createConversionRequest()))
                .exchange()
                .expectStatus().isOk()
                .returnResult(ConversionResponse.class);

        verify(firstAPIProvider, atMostOnce()).invoke(BASE_CURRENCY);
        verify(secondAPIProvider, atMostOnce()).invoke(BASE_CURRENCY);

        ConversionResponse conversionResponse = result.getResponseBody().blockFirst();
        assertTrue(conversionResponse.getConverted().doubleValue() == EXPECTED_CONVERTED_VALUE_FROM_FIRST_PROVIDER ||
                conversionResponse.getConverted().doubleValue() == EXPECTED_CONVERTED_VALUE_FROM_SECOND_PROVIDER);
    }
}
