package com.usg;

import com.usg.dto.ConversionRequest;
import com.usg.dto.ConversionResponse;
import com.usg.dto.ExternalCurrencyProviderResponseDTO;

import java.math.BigDecimal;
import java.util.Collections;

public class TestUtils {
    public  static final String BASE_CURRENCY = "EUR";

    public static final double USD_RATE_FIRST_PROVIDER = 0.5;
    public static final double USD_RATE_SECOND_PROVIDER = 1.0;

    public static final double EXPECTED_CONVERTED_VALUE_FROM_FIRST_PROVIDER = 5.0;
    public static final double EXPECTED_CONVERTED_VALUE_FROM_SECOND_PROVIDER = 10.0;

    public static ConversionRequest createConversionRequest() {
        return ConversionRequest.builder().amount(10.0).from("EUR").to("USD").build();
    }

    public static ConversionResponse createConversionResponseFromSecondProvider() {
        return ConversionResponse.conversionResponseBuilder().amount(10.0).from("EUR").to("USD")
                .converted(BigDecimal.valueOf(10.0)).build();
    }


    public static ExternalCurrencyProviderResponseDTO createExternalProviderResponse(Double usdRate) {
        return ExternalCurrencyProviderResponseDTO.builder()
                .base("EUR")
                .rates(Collections.singletonMap("USD", usdRate))
                .build();
    }
}
