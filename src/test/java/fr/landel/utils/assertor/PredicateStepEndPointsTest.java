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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.junit.Test;

import fr.landel.utils.commons.expect.ExpectException;

/**
 * Test end points class
 *
 * @since Aug 2, 2016
 * @author Gilles
 *
 */
public class PredicateStepEndPointsTest extends AbstractTest {

    /**
     * Default exception builder
     */
    protected static final BiFunction<String, List<ParameterAssertor<?>>, IllegalArgumentException> DEFAULT_EXCEPTION_BUILDER = (
            String errors, List<ParameterAssertor<?>> parameters) -> new IllegalArgumentException(
                    HelperMessage.getMessage(ConstantsAssertor.DEFAULT_ASSERTION, null, errors, parameters, null));

    private static final BiFunction<String, List<ParameterAssertor<?>>, IOException> EXCEPTION_BUILDER = (String errors,
            List<ParameterAssertor<?>> parameters) -> new IOException(
                    HelperMessage.getMessage(ConstantsAssertor.DEFAULT_ASSERTION, null, errors, parameters, null));

    /**
     * Test method for {@link PredicateStep#orElseThrow()}.
     */
    @Test
    public void testOrElseThrow() {
        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow();
            fail();
        }, IllegalArgumentException.class);

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow((Exception) null, false);
            fail();
        }, IllegalArgumentException.class, "the char sequence 'text' should be null or empty");

        assertException(() -> {
            Assertor.that((CharSequence) null).hasLength(-1).orElseThrow((Exception) null, false);
            fail();
        }, IllegalArgumentException.class, "the length has to be greater than or equal to 0 and the char sequence cannot be null");

        assertException(() -> {
            Assertor.that((CharSequence) null).hasLength(-1).orElseThrow((Exception) null, true);
            fail();
        }, IllegalArgumentException.class, "the length has to be greater than or equal to 0 and the char sequence cannot be null");

        assertException(() -> {
            Assertor.that((CharSequence) null).hasLength(-1).orElseThrow((Supplier<Throwable>) null);
            fail();
        }, NullPointerException.class);

        assertException(() -> {
            Assertor.that((CharSequence) null).hasLength(-1).orElseThrow((error, parameters) -> new IOException(String.valueOf(error)));
            fail();
        }, IOException.class, "the length has to be greater than or equal to 0 and the char sequence cannot be null");

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow((CharSequence) null);
            fail();
        }, IllegalArgumentException.class, "the char sequence 'text' should be null or empty");

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow("test");
            fail();
        }, IllegalArgumentException.class, "test");

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow("test: '%s*'");
            fail();
        }, IllegalArgumentException.class, "test: 'text'");

        assertException(() -> {
            Assertor.that("text").isEmpty().and("").isEmpty().orElseThrow("%s %2$s test: '%1$s*', '%s*', '%1$s*'.%s*.%2$s*.", "this is",
                    "a");
            fail();
        }, IllegalArgumentException.class, "this is a test: 'text', 'text', 'text'...");

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow(() -> new IOException());
            fail();
        }, IOException.class);

        assertException(() -> {
            Assertor.that((String) null).hasLength(1).orElseThrow(() -> new IOException());
            fail();
        }, IOException.class);

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow(new IOException(), false);
            fail();
        }, IOException.class);

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow(null, false);
            fail();
        }, IllegalArgumentException.class, "the char sequence 'text' should be null or empty");

        assertException(() -> {
            Assertor.that("text").isEmpty("unused message").orElseThrow("test");
            fail();
        }, IllegalArgumentException.class, "test");

        assertEquals("text", Assertor.that("text").isNotEmpty().orElseThrow(() -> new IllegalArgumentException()));
        assertEquals("text", Assertor.that("text").isNotEmpty().orElseThrow(DEFAULT_EXCEPTION_BUILDER));

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow(DEFAULT_EXCEPTION_BUILDER);
        }, IllegalArgumentException.class, "the char sequence 'text' should be null or empty");

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow(EXCEPTION_BUILDER);
            fail();
        }, IOException.class, "the char sequence 'text' should be null or empty");

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow(() -> new IllegalArgumentException("error"));
        }, IllegalArgumentException.class, "error");

        assertException(() -> {
            Assertor.that("text").isEmpty().orElseThrow(() -> new IOException("error"));
            fail();
        }, IOException.class, "error");

        // Check if the checked instance is correctly returned
        final Exception exception = new IllegalArgumentException();
        assertEquals(exception, Assertor.that(exception).isNotNull().orElseThrow());
    }

    /**
     * Test method for {@link PredicateStep#orElseThrow()}.
     */
    @Test(expected = ExpectException.class)
    public void testOrElseThrowException() {
        assertException(() -> {
            Assertor.that("text").isNotEmpty().orElseThrow();
        }, IllegalArgumentException.class);
    }

    /**
     * Test method for {@link PredicateStep#getErrors()}.
     */
    @Test
    public void testGetErrors() {
        assertFalse(Assertor.that(new BigDecimal("265.45155")).isNotNull(Locale.FRANCE, "test %1$,.3f*").getErrors().isPresent());

        assertEquals("test 265,452", Assertor.that(new BigDecimal("265.45155")).isNull(Locale.FRANCE, "test %1$,.3f*").getErrors().get());
        assertEquals("test 265.452", Assertor.that(new BigDecimal("265.45155")).isNull(Locale.US, "test %1$,.3f*").getErrors().get());
        assertEquals("test 2 654 125,452",
                Assertor.that(new BigDecimal("2654125.45155")).isNull(Locale.FRANCE, "test %1$,.3f*").getErrors().get());
        assertEquals("test 2,654,125.452",
                Assertor.that(new BigDecimal("2654125.45155")).isNull(Locale.US, "test %1$,.3f*").getErrors().get());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 6, 25);
        assertEquals("test 25 juillet 2016", Assertor.that(calendar).isNull(Locale.FRANCE, "test %1$te* %1$tB* %1$tY*").getErrors().get());
        assertEquals("test July 25, 2016", Assertor.that(calendar).isNull(Locale.US, "test %1$tB* %1$te*, %1$tY*").getErrors().get());
    }

    /**
     * Test method for {@link PredicateStep#getNullable()}.
     */
    @Test
    public void testGetNullable() {
        assertNull(Assertor.that("text").isBlank().getNullable());
        assertNull(Assertor.that((String) null).isBlank().getNullable());
        assertEquals("text", Assertor.that("text").isNotBlank().getNullable());
        assertNull(Assertor.that((String) null).isNotBlank().getNullable());
    }

    /**
     * Test method for {@link PredicateStep#orElse(Object)}.
     */
    @Test
    public void testOrElse() {
        assertEquals("def", Assertor.that("text").isBlank().orElse("def"));
        assertEquals("def", Assertor.that((String) null).isBlank().orElse("def"));
        assertEquals("text", Assertor.that("text").isNotBlank().orElse("def"));
        assertEquals("def", Assertor.that((String) null).isNotBlank().orElse("def"));
        assertNull(Assertor.that("text").isBlank().orElse(null));
    }

    /**
     * Test method for {@link PredicateStep#orElseGet(Supplier)}.
     */
    @Test
    public void testOrElseGet() {
        assertEquals("def", Assertor.that("text").isBlank().orElseGet(() -> "def"));
        assertEquals("def", Assertor.that((String) null).isBlank().orElseGet(() -> "def"));
        assertEquals("text", Assertor.that("text").isNotBlank().orElseGet(() -> "def"));
        assertEquals("def", Assertor.that((String) null).isNotBlank().orElseGet(() -> "def"));
        assertNull(Assertor.that("text").isBlank().orElseGet(() -> null));
        assertException(() -> Assertor.that("text").isBlank().orElseGet(null), NullPointerException.class);
    }

    /**
     * Test method for {@link PredicateStep#get()}.
     */
    @Test
    public void testGet() {
        assertFalse(Assertor.that("text").isBlank().get().isPresent());
        assertException(() -> Assertor.that("text").isBlank().get().get(), NoSuchElementException.class);
        assertEquals("default", Assertor.that("text").isBlank().get().orElse("default"));

        assertTrue(Assertor.that("text").isNotBlank().get().isPresent());
        assertEquals("text", Assertor.that("text").isNotBlank().get().get());
        assertEquals("text", Assertor.that("text").isNotBlank().get().orElse("default"));

        assertEquals("default", Assertor.that((String) null).isBlank().get().orElse("default"));
        assertEquals("default", Assertor.that((String) null).isNotBlank().get().orElse("default"));

        assertFalse(Assertor.that((CharSequence) null).hasLength(-1).get().isPresent());
    }

    /**
     * Test method for {@link PredicateStep#asResult()}.
     */
    @Test
    public void testGetResult() {
        assertFalse(Assertor.that("text").isBlank().asResult().isPresent());
        assertException(() -> Assertor.that("text").isBlank().asResult().get(), NoSuchElementException.class);
        assertEquals("default", Assertor.that("text").isBlank().asResult().orElse("default"));

        assertTrue(Assertor.that("text").isNotBlank().asResult().isPresent());
        assertEquals("text", Assertor.that("text").isNotBlank().asResult().get());
        assertEquals("text", Assertor.that("text").isNotBlank().asResult().orElse("default"));

        assertNull(Assertor.that((String) null).isBlank().asResult().orElse("default"));
        assertEquals("default", Assertor.that((String) null).isNotBlank().asResult().orElse("default"));

        assertFalse(Assertor.that((CharSequence) null).hasLength(-1).asResult().isPresent());
    }

    /**
     * Test method for {@link PredicateStep#asDefault(defaultValue)}.
     */
    @Test
    public void testGetDefault() {
        assertFalse(Assertor.that("text").isBlank().asDefault("def").isPresent());
        assertEquals("def", Assertor.that("text").isBlank().asDefault("def").get());
        assertEquals("default", Assertor.that("text").isBlank().asDefault("def").orElse("default"));

        assertTrue(Assertor.that("text").isNotBlank().asDefault("def").isPresent());
        assertEquals("text", Assertor.that("text").isNotBlank().asDefault("def").get());
        assertEquals("text", Assertor.that("text").isNotBlank().asDefault("def").orElse("default"));

        assertEquals("default", Assertor.that((String) null).isBlank().asDefault("def").orElse("default"));
        assertEquals("default", Assertor.that((String) null).isNotBlank().asDefault("def").orElse("default"));

        assertFalse(Assertor.that((CharSequence) null).hasLength(-1).asDefault("def").isPresent());
    }
}
