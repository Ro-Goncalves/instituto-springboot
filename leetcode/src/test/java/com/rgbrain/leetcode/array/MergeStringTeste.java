package com.rgbrain.leetcode.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MergeStringTeste {
    
    private MergeString mergeString;

    @BeforeEach
    public void setUp() {
        mergeString = new MergeString();
    }

    @DisplayName("Testa merge de strings")
    @ParameterizedTest(name = "[{index}]: \"{0}\" merged with \"{1}\" is \"{2}\"")
    // Given
    @CsvSource({
        "abc, pqr, apbqcr",
        "ab, pqrs, apbqrs",
        "abcd, pq, apbqcd"
    })
    public void mergeString(String word1, String word2, String expected) {
        // When
        var result = mergeString.merge(word1, word2);

        // Then
        assertThat(result, is(expected));        
    }
}
