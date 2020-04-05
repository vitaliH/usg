package com.usg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalCurrencyProviderResponseDTO {
    @NotBlank
    @JsonProperty
    private String base;

    @NotEmpty
    @JsonProperty
    private Map<String, Double> rates;
}
