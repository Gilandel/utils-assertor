/*
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

import java.util.Objects;
import java.util.regex.Pattern;

import fr.landel.utils.commons.StringUtils;
import fr.landel.utils.commons.function.ThrowableSupplier;
import fr.landel.utils.commons.function.TriFunction;

/**
 * Assertion utility class that assists in validating thrown exception.
 *
 * @author Gilles
 */
public final class Expect {

    private static final String ERROR_CONSUMER_NULL = "Consumer cannot be null";
    private static final String ERROR_EXPECTED_NULL = "Expected exception cannot be null";
    private static final String ERROR_NO_EXCEPTION = "No exception thrown";
    private static final String ERROR_EXCEPTION_DONT_MATCH = "The expected exception never came up";
    private static final String ERROR_MESSAGE_DONT_MATCH = "The exception message isn't as expected.\nExpected (first part) and result (second part):\n{}\n-----\n{}";

    private Expect() {
    }

    /**
     * Check that the consumed code raise the specified exception.
     * 
     * <pre>
     * Expect.exception(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class);
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param <T>
     *            The generic expected exception type
     */
    public static <T extends Throwable> void exception(final ThrowableSupplier<Throwable> consumer, final Class<T> expectedException) {
        exception(consumer, expectedException, null, null, null);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message.
     * 
     * <pre>
     * Expect.exception(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class, "parameter cannot be null");
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param expectedMessage
     *            The expected exception message
     * @param <T>
     *            The generic expected exception type
     */
    public static <T extends Throwable> void exception(final ThrowableSupplier<Throwable> consumer, final Class<T> expectedException,
            final String expectedMessage) {
        exception(consumer, expectedException, expectedMessage, null);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message with the specified pattern.
     * 
     * <pre>
     * Expect.exception(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class, Pattern.compile("^parameter");
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param messagePattern
     *            The message pattern
     * @param <T>
     *            The generic expected exception type
     */
    public static <T extends Throwable> void exception(final ThrowableSupplier<Throwable> consumer, final Class<T> expectedException,
            final Pattern messagePattern) {
        exception(consumer, expectedException, messagePattern, null);
    }

    /**
     * Check that the consumed code raise the specified exception and allow to
     * change the thrown exception.
     * 
     * <pre>
     * // Obviously, you can save this in a static variable to share it
     * TriFunction&lt;Boolean, String, String&gt; junitError = (catched, expected, actual) -&gt; {
     *     if (catched) {
     *         return new ComparisonFailure("The exception message don't match.", expected, actual);
     *     } else {
     *         return new AssertionError("The expected exception never came up");
     *     }
     * };
     * 
     * Expect.exception(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class, junitError);
     * 
     * // ComparisonFailure come from: org.junit.ComparisonFailure
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param exceptionFunction
     *            The exception function (three parameters are injected: (first:
     *            if it's the expected exception), (second: the expected
     *            message) and (third: the actual message), the return has to be
     *            a {@link Throwable}). If the exceptions don't match, the
     *            {@link String} parameters are {@code null}}.
     * @param <T>
     *            The generic expected exception type
     * @param <E>
     *            The exception thrown if the expected exception isn't raised
     * @throws E
     *             Exception provided
     */
    public static <T extends Throwable, E extends Throwable> void exception(final ThrowableSupplier<Throwable> consumer,
            final Class<T> expectedException, final TriFunction<Boolean, String, String, E> exceptionFunction) throws E {
        exception(consumer, expectedException, null, null, exceptionFunction);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message and allow to change the thrown exception.
     * 
     * <pre>
     * // Obviously, you can save this in a static variable to share it
     * TriFunction&lt;Boolean, String, String&gt; junitError = (catched, expected, actual) -&gt; {
     *     if (catched) {
     *         return new ComparisonFailure("The exception message don't match.", expected, actual);
     *     } else {
     *         return new AssertionError("The expected exception never came up");
     *     }
     * };
     * 
     * Expect.exception(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class, "parameter cannot be null", junitError);
     * 
     * // ComparisonFailure come from: org.junit.ComparisonFailure
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param expectedMessage
     *            The expected exception message
     * @param exceptionFunction
     *            The exception function (three parameters are injected: (first:
     *            if it's the expected exception), (second: the expected
     *            message) and (third: the actual message), the return has to be
     *            a {@link Throwable}). If the exceptions don't match, the
     *            {@link String} parameters are {@code null}}.
     * @param <T>
     *            The generic expected exception type
     * @param <E>
     *            The exception thrown if the expected exception isn't raised
     * @throws E
     *             Provided exception
     */
    public static <T extends Throwable, E extends Throwable> void exception(final ThrowableSupplier<Throwable> consumer,
            final Class<T> expectedException, final String expectedMessage, final TriFunction<Boolean, String, String, E> exceptionFunction)
            throws E {

        Expect.exception(consumer, expectedException, expectedMessage, null, exceptionFunction);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message with the specified pattern and allow to change the thrown
     * exception.
     * 
     * <pre>
     * // Obviously, you can save this in a static variable to share it
     * TriFunction&lt;Boolean, String, String&gt; junitError = (catched, expected, actual) -&gt; {
     *     if (catched) {
     *         return new ComparisonFailure("The exception message don't match.", expected, actual);
     *     } else {
     *         return new AssertionError("The expected exception never came up");
     *     }
     * };
     * 
     * Expect.exception(() -&gt; {
     *     // throw new IllegalArgumentException("parameter cannot be null");
     *     getMyType(null);
     * }, IllegalArgumentException.class, Pattern.compile("^parameter"), junitError);
     * 
     * // ComparisonFailure come from: org.junit.ComparisonFailure
     * </pre>
     * 
     * @param consumer
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param messagePattern
     *            The message pattern
     * @param exceptionFunction
     *            The exception function (three parameters are injected: (first:
     *            if it's the expected exception), (second: the expected pattern
     *            message) and (third: the actual message), the return has to be
     *            a {@link Throwable}). If the exceptions don't match, the
     *            {@link String} parameters are {@code null}}.
     * @param <T>
     *            The generic expected exception type
     * @param <E>
     *            The exception thrown if the expected exception isn't raised
     * @throws E
     *             Provided exception
     */
    public static <T extends Throwable, E extends Throwable> void exception(final ThrowableSupplier<Throwable> consumer,
            final Class<T> expectedException, final Pattern messagePattern, final TriFunction<Boolean, String, String, E> exceptionFunction)
            throws E {

        Expect.exception(consumer, expectedException, null, messagePattern, exceptionFunction);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message and allow to change the thrown exception.
     * 
     * @param exceptionSupplier
     *            The consumer (required, not null)
     * @param expectedException
     *            The expected exception type (required, not null)
     * @param expectedMessage
     *            The expected exception message
     * @param messagePattern
     *            The message pattern (applied if expected message is
     *            {@code null})
     * @param exceptionFunction
     *            The exception function (three parameters are injected: (first:
     *            if it's the expected exception), (second: the expected message
     *            or the pattern as message) and (third: the actual message),
     *            the return has to be a {@link Throwable}). If the exceptions
     *            don't match, the {@link String} parameters are {@code null}}.
     * @param <T>
     *            The generic expected exception type
     * @param <E>
     *            The exception thrown if the expected exception isn't raised
     * @throws E
     *             Provided exception
     */
    private static <T extends Throwable, E extends Throwable> void exception(final ThrowableSupplier<Throwable> exceptionSupplier,
            final Class<T> expectedException, final String expectedMessage, final Pattern messagePattern,
            final TriFunction<Boolean, String, String, E> exceptionFunction) throws E {
        Objects.requireNonNull(exceptionSupplier, ERROR_CONSUMER_NULL);
        Objects.requireNonNull(expectedException, ERROR_EXPECTED_NULL);

        Throwable e = null;
        try {
            exceptionSupplier.throwException();
        } catch (Throwable e1) {
            e = e1;
        }

        if (e == null) {
            throw new ExpectException(ERROR_NO_EXCEPTION);
        }

        boolean exceptionDontMatch = !expectedException.isAssignableFrom(e.getClass());
        boolean expectedDontMatch = expectedMessage != null && !expectedMessage.equals(e.getMessage());
        boolean patternDontMatch = expectedMessage == null && messagePattern != null && !messagePattern.matcher(e.getMessage()).matches();

        if (exceptionDontMatch || expectedDontMatch || patternDontMatch) {

            final String expectedResult;
            if (expectedMessage == null && messagePattern != null) {
                expectedResult = messagePattern.pattern();
            } else {
                expectedResult = expectedMessage;
            }

            if (exceptionFunction != null) {
                throw exceptionFunction.apply(!exceptionDontMatch, expectedResult, e.getMessage());
            } else if (exceptionDontMatch) {
                throw new ExpectException(ERROR_EXCEPTION_DONT_MATCH);
            } else {
                throw new ExpectException(StringUtils.inject(ERROR_MESSAGE_DONT_MATCH, expectedResult, e.getMessage()));
            }
        }
    }
}
