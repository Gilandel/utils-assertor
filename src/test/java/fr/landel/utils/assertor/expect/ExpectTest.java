/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilandel
 * %%
 * Authors: Gilles Landel
 * URL: https://github.com/Gilandel
 * 
 * This file is under Apache License, version 2.0 (2004).
 * #L%
 */
package fr.landel.utils.assertor.expect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.ComparisonFailure;
import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;

/**
 * Check the expect class
 *
 * @since Jul 16, 2016
 * @author Gilles
 *
 */
public class ExpectTest extends AbstractTest {

    /**
     * Test method for {@link Expect#exception(ConsumerAssert, Class)}.
     */
    @Test
    public void testExceptionConsumerAssertOfThrowableClassOfT() {
        Expect.exception(() -> {
            throw new IllegalArgumentException();
        }, IllegalArgumentException.class);

        try {
            Expect.exception(() -> {
                throw new IllegalArgumentException();
            }, IOException.class);
            fail();
        } catch (ExpectException e) {
            assertEquals("The expected exception never came up", e.getMessage());
        }

        try {
            Expect.exception(() -> {
            }, IOException.class);
            fail();
        } catch (ExpectException e) {
            assertEquals("No exception thrown", e.getMessage());
        }
    }

    /**
     * Test method for {@link Expect#exception(ConsumerAssert, Class, String)}
     * and {@link Expect#exception(ConsumerAssert, Class, Pattern)}.
     */
    @Test
    public void testExceptionConsumerAssertOfThrowableClassOfTString() {
        Expect.exception(() -> {
            throw new IllegalArgumentException("message");
        }, IllegalArgumentException.class, "message");

        Expect.exception(() -> {
            throw new IllegalArgumentException("message");
        }, IllegalArgumentException.class, Pattern.compile("^mes.*"));

        try {
            Expect.exception(() -> {
                throw new IllegalArgumentException("message");
            }, IllegalArgumentException.class, "message2");
            fail();
        } catch (ExpectException e) {
            assertEquals(
                    "The exception message isn't as expected.\nExpected (first part) and result (second part):\nmessage2\n-----\nmessage",
                    e.getMessage());
        }

        try {
            Expect.exception(() -> {
                throw new IllegalArgumentException("message");
            }, IllegalArgumentException.class, Pattern.compile(".*?2$"));
            fail();
        } catch (ExpectException e) {
            assertEquals("The exception message isn't as expected.\nExpected (first part) and result (second part):\n.*?2$\n-----\nmessage",
                    e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link Expect#exception(ConsumerAssert, Class, Throwable)}.
     * 
     * @throws IOException
     *             On error
     */
    @Test
    public void testExceptionConsumerAssertOfThrowableClassOfTE() throws IOException {
        Expect.exception(() -> {
            throw new IllegalArgumentException();
        }, IllegalArgumentException.class, (ok, e, a) -> new IOException());

        try {
            Expect.exception(() -> {
                throw new IllegalArgumentException();
            }, IOException.class, (ok, e, a) -> new IOException(ok + ":" + e + ":" + a));
            fail();
        } catch (IOException e) {
            assertEquals("false:null:null", e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link Expect#exception(ConsumerAssert, Class, String, Throwable)}.
     * 
     * @throws IOException
     */
    @Test
    public void testExceptionConsumerAssertOfThrowableClassOfTStringE() throws IOException {
        Expect.exception(() -> {
            throw new IllegalArgumentException("message");
        }, IllegalArgumentException.class, "message", (ok, e, a) -> new IOException());

        try {
            Expect.exception(() -> {
                throw new IllegalArgumentException("message");
            }, IllegalArgumentException.class, "message2", (ok, e, a) -> new IOException());
            fail();
        } catch (IOException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test method for
     * {@link Expect#exception(ConsumerAssert, Class, String, Throwable)}.
     * 
     * @throws IOException
     */
    @Test(expected = AssertionError.class)
    public void testExceptionJUnit() throws IOException {
        // To raise a failure in JUnit tests in place of error
        Expect.exception(() -> {
            throw new IllegalArgumentException("message");
        }, IllegalArgumentException.class, "message2", JUNIT_ERROR);
    }

    /**
     * Test method for
     * {@link Expect#exception(ConsumerAssert, Class, String, Throwable)}.
     * 
     * @throws IOException
     */
    @Test
    public void testExceptionJUnit2() throws IOException {

        try {
            Expect.exception(() -> {
                throw new IllegalArgumentException();
            }, IOException.class, JUNIT_ERROR);
            fail();
        } catch (AssertionError e) {
            assertNotNull(e);
        }
        // To raise a failure in JUnit tests in place of error
        Expect.exception(() -> {
            Expect.exception(() -> {
                throw new IllegalArgumentException("message");
            }, IllegalArgumentException.class, "message2", JUNIT_ERROR);
        }, ComparisonFailure.class, "The exception message don't match. expected:<message[2]> but was:<message[]>");
    }
}
