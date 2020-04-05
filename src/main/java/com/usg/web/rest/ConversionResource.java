package com.usg.web.rest;

import com.usg.dto.ConversionRequest;
import com.usg.dto.ConversionResponse;
import com.usg.service.ConversionRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class ConversionResource {

    @Autowired
    private ConversionRateService conversionRateService;

    @PostMapping("/api/currency/convert")
    public Mono<ConversionResponse> convertCurrency(@Valid @RequestBody ConversionRequest conversionRequest)  {
        return conversionRateService.convert(conversionRequest);
    }
}
