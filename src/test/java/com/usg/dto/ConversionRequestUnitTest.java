package com.usg.dto;

import com.usg.TestUtils;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionRequestUnitTest extends AbstractDTOUnitTest {

    @Test
    public void testRequiredFieldsAreInitialized() {
        ConversionRequest request = TestUtils.createConversionRequest();

        Set<ConstraintViolation<ConversionRequest>> violations = getValidator().validate(request);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testRequiredFieldsAreNotInitialized() {
        ConversionRequest request = new ConversionRequest();
        request.setAmount(-1.0);

        List<String> validationFailedProperties =
                getValidator().validate(request).stream().map(c -> c.getPropertyPath().toString())
                        .collect(Collectors.toList());

        assertThat(validationFailedProperties).hasSize(3);
        assertThat(validationFailedProperties)
                .contains("from", "to", "amount");
    }
}
