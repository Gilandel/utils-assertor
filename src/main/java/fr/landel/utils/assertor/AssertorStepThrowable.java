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

import java.util.Locale;
import java.util.regex.Pattern;

import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorThrowable;

/**
 * This class define methods that can be applied on the checked
 * {@link Throwable} object. To provide a result, it's also provide a chain
 * builder by returning a {@link AssertorStepThrowable}. The chain looks like:
 * 
 * <pre>
 * {@link AssertorStepThrowable} &gt; {@link AssertorStepThrowable} &gt; {@link AssertorStepThrowable} &gt; {@link AssertorStepThrowable}...
 * </pre>
 * 
 * This chain always starts with a {@link AssertorStepThrowable} and ends with
 * {@link AssertorStepThrowable}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface AssertorStepThrowable<T extends Throwable> extends AssertorStep<StepThrowable<T>, T> {

    /**
     * {@inheritDoc}
     */
    @Override
    default StepThrowable<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default AssertorStepThrowable<T> not() {
        return () -> HelperStep.not(getStep());
    }

    /**
     * Asserts that the given {@link Throwable} is an instance of {@code clazz}
     * and has the specified message.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isInstanceOf(type, "Internal error").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param throwableMessage
     *            the expected throwable message
     * @return The operator
     */
    default StepThrowable<T> isInstanceOf(final Class<?> clazz, final CharSequence throwableMessage) {
        return this.isInstanceOf(clazz, throwableMessage, (CharSequence) null);
    }

    /**
     * Asserts that the given {@link Throwable} is an instance of {@code clazz}
     * and has the specified message.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isInstanceOf(type, "Internal error", "not an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param throwableMessage
     *            the expected throwable message
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> isInstanceOf(final Class<?> clazz, final CharSequence throwableMessage, final CharSequence message,
            final Object... arguments) {
        return this.isInstanceOf(clazz, throwableMessage, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} is an instance of {@code clazz}
     * and has the specified message.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isInstanceOf(type, "Internal error", Locale.US, "not an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param throwableMessage
     *            the expected throwable message
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> isInstanceOf(final Class<?> clazz, final CharSequence throwableMessage, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.isInstanceOf(this.getStep(), clazz, throwableMessage,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} is an instance of {@code clazz}
     * and matches the pattern.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isInstanceOf(type, pattern).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @return The operator
     */
    default StepThrowable<T> isInstanceOf(final Class<?> clazz, final Pattern pattern) {
        return this.isInstanceOf(clazz, pattern, null);
    }

    /**
     * Asserts that the given {@link Throwable} is an instance of {@code clazz}
     * and matches the pattern.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isInstanceOf(type, pattern, "not an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> isInstanceOf(final Class<?> clazz, final Pattern pattern, final CharSequence message,
            final Object... arguments) {
        return this.isInstanceOf(clazz, pattern, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} is an instance of {@code clazz}
     * and matches the pattern.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isInstanceOf(type, pattern, Locale.US, "not an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> isInstanceOf(final Class<?> clazz, final Pattern pattern, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorThrowable.isInstanceOf(this.getStep(), clazz, pattern, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} is assignable from {@code clazz}
     * and has the specified message.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isAssignableFrom(type, "Internal error").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param throwableMessage
     *            the expected throwable message
     * @return The operator
     */
    default StepThrowable<T> isAssignableFrom(final Class<?> clazz, final CharSequence throwableMessage) {
        return this.isAssignableFrom(clazz, throwableMessage, (CharSequence) null);
    }

    /**
     * Asserts that the given {@link Throwable} is assignable from {@code clazz}
     * and has the specified message.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isAssignableFrom(type, "Internal error", "not an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param throwableMessage
     *            the expected throwable message
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> isAssignableFrom(final Class<?> clazz, final CharSequence throwableMessage, final CharSequence message,
            final Object... arguments) {
        return this.isAssignableFrom(clazz, throwableMessage, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} is assignable from {@code clazz}
     * and has the specified message.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isAssignableFrom(type, "Internal error", Locale.US, "not an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param throwableMessage
     *            the expected throwable message
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> isAssignableFrom(final Class<?> clazz, final CharSequence throwableMessage, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.isAssignableFrom(this.getStep(), clazz, throwableMessage,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} is assignable from {@code clazz}
     * and matches the pattern.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isAssignableFrom(type, pattern).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @return The operator
     */
    default StepThrowable<T> isAssignableFrom(final Class<?> clazz, final Pattern pattern) {
        return this.isAssignableFrom(clazz, pattern, null);
    }

    /**
     * Asserts that the given {@link Throwable} is assignable from {@code clazz}
     * and matches the pattern.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isAssignableFrom(type, pattern, "not assignable from").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> isAssignableFrom(final Class<?> clazz, final Pattern pattern, final CharSequence message,
            final Object... arguments) {
        return this.isAssignableFrom(clazz, pattern, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} is assignable from {@code clazz}
     * and matches the pattern.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).isAssignableFrom(type, pattern, Locale.US, "not assignable from").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> isAssignableFrom(final Class<?> clazz, final Pattern pattern, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorThrowable.isAssignableFrom(this.getStep(), clazz, pattern, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} has a cause not {@code null}
     * 
     * <p>
     * precondition: throwable cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseNotNull().orElseThrow();
     * </pre>
     * 
     * @return The operator
     */
    default StepThrowable<T> hasCauseNotNull() {
        return this.hasCauseNotNull(null);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause not {@code null}
     * 
     * <p>
     * precondition: throwable cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseNotNull("cause cannot be null").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseNotNull(final CharSequence message, final Object... arguments) {
        return this.hasCauseNotNull(null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause not {@code null}
     * 
     * <p>
     * precondition: throwable cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseNotNull(Locale.US, "cause cannot be null").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseNotNull(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.hasCauseNotNull(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} has a cause {@code null}
     * 
     * <p>
     * precondition: throwable cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseNull().orElseThrow();
     * </pre>
     * 
     * @return The operator
     */
    default StepThrowable<T> hasCauseNull() {
        return this.hasCauseNull(null);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause {@code null}
     * 
     * <p>
     * precondition: throwable cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseNull("cause must be null").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseNull(final CharSequence message, final Object... arguments) {
        return this.hasCauseNull(null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause {@code null}
     * 
     * <p>
     * precondition: throwable cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseNull(Locale.US, "cause must be null").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseNull(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.hasCauseNull(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz}. If {@code recursively} is set to true, the method will
     * check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, true).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final boolean recursively) {
        return this.hasCauseAssignableFrom(clazz, recursively, null);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz}. If {@code recursively} is set to true, the method will
     * check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, true, "not assignable from").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final boolean recursively, final CharSequence message,
            final Object... arguments) {
        return this.hasCauseAssignableFrom(clazz, recursively, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz}. If {@code recursively} is set to true, the method will
     * check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, true, Locale.US, "not assignable from").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final boolean recursively, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.hasCauseAssignableFrom(this.getStep(), clazz, recursively,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, exceptionMessage).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param exceptionMessage
     *            the exception message
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final CharSequence exceptionMessage, final boolean recursively) {
        return this.hasCauseAssignableFrom(clazz, exceptionMessage, recursively, null);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, exceptionMessage, true, "not assignable from").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param exceptionMessage
     *            the exception message
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final CharSequence exceptionMessage, final boolean recursively,
            final CharSequence message, final Object... arguments) {
        return this.hasCauseAssignableFrom(clazz, exceptionMessage, recursively, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, exceptionMessage, true, Locale.US, "not assignable from").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param exceptionMessage
     *            the exception message
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final CharSequence exceptionMessage, final boolean recursively,
            final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.hasCauseAssignableFrom(this.getStep(), clazz, exceptionMessage, recursively,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, pattern, true).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final Pattern pattern, final boolean recursively) {
        return this.hasCauseAssignableFrom(clazz, pattern, recursively, null);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, pattern, true, "not assignable from").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final Pattern pattern, final boolean recursively,
            final CharSequence message, final Object... arguments) {
        return this.hasCauseAssignableFrom(clazz, pattern, recursively, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause assignable from
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseAssignableFrom(type, pattern, true, Locale.US, "not assignable from").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseAssignableFrom(final Class<?> clazz, final Pattern pattern, final boolean recursively,
            final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.hasCauseAssignableFrom(this.getStep(), clazz, pattern, recursively,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz}. If {@code recursively} is set to true, the method will
     * check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, true).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final boolean recursively) {
        return this.hasCauseInstanceOf(clazz, recursively, null);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz}. If {@code recursively} is set to true, the method will
     * check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, true, "not with an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final boolean recursively, final CharSequence message,
            final Object... arguments) {
        return this.hasCauseInstanceOf(clazz, recursively, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz}. If {@code recursively} is set to true, the method will
     * check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, true, Locale.US, "not with an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final boolean recursively, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.hasCauseInstanceOf(this.getStep(), clazz, recursively,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, exceptionMessage).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param exceptionMessage
     *            the exception message
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final CharSequence exceptionMessage, final boolean recursively) {
        return this.hasCauseInstanceOf(clazz, exceptionMessage, recursively, null);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, exceptionMessage, true, "not with an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param exceptionMessage
     *            the exception message
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final CharSequence exceptionMessage, final boolean recursively,
            final CharSequence message, final Object... arguments) {
        return this.hasCauseInstanceOf(clazz, exceptionMessage, recursively, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable and clazz cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, exceptionMessage, true, Locale.US, "not with an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param exceptionMessage
     *            the exception message
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final CharSequence exceptionMessage, final boolean recursively,
            final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.hasCauseInstanceOf(this.getStep(), clazz, exceptionMessage, recursively,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, pattern, true).orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final Pattern pattern, final boolean recursively) {
        return this.hasCauseInstanceOf(clazz, pattern, recursively, null);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, pattern, true, "not with an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final Pattern pattern, final boolean recursively,
            final CharSequence message, final Object... arguments) {
        return this.hasCauseInstanceOf(clazz, pattern, recursively, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Throwable} has a cause with an instance of
     * {@code clazz} and matches the pattern. If {@code recursively} is set to
     * true, the method will check the cause of cause recursively.
     * 
     * <p>
     * precondition: throwable, clazz and pattern cannot be null
     * </p>
     * 
     * <pre>
     * Assertor.that(throwable).hasCauseInstanceOf(type, pattern, true, Locale.US, "not with an instance of").orElseThrow();
     * </pre>
     * 
     * @param clazz
     *            The super {@link Class} (super class or interface)
     * @param pattern
     *            the message pattern
     * @param recursively
     *            if true, sub causes are checked until a cause has the type and
     *            matches the pattern
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default StepThrowable<T> hasCauseInstanceOf(final Class<?> clazz, final Pattern pattern, final boolean recursively, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorThrowable.hasCauseInstanceOf(this.getStep(), clazz, pattern, recursively,
                MessageAssertor.of(locale, message, arguments));
    }
}
