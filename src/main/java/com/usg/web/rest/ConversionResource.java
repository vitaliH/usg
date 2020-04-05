package com.usg.web.rest;

import com.usg.dto.ConversionRequest;
import com.usg.dto.ConversionResponse;
import com.usg.service.ConversionRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
public class ConversionResource {

    @Autowired
    private ConversionRateService conversionRateService;

    @PostMapping("/api/currency/convert")
    public Mono<ConversionResponse> convertCurrency(@Valid @RequestBody ConversionRequest conversionRequest) {
        log.info("Received request to convert currency {}:", conversionRequest);
        return conversionRateService.convert(conversionRequest);
    }
}
