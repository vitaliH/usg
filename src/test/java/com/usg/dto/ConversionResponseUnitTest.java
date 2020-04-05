package com.usg.dto;

import com.usg.TestUtils;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionResponseUnitTest extends AbstractDTOUnitTest {

    @Test
    public void testRequiredFieldsAreInitialized() {
        ConversionResponse response = TestUtils.createConversionResponseFromSecondProvider();

        Set<ConstraintViolation<ConversionRequest>> violations = getValidator().validate(response);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testRequiredFieldsAreNotInitialized() {
        ConversionResponse response = new ConversionResponse();
        response.setAmount(-10.0);
        response.setConverted(BigDecimal.valueOf(-10));

        List<String> validationFailedProperties =
                getValidator().validate(response).stream().map(c -> c.getPropertyPath().toString())
                        .collect(Collectors.toList());

        assertThat(validationFailedProperties).hasSize(4);
        assertThat(validationFailedProperties)
                .contains("from", "to", "amount", "converted");
    }
}
