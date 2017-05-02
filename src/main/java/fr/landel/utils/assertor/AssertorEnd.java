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
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.helper.HelperEnd;
import fr.landel.utils.commons.Default;
import fr.landel.utils.commons.ObjectUtils;
import fr.landel.utils.commons.Result;

/**
 * This class is the final link in chain, see {@link AssertorStep}.
 * 
 * <p>
 * Final by using methods:
 * </p>
 * <ul>
 * <li>{@link AssertorEnd#isOK}: to get the boolean result of the assertion
 * {@code true} or {@code false}.</li>
 * <li>{@link AssertorEnd#getErrors}: to get the error message (precondition
 * message or message depending of error type).</li>
 * <li>{@link AssertorEnd#get}: to get the result as an {@link Optional} object.
 * The result is set to empty if the assertion failed or if the checked value is
 * {@code null}.</li>
 * <li>{@link AssertorEnd#getNullable}: to get the result even if value is
 * {@code null}.</li>
 * <li>{@link AssertorEnd#orElse}: to get the value if not {@code null},
 * otherwise returns the default value.</li>
 * <li>{@link AssertorEnd#orElseThrow}: to throw an exception if assertion is
 * false, or to get the checked value otherwise.</li>
 * <li>{@link AssertorEnd#asResult}: to get the result as a {@link Result}
 * object. The result is set to empty only if the assertion failed.</li>
 * <li>{@link AssertorEnd#asDefault}: to get the result as a {@link Default}
 * object. It's the as {@link Optional} but the default value is already
 * included.</li>
 * </ul>
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 * @param <T>
 *            the type of checked object
 */
@FunctionalInterface
public interface AssertorEnd<T> {

    /**
     * @return the step result
     */
    StepAssertor<T> getStep();

    /**
     * Get the object as {@link Optional}. This method is similar to
     * {@link #asResult()}. The difference is if the result is {@code null} it's
     * considered as empty.
     * 
     * <pre>
     * Optional&lt;String&gt; optional1 = Assertor.that("text").isBlank().get();
     * optional1.isPresent(); // =&gt; false
     * optional1.get(); // =&gt; throw a NoSuchElementException exception
     * optinal1.orElse("default"); // =&gt; "default"
     * 
     * Optional&lt;String&gt; optional2 = Assertor.that("text").isNotBlank().get();
     * optional2.isPresent(); // =&gt; true
     * optional2.get(); // =&gt; "text"
     * optional2.orElse("default"); // =&gt; "text"
     * 
     * // Difference with result() method which returns a Result object
     * Assertor.that((String) null).isBlank().get().orElse("default");
     * // =&gt; "default"
     * Assertor.that((String) null).isNotBlank().get().orElse("default");
     * // =&gt; "default"
     * </pre>
     * 
     * @return The optional
     */
    default Optional<T> get() {
        return Optional.ofNullable(this.getNullable());
    }

    /**
     * Get the object as {@link Result}. This method is similar to
     * {@link #get()}. The difference is even if the result is {@code null} it's
     * not considered as empty.
     * 
     * <pre>
     * Assertor.that("text").isBlank().asResult().isPresent();
     * // = false
     * Assertor.that("text").isBlank().asResult().get();
     * // =&gt; throw a NoSuchElementException exception
     * Assertor.that("text").isBlank().asResult().orElse("default");
     * // = "default"
     * 
     * Assertor.that("text").isNotBlank().asResult().isPresent();
     * // = true
     * Assertor.that("text").isNotBlank().asResult().get();
     * // = "text"
     * Assertor.that("text").isNotBlank().asResult().orElse("default");
     * // = "text"
     * 
     * // Difference with get() method which returns an Optional object
     * Assertor.that((String) null).isBlank().asResult().orElse("default");
     * // = null
     * Assertor.that((String) null).isNotBlank().asResult().orElse("default");
     * // = "default"
     * </pre>
     * 
     * @return The result
     */
    default Result<T> asResult() {
        return HelperEnd.asResult(this.getStep());
    }

    /**
     * Get the object as {@link Default}. This method is similar to
     * {@link #asResult()}. The difference is if the result is {@code null} it's
     * returned the default value.
     * 
     * <pre>
     * Assertor.that("text").isBlank().asDefault(defaultValue).isPresent();
     * // = false
     * Assertor.that("text").isBlank().asDefault(defaultValue).get();
     * // =&gt; throw a NoSuchElementException exception
     * Assertor.that("text").isBlank().asDefault(defaultValue).orElse("default");
     * // = "default"
     * 
     * Assertor.that("text").isNotBlank().asDefault(defaultValue).isPresent();
     * // = true
     * Assertor.that("text").isNotBlank().asDefault(defaultValue).get();
     * // = "text"
     * Assertor.that("text").isNotBlank().asDefault(defaultValue).orElse("default");
     * // = "text"
     * 
     * // Difference with result() method which returns a Result object
     * Assertor.that((String) null).isBlank().asDefault(defaultValue).orElse("default");
     * // = "default"
     * Assertor.that((String) null).isNotBlank().asDefault(defaultValue).orElse("default");
     * // = "default"
     * </pre>
     * 
     * @param defaultValue
     *            the default value
     * @return the default object
     */
    default Default<T> asDefault(final T defaultValue) {
        return Default.ofNullable(this.getNullable(), defaultValue);
    }

    /**
     * Returns if the assertion is valid or not
     * 
     * @return true, if valid
     */
    default boolean isOK() {
        return HelperEnd.isOK(this.getStep());
    }

    /**
     * Returns the errors. If preconditions are invalid, all others errors in
     * checked steps are ignored.
     * 
     * <pre>
     * Assertor.that("toto part en vacances").contains("toto").and().contains("voyage")
     *         .and(Assertor.that("text").isBlank().or().not().contains("text")).getErrors();
     * 
     * // the message -&gt; "the char sequence 'toto part en vacances' should
     * // contain 'voyage' AND (the char sequence 'text' should be null, empty
     * // or blank OR the char sequence 'text' should NOT contain 'text')"
     * </pre>
     * 
     * @return an {@link Optional} containing the errors message
     */
    default Optional<String> getErrors() {
        return HelperEnd.getErrors(this.getStep());
    }

    /**
     * Get the last checked object.
     * 
     * <pre>
     * Assertor.that("text").isBlank().getNullable(); // =&gt; null
     * Assertor.that("text").isNotBlank().getNullable(); // =&gt; "text"
     * Assertor.that((String) null).isBlank().getNullable(); // =&gt; null
     * Assertor.that((String) null).isNotBlank().getNullable(); // =&gt; null
     * </pre>
     * 
     * @return the object (may be {@code null})
     */
    default T getNullable() {
        return HelperEnd.getNullable(this.getStep());
    }

    /**
     * Get the last checked object if not {@code null} or 'else'.
     * 
     * <pre>
     * Assertor.that("text").isBlank().orElse("def"); // =&gt; "def"
     * Assertor.that("text").isNotBlank().orElse("def"); // =&gt; "text"
     * Assertor.that((String) null).isBlank().orElse("def"); // =&gt; "def"
     * Assertor.that((String) null).isNotBlank().orElse("def"); // =&gt; "def"
     * Assertor.that("text").isBlank().orElse(null); // =&gt; null
     * </pre>
     * 
     * @param other
     *            default value, used if main value is {@code null}
     * @return the object (may be {@code null} if other is {@code null})
     */
    default T orElse(final T other) {
        return ObjectUtils.defaultIfNull(this.getNullable(), other);
    }

    /**
     * Get the last checked object if not {@code null} otherwise call 'else'
     * supplier.
     * 
     * <pre>
     * Assertor.that("text").isBlank().orElseGet(() -&gt; "def");
     * // =&gt; "def"
     * Assertor.that("text").isNotBlank().orElseGet(() -&gt; "def");
     * // =&gt; "text"
     * Assertor.that((String) null).isBlank().orElseGet(() -&gt; "def");
     * // =&gt; "def"
     * Assertor.that((String) null).isNotBlank().orElseGet(() -&gt; "def");
     * // =&gt; "def"
     * Assertor.that("text").isBlank().orElseGet(null);
     * // =&gt; throws NullPointerException
     * </pre>
     * 
     * @param other
     *            default value supplier, used if main value is {@code null}
     * @return the object (may be {@code null} if other is {@code null})
     * @throws NullPointerException
     *             if supplier is {@code null}
     */
    default T orElseGet(final Supplier<? extends T> other) {
        return ObjectUtils.defaultIfNull(this.getNullable(), other);
    }

    /**
     * Throw an {@link IllegalArgumentException} with assertion errors as
     * message, only if assertion is wrong.
     * 
     * <pre>
     * Assertor.that("text").isBlank().orElseThrow(); // throws an exception
     * Assertor.that("text").isNotBlank().orElseThrow(); // returns "text"
     * </pre>
     * 
     * @return the last checked object
     */
    default T orElseThrow() {
        return this.orElseThrow((CharSequence) null);
    }

    /**
     * Throw an {@link IllegalArgumentException} with message and arguments as
     * message, only if assertion is wrong. The arguments will be injected throw
     * the {@link String#format(String, Object...)} method. Parameters can also
     * be injected by using the same syntax, just add an asterisk/star at the
     * end.
     * 
     * <pre>
     * Assertor.that("text").isBlank().orElseThrow("text should be blank");
     * // -&gt; throw an exception with message: text should be blank
     * 
     * Assertor.that("text").isBlank().orElseThrow("param '%1$s*' should be %s", "blank");
     * // -&gt; throw an exception with message: param 'text' should be blank
     * 
     * Assertor.that("text").isNotBlank().orElseThrow("text should be blank");
     * // -&gt; do nothing
     * </pre>
     * 
     * @param message
     *            the message to thrown
     * @param arguments
     *            the messages arguments
     * @return the last checked object
     */
    default T orElseThrow(final CharSequence message, final Object... arguments) {
        return this.orElseThrow(null, message, arguments);
    }

    /**
     * Throw an {@link IllegalArgumentException} with message and arguments as
     * message, only if assertion is wrong. The arguments will be injected throw
     * the {@link String#format(Locale, String, Object...)} method. Parameters
     * can also be injected by using the same syntax, just add an asterisk/star
     * at the end.
     * 
     * <pre>
     * Assertor.that("text").isBlank().orElseThrow(Locale.US, "text should be blank");
     * // -&gt; throws an exception with message: text should be blank
     * 
     * Assertor.that(26.354f).isGT(27f).orElseThrow(Locale.US, "param '%1$.2f*' should be %s than '%2$.2f'", "greater");
     * // -&gt; throws an exception with message: param '26.35' should be greater
     * // than '27.00'
     * 
     * Assertor.that(26.354f).isGT(27f).orElseThrow(Locale.FRANCE, "param '%1$.2f*' should be %s than '%2$.2f'", "greater");
     * // -&gt; throws an exception with message: param '26,35' should be greater
     * // than '27,00'
     * 
     * Assertor.that("text").isNotBlank().orElseThrow(Locale.US, "text should be blank");
     * // -&gt; returns "text"
     * </pre>
     * 
     * @param locale
     *            the message locale
     * @param message
     *            the message to thrown
     * @param arguments
     *            the messages arguments
     * @return the last checked object
     */
    default T orElseThrow(final Locale locale, final CharSequence message, final Object... arguments) {
        return HelperEnd.orElseThrow(this.getStep(), locale, message, arguments);
    }

    /**
     * Calls the function and throw the specific exception, only if assertion is
     * wrong. The function provide two data:
     * <ul>
     * <li>first: the current error message</li>
     * <li>second: the list of parameters as {@link ParameterAssertor} of:
     * <ul>
     * <li>object to check</li>
     * <li>the type of object</li>
     * <li>if it's a checked object or a parameter</li>
     * </ul>
     * </li>
     * </ul>
     * 
     * <p>
     * The function inject original exception as suppressed
     * </p>
     * 
     * <pre>
     * Assertor.that("text").isBlank().orElseThrow((errors, parameters) -&gt; new MyException("text should be blank"));
     * // -&gt; throws a MyException with message: text should be blank
     * 
     * Assertor.that("text").isBlank().orElseThrow((errors, parameters) -&gt; new MyException(errors));
     * // -&gt; throws a MyException with errors message: the char sequence 'text'
     * // should be null, empty or blank
     * 
     * Assertor.that("text").isNotBlank().orElseThrow((errors, parameters) -&gt; new MyException("text should be blank"));
     * // -&gt; returns "text"
     * </pre>
     * 
     * @param function
     *            the function to apply if assertion is wrong (required, cannot
     *            be {@code null}, throw a {@link NullPointerException})
     * @return the last checked object
     * @param <E>
     *            the generic exception type
     * @throws E
     *             The type of exception to throw
     */
    default <E extends Throwable> T orElseThrow(final BiFunction<String, List<ParameterAssertor<?>>, E> function) throws E {
        return HelperEnd.orElseThrow(this.getStep(), function);
    }

    /**
     * Calls the function and throw the specific exception, only if assertion is
     * wrong. The function provide any data (back side, no message is generated,
     * so this cost less performance).
     * 
     * <pre>
     * Assertor.that("text").isBlank().orElseThrow((s) -&gt; new MyException("text should be blank"));
     * // -&gt; throw a MyException with message: text should be blank
     * 
     * Assertor.that("text").isNotBlank().orElseThrow(() -&gt; new MyException("text should be blank"));
     * // -&gt; do nothing
     * </pre>
     * 
     * @param exceptionSupplier
     *            the supplier to call if assertion is wrong (required, cannot
     *            be {@code null}, throw a {@link NullPointerException})
     * @return the last checked object
     * @param <E>
     *            the generic exception type
     * @throws E
     *             The type of exception to throw
     */
    default <E extends Throwable> T orElseThrow(final Supplier<E> exceptionSupplier) throws E {
        return HelperEnd.orElseThrow(this.getStep(), exceptionSupplier);
    }

    /**
     * Throw the specific exception, only if assertion is wrong and inject or
     * not the internal exception to the specified one as suppressed. The
     * suppressed can be read through the method
     * {@link Throwable#getSuppressed()}.
     * 
     * <pre>
     * Assertor.that("text").isBlank().orElseThrow(new MyException("text should be blank"), true);
     * // -&gt; throw a MyException with message: text should be blank and inject
     * // the original exception as suppressed
     * 
     * Assertor.that("text").isNotBlank().orElseThrow(new MyException("text should be blank"), true);
     * // -&gt; do nothing
     * </pre>
     * 
     * @param exception
     *            the specific exception
     * @param injectSuppressed
     *            if internal exception is added to the specific exception as
     *            suppressed
     * @return the last checked object
     * @param <E>
     *            the generic exception type
     * @throws E
     *             the type of exception to throw
     */
    default <E extends Throwable> T orElseThrow(final E exception, final boolean injectSuppressed) throws E {
        return HelperEnd.orElseThrow(this.getStep(), exception, injectSuppressed);
    }
}