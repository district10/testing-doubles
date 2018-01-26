package com.tangzhixiong.trymockitojunit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BetterManagerTest {

    Manager manager;

    @Mock
    Employee employee;

    @BeforeEach
    void setUp() throws Exception {
        initMocks(this);
        manager = spy(new Manager(employee));
        doReturn("fallback value").when(manager).deliverFallbackValue();
    }

    @Test
    void shouldReturnValue() throws Exception {
        when(employee.deliverValue()).thenReturn("value");

        assertEquals(manager.deliverValue(), "value");
    }

    @Test
    void shouldReturnFallbackValueIfEmployeeGiveUp() throws Exception {
        when(employee.deliverValue()).thenThrow(new Exception("It cost me too much time!"));

        assertEquals(manager.deliverValue(), "fallback value");
    }

    @Test
    void shouldIncreaseEmployeeSalaryBy10IfReviewGoodResult() {
        manager.review("good");

        ArgumentCaptor<Integer> deltaCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(employee).adjustSalary(deltaCaptor.capture());

        assertThat(deltaCaptor.getValue(), equalTo(10));

    }

}