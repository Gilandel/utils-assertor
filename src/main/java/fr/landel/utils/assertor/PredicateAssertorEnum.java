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

/**
 * This class define methods that can be applied on the checked {@link Enum}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepEnum}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorEnum} &gt; {@link PredicateStepEnum} &gt; {@link PredicateAssertorEnum} &gt; {@link PredicateStepEnum}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorEnum} and ends with
 * {@link PredicateStepEnum}.
 *
 * @since Aug 7, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateAssertorEnum<T extends Enum<T>> extends PredicateAssertor<PredicateStepEnum<T>, T> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepEnum<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorEnum<T> not() {
        return () -> HelperAssertor.not(getStep());
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name}.
     * 
     * <p>
     * precondition: {@link Enum} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasName(name).orElseThrow();
     * </pre>
     * 
     * @param name
     *            The name
     * @return The operator
     */
    default PredicateStepEnum<T> hasName(final CharSequence name) {
        return this.hasName(name, null);
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name}.
     * 
     * <p>
     * precondition: {@link Enum} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasName(name, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The name
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepEnum<T> hasName(final CharSequence name, final CharSequence message, final Object... arguments) {
        return this.hasName(name, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name}.
     * 
     * <p>
     * precondition: {@link Enum} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasName(name, Locale.US, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The name
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepEnum<T> hasName(final CharSequence name, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorEnum.hasName(this.getStep(), name, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name},
     * ignoring case considerations.
     * 
     * <p>
     * precondition: {@link Enum} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasNameIgnoreCase(name).orElseThrow();
     * </pre>
     * 
     * @param name
     *            The name
     * @return The operator
     */
    default PredicateStepEnum<T> hasNameIgnoreCase(final CharSequence name) {
        return this.hasNameIgnoreCase(name, null);
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name},
     * ignoring case considerations.
     * 
     * <p>
     * precondition: {@link Enum} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasNameIgnoreCase(name, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The name
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepEnum<T> hasNameIgnoreCase(final CharSequence name, final CharSequence message, final Object... arguments) {
        return this.hasNameIgnoreCase(name, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code name},
     * ignoring case considerations.
     * 
     * <p>
     * precondition: {@link Enum} cannot be null and {@code name} cannot be
     * {@code null} or empty
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasNameIgnoreCase(name, Locale.US, "bad name").orElseThrow();
     * </pre>
     * 
     * @param name
     *            The name
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepEnum<T> hasNameIgnoreCase(final CharSequence name, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorEnum.hasNameIgnoreCase(this.getStep(), name, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code ordinal}.
     * 
     * <p>
     * precondition: the {@code ordinal} cannot be lower than 0
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasOrdinal(ordinal).orElseThrow();
     * </pre>
     * 
     * @param ordinal
     *            The ordinal
     * @return The operator
     */
    default PredicateStepEnum<T> hasOrdinal(final int ordinal) {
        return this.hasOrdinal(ordinal, null);
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code ordinal}.
     * 
     * <p>
     * precondition: the {@code ordinal} cannot be lower than 0
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasOrdinal(ordinal, "bad ordinal").orElseThrow();
     * </pre>
     * 
     * @param ordinal
     *            The ordinal
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepEnum<T> hasOrdinal(final int ordinal, final CharSequence message, final Object... arguments) {
        return this.hasOrdinal(ordinal, null, message, arguments);
    }

    /**
     * Asserts that the given {@link Class} has the specified {@code ordinal}.
     * 
     * <p>
     * precondition: the {@code ordinal} cannot be lower than 0
     * </p>
     * 
     * <pre>
     * Assertor.that(enumeration).hasOrdinal(ordinal, Locale.US, "bad ordinal").orElseThrow();
     * </pre>
     * 
     * @param ordinal
     *            The ordinal
     * @param locale
     *            The locale of the message (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepEnum<T> hasOrdinal(final int ordinal, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorEnum.hasOrdinal(this.getStep(), ordinal, MessageAssertor.of(locale, message, arguments));
    }
}
