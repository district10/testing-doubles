package com.tangzhixiong.learningtests;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Junit5ExampleTest {

    @Test
    @DisplayName("emoji \uD83D\uDE31")
    void shouldWorkGreatly() {

        assertTrue(true);
        assertTrue(true, "true");
        assertTrue(true, () -> "will not evaluate right now");

        assertEquals(3 == 3, true, "should be true");

        assertAll("number",
                () -> assertNotNull(new Date()),
                () -> assertAll("nested",
                        () -> assertTrue(true),
                        () -> assertFalse(false)
                ),
                () -> assertThrows(Exception.class, () -> {
                    throw new Exception("an exception");
                })
        );
    }

    @Test
    void shouldPassAllTimeoutTests() {
        assertTimeout(Duration.ofMinutes(2), () -> {
            System.out.println("will not possibly cost two minutes");
        });

        assertTimeout(Duration.ofMillis(10), () -> {
            // will fail
//            Thread.sleep(100);
        });

        assertTimeoutPreemptively(Duration.ofMillis(10), () -> {
            // will fail
//            Thread.sleep(100);
        });

    }

    @Disabled
    @Test
    void shouldFail() {
        assertThat(true, equalTo(false));
    }

    @Test
    void shouldAssertThat() {
        assertThat(2 + 1, is(equalTo(3)));
        assumingThat("TRUE".startsWith("T"), () -> {
            assertTrue(true);
        });
        assumingThat("TRUE".startsWith("F"), () -> {
            assertTrue(false);
        });
    }

    @Nested
    class AnotherTest {

        AnotherTest(TestInfo testInfo) {
            assertThat(testInfo.getDisplayName(), equalTo("AnotherTest"));
        }

        @BeforeEach
        void init(TestInfo testInfo) {
            System.out.println(testInfo.getDisplayName());
            assertTrue(testInfo.getDisplayName().startsWith("should"));
        }

        @Test
        void shouldPass() {
            assertThat(true, is(true));
        }

        @Disabled
        @Test
        void itsOkayNotToStartWithShouldIfIsDisabled() {
        }
    }

    @Test
    void shouldWriteToStdOut(TestReporter testReporter) {
        Map<String, String> stdout = new HashMap<>();
        stdout.put("A", "alpha");
        stdout.put("B", "beta");
        testReporter.publishEntry("key", "value");
        testReporter.publishEntry(stdout);
    }
}
