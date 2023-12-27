package com.durys.jakub.leaveentitlementsservice.entilements.domain;

import com.durys.jakub.leaveentitlementsservice.common.exception.DomainValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmountTest {

    @Test
    void shouldCreateAmount() {
        assertDoesNotThrow(() -> new Amount(12));
    }

    @Test
    void shouldNotCreateAmount_whenValueIsEmpty() {
        DomainValidationException exception = assertThrows(DomainValidationException.class, () -> new Amount(null));
        assertEquals("Amount value cannot be empty", exception.getMessage());
    }


    @Test
    void shouldNotCreateAmount_whenValueIsLessThanZero() {
        DomainValidationException exception = assertThrows(DomainValidationException.class, () -> new Amount(-2));
        assertEquals("Invalid amount value", exception.getMessage());
    }


}