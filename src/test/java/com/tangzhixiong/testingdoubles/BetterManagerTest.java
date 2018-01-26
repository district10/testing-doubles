package com.tangzhixiong.testingdoubles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class BetterManagerTest {

    Manager manager;

    @Mock
    Employee employee;

    @BeforeEach
    void setUp() throws Exception {
        initMocks(this);
        manager = spy(new Manager(employee));
    }

    @Test
    void shouldReturnValue() throws Exception {
        // 测得是 manager 的逻辑, 没必要真的让 employee 工作
        when(employee.deliverValue()).thenReturn("value");

        assertEquals(manager.deliverValue(), "value");
    }

    @Test
    void shouldReturnFallbackValueIfEmployeeGiveUp() throws Exception {
        when(employee.deliverValue()).thenThrow(new Exception("I give up!"));
        // 我们不测这个函数, 直接 stub 它
        doReturn("fallback value").when(manager).deliverFallbackValue();

        assertEquals(manager.deliverValue(), "fallback value");
    }

    @Test
    void shouldThrowIfEvenTheManagerGivesUp() throws Exception {
        when(employee.deliverValue()).thenThrow(new Exception("I give up!"));
        doThrow(new Exception("I give up too!")).when(manager).deliverFallbackValue();

        assertThrows(Exception.class, () -> manager.deliverValue());
    }

    @Test
    void shouldHandleAllCases() throws Exception {
        when(employee.deliverValue()).thenReturn("value");
        assertEquals(manager.deliverValue(), "value");

        reset(employee);
        when(employee.deliverValue()).thenThrow(new Exception("I give up!"));
        doReturn("fallback value").when(manager).deliverFallbackValue();
        assertEquals(manager.deliverValue(), "fallback value");

        reset(employee, manager);
        when(employee.deliverValue()).thenThrow(new Exception("I give up!"));
        doThrow(new Exception("I give up too!")).when(manager).deliverFallbackValue();
        assertThrows(Exception.class, () -> manager.deliverValue());

        System.err.println(">>> if the manager choose to do that work, it takes time too");
        reset(employee, manager);
        when(employee.deliverValue()).thenThrow(new Exception("I give up!"));
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