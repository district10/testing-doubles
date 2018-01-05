package com.tangzhixiong.trymockitojunit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;

/**
 * @since 5.0
 * @see MockitoExtension
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class MockitoExtensionInBaseClassTest {

    @Mock
    private NumberGenerator numberGenerator;

    @BeforeEach
    void initialize(@Mock MyType myType, TestInfo testInfo) {
        when(myType.getName()).thenReturn(testInfo.getDisplayName());
        when(numberGenerator.next()).thenReturn(42);
    }

    @Test
    void firstTestWithInjectedMock(@Mock MyType myType) {
        assertEquals("firstTestWithInjectedMock(MyType)", myType.getName());
        assertEquals(42, numberGenerator.next());
    }

    @Test
    void secondTestWithInjectedMock_ThisIsTheFunctionName(@Mock MyType myType) {
        assertEquals("secondTestWithInjectedMock_ThisIsTheFunctionName(MyType)", myType.getName());
        assertEquals(42, numberGenerator.next());
    }

    @Disabled
    @Test
    void multipleImplicitlyNamedInjectedMocksOfSameTypeAreNotTheSameInstance(@Mock MyType myType1,
            @Mock MyType myType2) {

        assertNotNull(myType1);
        assertNotNull(myType2);

        assertNotSame(myType1, myType2, "Make sure the project was compiled with -parameters");

        assertTrue(myType1.toString().contains("myType1"));
        assertTrue(myType2.toString().contains("myType2"));
    }

    @Test
    void multipleExplicitlyNamedInjectedMocksOfSameTypeAreNotTheSameInstanceTest(@Mock(name = "one") MyType myType1,
            @Mock(name = "two") MyType myType2) {

        assertNotNull(myType1);
        assertNotNull(myType2);
        assertNotSame(myType1, myType2);

        assertTrue(myType1.toString().contains("one"));
        assertTrue(myType2.toString().contains("two"));
    }

}
