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

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import org.junit.ComparisonFailure;

import fr.landel.utils.commons.expect.Expect;
import fr.landel.utils.commons.function.ThrowableSupplier;
import fr.landel.utils.commons.function.TriFunction;

/**
 * Abstract for tests
 *
 * @since Jul 31, 2016
 * @author Gilles
 *
 */
public abstract class AbstractTest extends ConstantsAssertor {

    /**
     * Map an {@link IllegalArgumentException} into and {@link AssertionError}
     */
    public static final BiFunction<String, List<ParameterAssertor<?>>, AssertionError> JUNIT_THROWABLE = (message,
            parameters) -> new AssertionError(message);;

    /**
     * Function to manage the creation of Junit exception
     */
    private static final TriFunction<Boolean, String, String, AssertionError> JUNIT_ERROR = (catched, expected, actual) -> {
        if (catched) {
            return new ComparisonFailure("The exception message don't match.", expected, actual);
        } else {
            return new AssertionError("The expected exception never comes up.");
        }
    };

    /**
     * Check that the consumed code throws the specified exception.
     * 
     * <pre>
     * assertException(() -&gt; {
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
    public static <T extends Throwable> void assertException(final ThrowableSupplier<Throwable> consumer,
            final Class<T> expectedException) {

        Expect.exception(consumer, expectedException, JUNIT_ERROR);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message.
     * 
     * <pre>
     * assertException(() -&gt; {
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
    public static <T extends Throwable> void assertException(final ThrowableSupplier<Throwable> consumer, final Class<T> expectedException,
            final String expectedMessage) {

        Expect.exception(consumer, expectedException, expectedMessage, JUNIT_ERROR);
    }

    /**
     * Check that the consumed code raise the specified exception, also check
     * the message with the specified pattern.
     * 
     * <pre>
     * assertException(() -&gt; {
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
    public static <T extends Throwable> void assertException(final ThrowableSupplier<Throwable> consumer, final Class<T> expectedException,
            final Pattern messagePattern) {

        Expect.exception(consumer, expectedException, messagePattern, JUNIT_ERROR);
    }
}
