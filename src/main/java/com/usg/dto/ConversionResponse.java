package com.usg.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ConversionResponse extends ConversionRequest{
    @PositiveOrZero
    private BigDecimal converted;

    @Builder(builderMethodName = "conversionResponseBuilder")
    public ConversionResponse(String from, String to, Double amount, BigDecimal converted) {
        super(from, to, amount);
        this.converted = converted;
    }
}
