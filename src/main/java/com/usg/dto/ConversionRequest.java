package com.usg.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {
    @NotBlank
    private String from;

    @NotBlank
    private String  to;

    @PositiveOrZero
    private Double amount;
}
