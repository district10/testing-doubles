package com.tangzhixiong.testingdoubles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ManagerTest {

    @InjectMocks
    Manager manager;

    @Mock
    Employee employee;

    @BeforeEach
    void setUp() throws Exception {
        initMocks(this);
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
        /*
            |   org.mockito.exceptions.misusing.MissingMethodInvocationException:
            |   when() requires an argument which has to be 'a method call on a mock'.
            |   For example:
            |       when(mock.getArticles()).thenReturn(articles);
            |
            |   Also, this error might show up because:
            |   1. you stub either of: final/private/equals()/hashCode() methods.
            |      Those methods *cannot* be stubbed/verified.
            |      Mocking methods declared on non-public parent classes is not supported.
            |       可能你 stub 了私有函数啥的 (私有函数应该会编译出错吧?)
            |  2. inside when() you don't call method on mock but on some other object.
            |       也可能你在尝试 stub 一个非 mock 对象的函数
            |-------------------------------------------------------------------------------
            $   when(manager.deliverFallbackValue()).thenReturn("fallback value");
         */

        assertEquals(manager.deliverValue(), "fallback value");
    }

//  如果不 spy manager, 你就测不到 manager 的这个逻辑
//-----------------------------------------------------------------------------
//  @Test
//  void shouldThrowIfEvenTheManagerGivesUp() throws Exception {
//      when(employee.deliverValue()).thenThrow(new Exception("I give up!"));
//      doThrow(new Exception("I give up too!")).when(manager).deliverFallbackValue();
//
//      assertThrows(Exception.class, () -> manager.deliverValue());
// }


    @Test
    void shouldDoRealWorkIfYouReallyWantAMockToDoSo() throws Exception {
        doCallRealMethod().when(employee).deliverValue();
        assertEquals(manager.deliverValue(), "value");
    }

    @Test
    void shouldIncreaseEmployeeSalaryBy10IfReviewGoodResult() {
        manager.review("good");

        ArgumentCaptor<Integer> deltaCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(employee).adjustSalary(deltaCaptor.capture());

        assertThat(deltaCaptor.getValue(), equalTo(10));

    }

}