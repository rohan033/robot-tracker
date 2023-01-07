package com.sysdes.rts.application.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovementTest {

    @Test
    void test_NotNulls(){
        assertNotNull(Movement.builder().toString());
        assertNotNull(new Movement().toBuilder());
    }
}
