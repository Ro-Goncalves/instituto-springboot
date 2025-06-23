package com.rgbrain.leetcode.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GreatestCommonDivisorStringsTest {

    private GreatestCommonDivisorStrings divisorStrings;

    @BeforeEach
    void setUp() {
        this.divisorStrings = new GreatestCommonDivisorStrings();
    }

    @DisplayName("Test Divisor Strings")
    @ParameterizedTest(name = "[{index}]: \"{2}\" is divisor of \"{0}\" and \"{1}\"")
    // Given
    @CsvSource({
        "ABCABC, ABC, ABC",
        "ABABAB, ABAB, AB",
        "LEET, CODE, ''"
    })
    public void mergeString(String str1, String str2, String divisor) {
        // When
        var result = divisorStrings.execute(str1, str2);

        // Then
        assertThat(result, is(divisor));        
    }
    
}
