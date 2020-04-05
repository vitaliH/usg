package com.usg.service.apiprovider;

import com.usg.dto.ExternalCurrencyProviderResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

@Slf4j
@Component
public class SecondAPIProvider implements ApiInvoker {
    private static final String EXTERNAL_PROVIDER_BASE_API = "https://api.exchangerate-api.com";

    @Override
    public Mono<ExternalCurrencyProviderResponseDTO> invoke(String baseCurrency) {
        log.info("Second API provider convert from {}:", baseCurrency);

        Function<UriBuilder, URI> uriFunction = uriBuilder -> uriBuilder
                .path("/v4/latest/{baseCurrency}")
                .build(baseCurrency);

        return createWebClient(EXTERNAL_PROVIDER_BASE_API).get().uri(uriFunction)
                .retrieve().bodyToMono(ExternalCurrencyProviderResponseDTO.class);
    }
}
