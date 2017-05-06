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
package fr.landel.utils.assertor.predicate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import org.junit.Test;

import fr.landel.utils.assertor.AbstractTest;
import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.utils.AssertorThrowable;

/**
 * Check {@link AssertorThrowable}
 *
 * @since Mar 21, 2017
 * @author Gilles
 *
 */
public class PredicateAssertorThrowableTest extends AbstractTest {

    /**
     * Test method for {@link AssertorThrowable} .
     */
    @Test
    public void testPredicateGet() {
        final Exception e = new Exception();

        assertFalse(Assertor.ofThrowable().hasHashCode(0).that(e).isOK());
        assertTrue(Assertor.ofThrowable().hasHashCode(Objects.hashCode(e)).that(e).isOK());
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

        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, (String) null).that(e).isOK());
        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, (String) null, "error").that(e).isOK());
        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, (String) null, Locale.US, "error").that(e).isOK());

        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, "msg").that(em).isOK());
        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, "msg", "error").that(em).isOK());
        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, "msg", Locale.US, "error").that(em).isOK());

        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, pattern).that(em).isOK());
        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, pattern, "error").that(em).isOK());
        assertTrue(Assertor.ofThrowable().isInstanceOf(Exception.class, pattern, Locale.US, "error").that(em).isOK());

        assertFalse(Assertor.ofThrowable().isInstanceOf(IOException.class, pattern).that(em).isOK());
        assertFalse(Assertor.ofThrowable().isInstanceOf(IOException.class, pattern, "error").that(em).isOK());
        assertFalse(Assertor.ofThrowable().isInstanceOf(IOException.class, pattern, Locale.US, "error").that(em).isOK());

        assertTrue(Assertor.ofThrowable().not().isInstanceOf(Exception.class, patternError).that(e).isOK());
        assertTrue(Assertor.ofThrowable().not().isInstanceOf(Exception.class, patternError).that(em).isOK());

        assertTrue(Assertor.ofThrowable().not().isInstanceOf(Exception.class, "").that(e).isOK());
        assertTrue(Assertor.ofThrowable().not().isInstanceOf(IOException.class, (String) null).that(e).isOK());

        assertTrue(Assertor.ofThrowable().isNotNull().and().not().isInstanceOf(Color.class, (String) null).that(new IOException()).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().or().isInstanceOf(Color.class, "msg").that(new IOException("msg")).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().xor().isInstanceOf(Color.class, "msg").that(new IOException("msg")).isOK());

        assertTrue(Assertor.that(new IOException()).isNotNull().and(Assertor.that(true).isTrue()).and().not()
                .isInstanceOf(Color.class, (String) null).isOK());

        assertTrue(Assertor.ofThrowable().isNotNull().and(Assertor.ofThrowable().not().isInstanceOf(Color.class, (String) null))
                .that(new IOException()).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().or(Assertor.ofThrowable().isInstanceOf(Color.class, "msg"))
                .that(new IOException("msg")).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().xor(Assertor.ofThrowable().isInstanceOf(Color.class, "msg"))
                .that(new IOException("msg")).isOK());
        assertTrue(Assertor.ofThrowable().isNull().nand(Assertor.ofThrowable().isInstanceOf(Color.class, "msg"))
                .that(new IOException("msg")).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().nor(Assertor.ofThrowable().isInstanceOf(Color.class, "msg"))
                .that(new IOException("msg")).isOK());

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(IOException.class).that(e).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(IOException.class, pattern).that(e).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(Exception.class, (Pattern) null).that(e).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(IOException.class, (Pattern) null).that(e).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(IOException.class).that(e).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(null, (String) null).that(e).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(null, (String) null).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(Exception.class, (String) null).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(Exception.class, (Pattern) null).that((Throwable) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(IOException.class, (Pattern) null).that((Throwable) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(null, (Pattern) null).that((Throwable) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(null, pattern).that((Throwable) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isInstanceOf(null, pattern).that(e).orElseThrow();
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

        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, (String) null).that(e).isOK());
        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, (String) null, "error").that(e).isOK());
        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, (String) null, Locale.US, "error").that(e).isOK());

        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, "msg").that(em).isOK());
        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, "msg", "error").that(em).isOK());
        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, "msg", Locale.US, "error").that(em).isOK());

        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, pattern).that(em).isOK());
        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, pattern, "error").that(em).isOK());
        assertTrue(Assertor.ofThrowable().isAssignableFrom(Exception.class, pattern, Locale.US, "error").that(em).isOK());

        assertTrue(Assertor.ofThrowable().not().isAssignableFrom(IOException.class, pattern).that(em).isOK());
        assertTrue(Assertor.ofThrowable().not().isAssignableFrom(Exception.class, patternError).that(em).isOK());
        assertTrue(Assertor.ofThrowable().not().isAssignableFrom(Exception.class, patternError).that(em).isOK());

        assertTrue(Assertor.ofThrowable().not().isAssignableFrom(IOException.class, pattern).that(e).isOK());

        assertTrue(Assertor.ofThrowable().not().isAssignableFrom(Exception.class, "").that(new Exception()).isOK());
        assertTrue(Assertor.ofThrowable().not().isAssignableFrom(IOException.class, (String) null).that(new Exception()).isOK());

        assertTrue(Assertor.ofThrowable().isNotNull().and().not().isAssignableFrom(Color.class, (String) null).that(e).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().or().isAssignableFrom(Color.class, "msg").that(new IOException("msg")).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().xor().isAssignableFrom(Color.class, "msg").that(new IOException("msg")).isOK());
        assertTrue(Assertor.ofThrowable().isNull().nand().isAssignableFrom(Color.class, "msg").that(new IOException("msg")).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().nor().isAssignableFrom(Color.class, "msg").that(new IOException("msg")).isOK());

        assertTrue(Assertor.that(e).isNotNull().and(Assertor.that(true).isTrue()).and().not().isAssignableFrom(Color.class, (String) null)
                .isOK());

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(IOException.class).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(IOException.class, (Pattern) null).that(e).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(IOException.class).that(new Exception()).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(null, (String) null).that(new Exception()).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(null, (String) null).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(Exception.class, (String) null).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(null, (Pattern) null).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(null, pattern).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(null, pattern).that(e).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().isAssignableFrom(null, (Pattern) null).that(e).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseNull(StepAssertor, MessageAssertor)}.
     */
    @Test
    public void testHasCauseNull() {
        final IOException e = new IOException();

        assertTrue(Assertor.ofThrowable().hasCauseNull().that(e).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseNull("error").that(e).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseNull(Locale.US, "error").that(e).isOK());

        assertFalse(Assertor.ofThrowable().hasCauseNull().that(new IOException(new IllegalArgumentException())).isOK());
        assertFalse(Assertor.ofThrowable().hasCauseNull("error").that(new IOException(new IllegalArgumentException())).isOK());
        assertFalse(Assertor.ofThrowable().hasCauseNull(Locale.US, "error").that(new IOException(new IllegalArgumentException())).isOK());

        assertTrue(Assertor.ofThrowable().isNotNull().and().hasCauseNull().that(e).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().or().hasCauseNull().that(new IOException("msg")).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().xor().not().hasCauseNull().that(new IOException("msg")).isOK());

        assertException(() -> {
            Assertor.ofThrowable().not().hasCauseNull().that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseNull().that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseNotNull(StepAssertor, MessageAssertor)}.
     */
    @Test
    public void testHasCauseNotNull() {
        final IOException e = new IOException();

        assertFalse(Assertor.ofThrowable().hasCauseNotNull().that(e).isOK());
        assertFalse(Assertor.ofThrowable().hasCauseNotNull("error").that(e).isOK());
        assertFalse(Assertor.ofThrowable().hasCauseNotNull(Locale.US, "error").that(e).isOK());

        assertTrue(Assertor.ofThrowable().hasCauseNotNull().that(new IOException(new IllegalArgumentException())).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseNotNull("error").that(new IOException(new IllegalArgumentException())).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseNotNull(Locale.US, "error").that(new IOException(new IllegalArgumentException())).isOK());

        assertFalse(Assertor.ofThrowable().isNotNull().and().hasCauseNotNull().that(e).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().or().hasCauseNotNull().that(new IOException("msg")).isOK());
        assertFalse(Assertor.ofThrowable().isNotNull().xor().not().hasCauseNotNull().that(new IOException("msg")).isOK());

        assertException(() -> {
            Assertor.ofThrowable().hasCauseNotNull().that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseNotNull().that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseInstanceOf(StepAssertor, java.lang.Class, boolean, MessageAssertor)}.
     */
    @Test
    public void testHasCauseInstanceOfOK() {
        final IOException e = new IOException();
        final Exception ec = new Exception(e);
        final Pattern pattern = Pattern.compile("^m.*g$");
        final Pattern patternError = Pattern.compile("^m.*e$");
        final Exception ecm = new Exception("msg", e);
        final Exception ecmm = new Exception("msg", new IOException("msg"));

        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(IOException.class, (String) null, true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, (String) null, true, "error", true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, (String) null, true, Locale.US, "error").that(ec).isOK());

        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(IOException.class, true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, true, "error", true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, true, Locale.US, "error").that(ec).isOK());

        assertFalse(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, pattern, true).that(ecm).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, pattern, true, "error").that(ecmm).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, pattern, true, Locale.US, "error").that(ecmm).isOK());

        assertTrue(Assertor.ofThrowable().not().hasCauseInstanceOf(IllegalArgumentException.class, pattern, true).that(ecmm).isOK());
        assertTrue(Assertor.ofThrowable().not().hasCauseInstanceOf(Exception.class, patternError, true).that(ecmm).isOK());

        assertTrue(Assertor.ofThrowable().not().hasCauseInstanceOf(IllegalArgumentException.class, "msg", true).that(ecmm).isOK());
        assertTrue(Assertor.ofThrowable().not().hasCauseInstanceOf(Exception.class, "message", true).that(ecmm).isOK());

        assertFalse(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, "msg", true).that(ecm).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, "msg", true, "error").that(ecmm).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, "msg", true, Locale.US, "error").that(ecmm).isOK());

        assertTrue(Assertor.that(new Exception(new IOException(new IOException("error"))))
                .hasCauseInstanceOf(IOException.class, "error", true).isOK());

        assertFalse(Assertor.that(new Exception(new IOException(new IOException("error"))))
                .hasCauseInstanceOf(IOException.class, "error", false).isOK());

        assertTrue(Assertor.ofThrowable().not().hasCauseInstanceOf(Exception.class, "", true).that(new Exception()).isOK());
        assertTrue(Assertor.ofThrowable().not().hasCauseInstanceOf(IOException.class, (String) null, true).that(new Exception()).isOK());

        assertTrue(Assertor.ofThrowable().isNotNull().and().not().hasCauseInstanceOf(Color.class, (String) null, true).that(e).isOK());
        assertTrue(
                Assertor.ofThrowable().isNotNull().or().hasCauseInstanceOf(Color.class, "msg", true).that(new IOException("msg")).isOK());
        assertTrue(
                Assertor.ofThrowable().isNotNull().xor().hasCauseInstanceOf(Color.class, "msg", true).that(new IOException("msg")).isOK());

        assertTrue(Assertor.that(e).isNotNull().and(Assertor.that(true).isTrue()).and().not()
                .hasCauseInstanceOf(Color.class, (String) null, true).isOK());
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseInstanceOf(StepAssertor, java.lang.Class, boolean, MessageAssertor)}.
     */
    @Test
    public void testHasCauseInstanceOfKO() {
        final Pattern pattern = Pattern.compile("^m.*g$");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(null, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(null, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(IOException.class, (String) null, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(null, (Pattern) null, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(IOException.class, (String) null, true).that(new Exception()).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(null, (String) null, true).that(new Exception()).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(null, (String) null, true).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(Exception.class, (String) null, true).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(IOException.class, (Pattern) null, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(IOException.class, (Pattern) null, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(null, pattern, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(null, pattern, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(null, (Pattern) null, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseInstanceOf(IOException.class, pattern, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);
    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseAssignableFrom(StepAssertor, java.lang.Class, boolean, MessageAssertor)}.
     */
    @Test
    public void testHasCauseAssignableFromOK() {
        final IOException e = new IOException();
        final Exception ec = new Exception(e);
        final Exception emem = new Exception("msg", new IOException("msg"));
        final Exception eeem = new Exception(new IOException(new IOException("error")));
        final Pattern pattern = Pattern.compile("^m.*g$");
        final Pattern patternError = Pattern.compile("^m.*e$");

        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, (String) null, true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, (String) null, true, "error", true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, (String) null, true, Locale.US, "error").that(ec).isOK());

        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, true, "error", true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, true, Locale.US, "error").that(ec).isOK());

        assertTrue(Assertor.ofThrowable().not().hasCauseAssignableFrom(IOException.class, patternError, true).that(ec).isOK());
        assertTrue(Assertor.ofThrowable().not().hasCauseAssignableFrom(IOException.class, pattern, true).that(ec).isOK());

        assertTrue(Assertor.ofThrowable().not().hasCauseAssignableFrom(IllegalArgumentException.class, "msg", true).that(emem).isOK());
        assertTrue(Assertor.ofThrowable().not().hasCauseAssignableFrom(Exception.class, "message", true).that(emem).isOK());

        assertTrue(Assertor.ofThrowable().not().hasCauseAssignableFrom(IOException.class, patternError, true).that(emem).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, pattern, true).that(emem).isOK());
        assertTrue(Assertor.ofThrowable().not().hasCauseAssignableFrom(IllegalArgumentException.class, pattern, true).that(emem).isOK());
        assertTrue(
                Assertor.ofThrowable().not().hasCauseAssignableFrom(IllegalArgumentException.class, patternError, true).that(emem).isOK());

        assertFalse(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, "msg", true).that(new Exception("msg", e)).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, "msg", true, "error").that(emem).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, "msg", true, Locale.US, "error").that(emem).isOK());

        assertFalse(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, pattern, true).that(new Exception("msg", e)).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, pattern, true, "error").that(emem).isOK());
        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, pattern, true, Locale.US, "error").that(emem).isOK());

        assertTrue(Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, "error", true).that(eeem).isOK());

        assertFalse(Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, "error", false).that(eeem).isOK());

        assertTrue(Assertor.ofThrowable().not().hasCauseAssignableFrom(Exception.class, "", true).that(new Exception()).isOK());
        assertTrue(
                Assertor.ofThrowable().not().hasCauseAssignableFrom(IOException.class, (String) null, true).that(new Exception()).isOK());

        assertTrue(Assertor.ofThrowable().isNotNull().and().not().hasCauseAssignableFrom(Color.class, (String) null, true).that(e).isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().or().hasCauseAssignableFrom(Color.class, "msg", true).that(new IOException("msg"))
                .isOK());
        assertTrue(Assertor.ofThrowable().isNotNull().xor().hasCauseAssignableFrom(Color.class, "msg", true).that(new IOException("msg"))
                .isOK());

        assertTrue(Assertor.that(e).isNotNull().and(Assertor.that(true).isTrue()).and().not()
                .hasCauseAssignableFrom(Color.class, (String) null, true).isOK());

    }

    /**
     * Test method for
     * {@link AssertorThrowable#hasCauseAssignableFrom(StepAssertor, java.lang.Class, boolean, MessageAssertor)}.
     */
    @Test
    public void testHasCauseAssignableFromKO() {
        final Pattern pattern = Pattern.compile("^m.*g$");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(null, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(null, true).that((Throwable) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, true).that((Throwable) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, (String) null, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, (String) null, true).that(new Exception()).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(null, (String) null, true).that(new Exception()).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(null, (String) null, true).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(Exception.class, (String) null, true).that((Throwable) null).orElseThrow("msg");
        }, IllegalArgumentException.class, "msg");

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, (Pattern) null, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(null, (Pattern) null, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(null, pattern, true).that(new Exception()).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(null, pattern, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(null, (Pattern) null, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, (Pattern) null, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.ofThrowable().hasCauseAssignableFrom(IOException.class, pattern, true).that((Exception) null).orElseThrow();
        }, IllegalArgumentException.class);
    }
}
