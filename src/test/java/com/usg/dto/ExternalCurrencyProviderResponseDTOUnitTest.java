package com.usg.dto;

import com.usg.TestUtils;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalCurrencyProviderResponseDTOUnitTest extends AbstractDTOUnitTest {

    @Test
    public void testRequiredFieldsAreInitialized() {
        ExternalCurrencyProviderResponseDTO externalProviderResponse = TestUtils.createExternalProviderResponse(1.0);

        Set<ConstraintViolation<ExternalCurrencyProviderResponseDTO>> violations = getValidator().validate(externalProviderResponse);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testRequiredFieldsAreNotInitialized() {
        ExternalCurrencyProviderResponseDTO response = new ExternalCurrencyProviderResponseDTO();

        List<String> validationFailedProperties =
                getValidator().validate(response).stream().map(c -> c.getPropertyPath().toString())
                        .collect(Collectors.toList());

        assertThat(validationFailedProperties).hasSize(2);
        assertThat(validationFailedProperties)
                .contains("base", "rates");
    }
}
