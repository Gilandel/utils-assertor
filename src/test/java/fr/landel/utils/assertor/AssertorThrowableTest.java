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
package fr.landel.utils.assertor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Check {@link AssertorThrowable}
 *
 * @since Mar 21, 2017
 * @author Gilles
 *
 */
public class AssertorThrowableTest extends AbstractTest {

    /**
     * Test method for {@link AssertorThrowable#AssertorThrowable()} .
     */
    @Test
    public void testConstructor() {
        assertNotNull(new AssertorThrowable());
    }

    /**
     * Test method for
     * {@link AssertorThrowable#isInstanceOf(StepAssertor, java.lang.Class, java.lang.CharSequence, MessageAssertor)}.
     */
    @Test
    public void testIsInstanceOf() {
        final Exception e = new Exception();
        final Exception em = new Exception("msg");
        final Pattern pattern = Pattern.compile("^m.*g$");
        final Pattern patternError = Pattern.compile("^m.*e$");
        final IllegalArgumentException il = new IllegalArgumentException("illegal");

        assertTrue(Assertor.that(e).isInstanceOf(Exception.class, (String) null).isOK());
        assertTrue(Assertor.that(e).isInstanceOf(Exception.class, (String) null, "error").isOK());
        assertTrue(Assertor.that(e).isInstanceOf(Exception.class, (String) null, Locale.US, "error").isOK());

        assertTrue(Assertor.that(em).isInstanceOf(Exception.class, "msg").isOK());
        assertTrue(Assertor.that(em).isInstanceOf(Exception.class, "msg", "error").isOK());
        assertTrue(Assertor.that(em).isInstanceOf(Exception.class, "msg", Locale.US, "error").isOK());

        assertTrue(Assertor.that(em).isInstanceOf(Exception.class, pattern).isOK());
        assertTrue(Assertor.that(em).isInstanceOf(Exception.class, pattern, "error").isOK());
        assertTrue(Assertor.that(em).isInstanceOf(Exception.class, pattern, Locale.US, "error").isOK());

        assertFalse(Assertor.that(em).isInstanceOf(IOException.class, pattern).isOK());
        assertFalse(Assertor.that(em).isInstanceOf(IOException.class, pattern, "error").isOK());
        assertFalse(Assertor.that(em).isInstanceOf(IOException.class, pattern, Locale.US, "error").isOK());

        assertTrue(Assertor.that(e).not().isInstanceOf(Exception.class, patternError).isOK());
        assertTrue(Assertor.that(em).not().isInstanceOf(Exception.class, patternError).isOK());

        assertTrue(Assertor.that(e).not().isInstanceOf(Exception.class, "").isOK());
        assertTrue(Assertor.that(e).not().isInstanceOf(IOException.class, (String) null).isOK());

        assertTrue(Assertor.that(new IOException()).isNotNull().and().not().isInstanceOf(Color.class, (String) null).isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().or().isInstanceOf(Color.class, "msg").isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().xor().isInstanceOf(Color.class, "msg").isOK());

        assertTrue(Assertor.that(new IOException()).isNotNull().and(Assertor.that(true).isTrue()).and().not()
                .isInstanceOf(Color.class, (String) null).isOK());

        assertTrue(Assertor.that(new IOException()).isNotNull().and(il).not().isInstanceOf(Color.class, (String) null).isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().or(il).isInstanceOf(Color.class, "msg").isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().xor(il).isInstanceOf(Color.class, "msg").isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNull().nand(il).isInstanceOf(Color.class, "msg").isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().nor(il).isInstanceOf(Color.class, "msg").isOK());

        assertException(() -> {
            Assertor.that(e).isInstanceOf(IOException.class).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(e).isInstanceOf(IOException.class, pattern).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(e).isInstanceOf(Exception.class, (Pattern) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(e).isInstanceOf(IOException.class, (Pattern) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(e).isInstanceOf(IOException.class).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(e).isInstanceOf(null, (String) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).isInstanceOf(null, (String) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).isInstanceOf(Exception.class, (String) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).isInstanceOf(Exception.class, (Pattern) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Throwable) null).isInstanceOf(IOException.class, (Pattern) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Throwable) null).isInstanceOf(null, (Pattern) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Throwable) null).isInstanceOf(null, pattern).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(e).isInstanceOf(null, pattern).orElseThrow();
        }, IllegalArgumentException.class);
    }

    /**
     * Test method for
     * {@link AssertorThrowable#isAssignableFrom(StepAssertor, java.lang.Class, java.lang.CharSequence, MessageAssertor)}.
     */
    @Test
    public void testIsAssignableFrom() {
        final IOException e = new IOException();
        final Exception em = new Exception("msg");
        final Pattern pattern = Pattern.compile("^m.*g$");
        final Pattern patternError = Pattern.compile("^m.*e$");

        assertTrue(Assertor.that(e).isAssignableFrom(Exception.class, (String) null).isOK());
        assertTrue(Assertor.that(e).isAssignableFrom(Exception.class, (String) null, "error").isOK());
        assertTrue(Assertor.that(e).isAssignableFrom(Exception.class, (String) null, Locale.US, "error").isOK());

        assertTrue(Assertor.that(em).isAssignableFrom(Exception.class, "msg").isOK());
        assertTrue(Assertor.that(em).isAssignableFrom(Exception.class, "msg", "error").isOK());
        assertTrue(Assertor.that(em).isAssignableFrom(Exception.class, "msg", Locale.US, "error").isOK());

        assertTrue(Assertor.that(em).isAssignableFrom(Exception.class, pattern).isOK());
        assertTrue(Assertor.that(em).isAssignableFrom(Exception.class, pattern, "error").isOK());
        assertTrue(Assertor.that(em).isAssignableFrom(Exception.class, pattern, Locale.US, "error").isOK());

        assertTrue(Assertor.that(em).not().isAssignableFrom(IOException.class, pattern).isOK());
        assertTrue(Assertor.that(em).not().isAssignableFrom(Exception.class, patternError).isOK());
        assertTrue(Assertor.that(em).not().isAssignableFrom(Exception.class, patternError).isOK());

        assertTrue(Assertor.that(e).not().isAssignableFrom(IOException.class, pattern).isOK());

        assertTrue(Assertor.that(new Exception()).not().isAssignableFrom(Exception.class, "").isOK());
        assertTrue(Assertor.that(new Exception()).not().isAssignableFrom(IOException.class, (String) null).isOK());

        assertTrue(Assertor.that(e).isNotNull().and().not().isAssignableFrom(Color.class, (String) null).isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().or().isAssignableFrom(Color.class, "msg").isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().xor().isAssignableFrom(Color.class, "msg").isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNull().nand().isAssignableFrom(Color.class, "msg").isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().nor().isAssignableFrom(Color.class, "msg").isOK());

        assertTrue(Assertor.that(e).isNotNull().and(Assertor.that(true).isTrue()).and().not().isAssignableFrom(Color.class, (String) null)
                .isOK());

        assertException(() -> {
            Assertor.that(new Exception()).isAssignableFrom(IOException.class).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(e).isAssignableFrom(IOException.class, (Pattern) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(new Exception()).isAssignableFrom(IOException.class).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(new Exception()).isAssignableFrom(null, (String) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).isAssignableFrom(null, (String) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).isAssignableFrom(Exception.class, (String) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).isAssignableFrom(null, (Pattern) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).isAssignableFrom(null, pattern).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(e).isAssignableFrom(null, pattern).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(e).isAssignableFrom(null, (Pattern) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseNull(StepAssertor, MessageAssertor)}.
     */
    @Test
    public void testHasCauseNull() {
        final IOException e = new IOException();

        assertTrue(Assertor.that(e).hasCauseNull().isOK());
        assertTrue(Assertor.that(e).hasCauseNull("error").isOK());
        assertTrue(Assertor.that(e).hasCauseNull(Locale.US, "error").isOK());

        assertFalse(Assertor.that(new IOException(new IllegalArgumentException())).hasCauseNull().isOK());
        assertFalse(Assertor.that(new IOException(new IllegalArgumentException())).hasCauseNull("error").isOK());
        assertFalse(Assertor.that(new IOException(new IllegalArgumentException())).hasCauseNull(Locale.US, "error").isOK());

        assertTrue(Assertor.that(e).isNotNull().and().hasCauseNull().isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().or().hasCauseNull().isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().xor().not().hasCauseNull().isOK());

        assertException(() -> {
            Assertor.that(new Exception()).not().hasCauseNull().orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Throwable) null).hasCauseNull().orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseNotNull(StepAssertor, MessageAssertor)}.
     */
    @Test
    public void testHasCauseNotNull() {
        final IOException e = new IOException();

        assertFalse(Assertor.that(e).hasCauseNotNull().isOK());
        assertFalse(Assertor.that(e).hasCauseNotNull("error").isOK());
        assertFalse(Assertor.that(e).hasCauseNotNull(Locale.US, "error").isOK());

        assertTrue(Assertor.that(new IOException(new IllegalArgumentException())).hasCauseNotNull().isOK());
        assertTrue(Assertor.that(new IOException(new IllegalArgumentException())).hasCauseNotNull("error").isOK());
        assertTrue(Assertor.that(new IOException(new IllegalArgumentException())).hasCauseNotNull(Locale.US, "error").isOK());

        assertFalse(Assertor.that(e).isNotNull().and().hasCauseNotNull().isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().or().hasCauseNotNull().isOK());
        assertFalse(Assertor.that(new IOException("msg")).isNotNull().xor().not().hasCauseNotNull().isOK());

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseNotNull().orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Throwable) null).hasCauseNotNull().orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseInstanceOf(StepAssertor, java.lang.Class, boolean, MessageAssertor)}.
     */
    @Test
    public void testHasCauseInstanceOf() {
        final IOException e = new IOException();
        final Exception ec = new Exception(e);
        final Pattern pattern = Pattern.compile("^m.*g$");
        final Pattern patternError = Pattern.compile("^m.*e$");
        final Exception ecm = new Exception("msg", e);
        final Exception ecmm = new Exception("msg", new IOException("msg"));

        assertTrue(Assertor.that(ec).hasCauseInstanceOf(IOException.class, (String) null, true).isOK());
        assertTrue(Assertor.that(ec).hasCauseInstanceOf(Exception.class, (String) null, true, "error", true).isOK());
        assertTrue(Assertor.that(ec).hasCauseInstanceOf(Exception.class, (String) null, true, Locale.US, "error").isOK());

        assertTrue(Assertor.that(ec).hasCauseInstanceOf(IOException.class, true).isOK());
        assertTrue(Assertor.that(ec).hasCauseInstanceOf(Exception.class, true, "error", true).isOK());
        assertTrue(Assertor.that(ec).hasCauseInstanceOf(Exception.class, true, Locale.US, "error").isOK());

        assertFalse(Assertor.that(ecm).hasCauseInstanceOf(Exception.class, pattern, true).isOK());
        assertTrue(Assertor.that(ecmm).hasCauseInstanceOf(Exception.class, pattern, true, "error").isOK());
        assertTrue(Assertor.that(ecmm).hasCauseInstanceOf(Exception.class, pattern, true, Locale.US, "error").isOK());

        assertTrue(Assertor.that(ecmm).not().hasCauseInstanceOf(IllegalArgumentException.class, pattern, true).isOK());
        assertTrue(Assertor.that(ecmm).not().hasCauseInstanceOf(Exception.class, patternError, true).isOK());

        assertFalse(Assertor.that(ecm).hasCauseInstanceOf(Exception.class, "msg", true).isOK());
        assertTrue(Assertor.that(ecmm).hasCauseInstanceOf(Exception.class, "msg", true, "error").isOK());
        assertTrue(Assertor.that(ecmm).hasCauseInstanceOf(Exception.class, "msg", true, Locale.US, "error").isOK());

        assertTrue(Assertor.that(new Exception(new IOException(new IOException("error"))))
                .hasCauseInstanceOf(IOException.class, "error", true).isOK());

        assertFalse(Assertor.that(new Exception(new IOException(new IOException("error"))))
                .hasCauseInstanceOf(IOException.class, "error", false).isOK());

        assertTrue(Assertor.that(new Exception()).not().hasCauseInstanceOf(Exception.class, "", true).isOK());
        assertTrue(Assertor.that(new Exception()).not().hasCauseInstanceOf(IOException.class, (String) null, true).isOK());

        assertTrue(Assertor.that(e).isNotNull().and().not().hasCauseInstanceOf(Color.class, (String) null, true).isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().or().hasCauseInstanceOf(Color.class, "msg", true).isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().xor().hasCauseInstanceOf(Color.class, "msg", true).isOK());

        assertTrue(Assertor.that(e).isNotNull().and(Assertor.that(true).isTrue()).and().not()
                .hasCauseInstanceOf(Color.class, (String) null, true).isOK());

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseInstanceOf(null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseInstanceOf(null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseInstanceOf(IOException.class, (String) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseInstanceOf(null, (Pattern) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseInstanceOf(IOException.class, (String) null, true).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseInstanceOf(null, (String) null, true).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).hasCauseInstanceOf(null, (String) null, true).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).hasCauseInstanceOf(Exception.class, (String) null, true).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseInstanceOf(IOException.class, (Pattern) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseInstanceOf(IOException.class, (Pattern) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseInstanceOf(null, pattern, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseInstanceOf(null, pattern, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseInstanceOf(null, (Pattern) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseInstanceOf(IOException.class, pattern, true).orElseThrow();
        }, IllegalArgumentException.class);
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseAssignableFrom(StepAssertor, java.lang.Class, boolean, MessageAssertor)}.
     */
    @Test
    public void testHasCauseAssignableFrom() {
        final IOException e = new IOException();
        final Exception ec = new Exception(e);
        final Exception emem = new Exception("msg", new IOException("msg"));
        final Exception eeem = new Exception(new IOException(new IOException("error")));
        final Pattern pattern = Pattern.compile("^m.*g$");
        final Pattern patternError = Pattern.compile("^m.*e$");

        assertTrue(Assertor.that(ec).hasCauseAssignableFrom(IOException.class, (String) null, true).isOK());
        assertTrue(Assertor.that(ec).hasCauseAssignableFrom(Exception.class, (String) null, true, "error", true).isOK());
        assertTrue(Assertor.that(ec).hasCauseAssignableFrom(Exception.class, (String) null, true, Locale.US, "error").isOK());

        assertTrue(Assertor.that(ec).hasCauseAssignableFrom(IOException.class, true).isOK());
        assertTrue(Assertor.that(ec).hasCauseAssignableFrom(Exception.class, true, "error", true).isOK());
        assertTrue(Assertor.that(ec).hasCauseAssignableFrom(Exception.class, true, Locale.US, "error").isOK());

        assertTrue(Assertor.that(ec).not().hasCauseAssignableFrom(IOException.class, patternError, true).isOK());
        assertTrue(Assertor.that(ec).not().hasCauseAssignableFrom(IOException.class, pattern, true).isOK());
        assertTrue(Assertor.that(emem).not().hasCauseAssignableFrom(IOException.class, patternError, true).isOK());
        assertTrue(Assertor.that(emem).hasCauseAssignableFrom(IOException.class, pattern, true).isOK());
        assertTrue(Assertor.that(emem).not().hasCauseAssignableFrom(IllegalArgumentException.class, pattern, true).isOK());
        assertTrue(Assertor.that(emem).not().hasCauseAssignableFrom(IllegalArgumentException.class, patternError, true).isOK());

        assertFalse(Assertor.that(new Exception("msg", e)).hasCauseAssignableFrom(Exception.class, "msg", true).isOK());
        assertTrue(Assertor.that(emem).hasCauseAssignableFrom(Exception.class, "msg", true, "error").isOK());
        assertTrue(Assertor.that(emem).hasCauseAssignableFrom(Exception.class, "msg", true, Locale.US, "error").isOK());

        assertFalse(Assertor.that(new Exception("msg", e)).hasCauseAssignableFrom(Exception.class, pattern, true).isOK());
        assertTrue(Assertor.that(emem).hasCauseAssignableFrom(Exception.class, pattern, true, "error").isOK());
        assertTrue(Assertor.that(emem).hasCauseAssignableFrom(Exception.class, pattern, true, Locale.US, "error").isOK());

        assertTrue(Assertor.that(eeem).hasCauseAssignableFrom(IOException.class, "error", true).isOK());

        assertFalse(Assertor.that(eeem).hasCauseAssignableFrom(IOException.class, "error", false).isOK());

        assertTrue(Assertor.that(new Exception()).not().hasCauseAssignableFrom(Exception.class, "", true).isOK());
        assertTrue(Assertor.that(new Exception()).not().hasCauseAssignableFrom(IOException.class, (String) null, true).isOK());

        assertTrue(Assertor.that(e).isNotNull().and().not().hasCauseAssignableFrom(Color.class, (String) null, true).isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().or().hasCauseAssignableFrom(Color.class, "msg", true).isOK());
        assertTrue(Assertor.that(new IOException("msg")).isNotNull().xor().hasCauseAssignableFrom(Color.class, "msg", true).isOK());

        assertTrue(Assertor.that(e).isNotNull().and(Assertor.that(true).isTrue()).and().not()
                .hasCauseAssignableFrom(Color.class, (String) null, true).isOK());

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseAssignableFrom(null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Throwable) null).hasCauseAssignableFrom(null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Throwable) null).hasCauseAssignableFrom(IOException.class, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseAssignableFrom(IOException.class, (String) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseAssignableFrom(IOException.class, (String) null, true).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseAssignableFrom(null, (String) null, true).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).hasCauseAssignableFrom(null, (String) null, true).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that((Throwable) null).hasCauseAssignableFrom(Exception.class, (String) null, true).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseAssignableFrom(IOException.class, (Pattern) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseAssignableFrom(null, (Pattern) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that(new Exception()).hasCauseAssignableFrom(null, pattern, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseAssignableFrom(null, pattern, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseAssignableFrom(null, (Pattern) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseAssignableFrom(IOException.class, (Pattern) null, true).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that((Exception) null).hasCauseAssignableFrom(IOException.class, pattern, true).orElseThrow();
        }, IllegalArgumentException.class);
    }
}
