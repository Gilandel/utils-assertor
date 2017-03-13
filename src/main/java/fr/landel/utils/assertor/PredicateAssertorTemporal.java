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

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Locale;

/**
 * This class define methods that can be applied on the checked comparable
 * {@link Temporal} object. To provide a result, it's also provide a chain
 * builder by returning a {@link PredicateStepTemporal}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorTemporal} &gt; {@link PredicateStepTemporal} &gt; {@link PredicateAssertorTemporal} &gt; {@link PredicateStepTemporal}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorTemporal} and ends
 * with {@link PredicateStepTemporal}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateAssertorTemporal<T extends Temporal & Comparable<T>> extends PredicateAssertor<PredicateStepTemporal<T>, T> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepTemporal<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorTemporal<T> not() {
        return () -> HelperAssertor.not(this.getStep());
    }

    /**
     * Check if the checked comparable {@link Temporal} is equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isEqual(temporal2).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isEqual(final T temporal) {
        return this.isEqual(temporal, (CharSequence) null);
    }

    /**
     * Check if the checked comparable {@link Temporal} is equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isEqual(temporal2, "not equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isEqual(final T temporal, final CharSequence message, final Object... arguments) {
        return this.isEqual(temporal, null, message, arguments);
    }

    /**
     * Check if the checked comparable {@link Temporal} is equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isEqual(temporal2, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isEqual(final T temporal, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorTemporal.isEqual(this.getStep(), temporal, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked comparable {@link Temporal} is NOT equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isNotEqual(temporal2).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isNotEqual(final T temporal) {
        return this.isNotEqual(temporal, (CharSequence) null);
    }

    /**
     * Check if the checked comparable {@link Temporal} is NOT equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isNotEqual(temporal2, "not equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isNotEqual(final T temporal, final CharSequence message, final Object... arguments) {
        return this.isNotEqual(temporal, null, message, arguments);
    }

    /**
     * Check if the checked comparable {@link Temporal} is NOT equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isNotEqual(temporal2, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale}) (only used to format
     *            this message, otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isNotEqual(final T temporal, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorTemporal.isNotEqual(this.getStep(), temporal, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is around the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAround(temporal2, Period.ofYears(1)).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAround(final T temporal, final TemporalAmount temporalAmount) {
        return this.isAround(temporal, temporalAmount, null);
    }

    /**
     * Check if the checked {@link Temporal} is around the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAround(temporal2, Period.ofYears(1), "not around").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAround(final T temporal, final TemporalAmount temporalAmount, final CharSequence message,
            final Object... arguments) {
        return this.isAround(temporal, temporalAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is around the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAround(temporal2, Period.ofYears(1), Locale.US, "not around").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAround(final T temporal, final TemporalAmount temporalAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorTemporal.isAround(this.getStep(), temporal, temporalAmount, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is NOT around the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isNotAround(temporal2, Period.ofYears(1)).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isNotAround(final T temporal, final TemporalAmount temporalAmount) {
        return this.isNotAround(temporal, temporalAmount, null);
    }

    /**
     * Check if the checked {@link Temporal} is NOT around the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isNotAround(temporal2, Period.ofYears(1), "must not be around").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isNotAround(final T temporal, final TemporalAmount temporalAmount, final CharSequence message,
            final Object... arguments) {
        return this.isNotAround(temporal, temporalAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is NOT around the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isNotAround(temporal2, Period.ofYears(1), Locale.US, "must not be around").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isNotAround(final T temporal, final TemporalAmount temporalAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorTemporal.isNotAround(this.getStep(), temporal, temporalAmount, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is after the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfter(temporal2).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfter(final T temporal) {
        return this.isAfter(temporal, (CharSequence) null);
    }

    /**
     * Check if the checked {@link Temporal} is after the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfter(temporal2, "not after").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfter(final T temporal, final CharSequence message, final Object... arguments) {
        return this.isAfter(temporal, (Locale) null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is after the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfter(temporal2, Locale.US, "not after").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfter(final T temporal, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorTemporal.isAfter(this.getStep(), temporal, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is after the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfter(temporal2, Period.ofMonths(1)).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfter(final T temporal, final TemporalAmount temporalAmount) {
        return this.isAfter(temporal, temporalAmount, null);
    }

    /**
     * Check if the checked {@link Temporal} is after the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfter(temporal2, Period.ofMonths(1), "not after").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfter(final T temporal, final TemporalAmount temporalAmount, final CharSequence message,
            final Object... arguments) {
        return this.isAfter(temporal, temporalAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is after the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfter(temporal2, Period.ofMonths(1), Locale.US, "not after").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfter(final T temporal, final TemporalAmount temporalAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorTemporal.isAfter(this.getStep(), temporal, temporalAmount, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is after or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfterOrEqual(temporal2).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfterOrEqual(final T temporal) {
        return this.isAfterOrEqual(temporal, (CharSequence) null);
    }

    /**
     * Check if the checked {@link Temporal} is after or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfterOrEqual(temporal2, "not after or equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfterOrEqual(final T temporal, final CharSequence message, final Object... arguments) {
        return this.isAfterOrEqual(temporal, (Locale) null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is after or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfterOrEqual(temporal2, Locale.US, "not after or equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfterOrEqual(final T temporal, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorTemporal.isAfterOrEqual(this.getStep(), temporal, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is after or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfterOrEqual(temporal2, Period.ofMonths(1)).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfterOrEqual(final T temporal, final TemporalAmount temporalAmount) {
        return this.isAfterOrEqual(temporal, temporalAmount, null);
    }

    /**
     * Check if the checked {@link Temporal} is after or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfterOrEqual(temporal2, Period.ofMonths(1), "not after or equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfterOrEqual(final T temporal, final TemporalAmount temporalAmount, final CharSequence message,
            final Object... arguments) {
        return this.isAfterOrEqual(temporal, temporalAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is after or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isAfterOrEqual(temporal2, Period.ofMonths(1), Locale.US, "not after or equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isAfterOrEqual(final T temporal, final TemporalAmount temporalAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorTemporal.isAfterOrEqual(this.getStep(), temporal, temporalAmount,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is before to the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBefore(temporal2).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBefore(final T temporal) {
        return this.isBefore(temporal, (CharSequence) null);
    }

    /**
     * Check if the checked {@link Temporal} is before to the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBefore(temporal2, "not before").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBefore(final T temporal, final CharSequence message, final Object... arguments) {
        return this.isBefore(temporal, (Locale) null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is before to the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBefore(temporal2, Locale.US, "not before").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBefore(final T temporal, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorTemporal.isBefore(this.getStep(), temporal, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is before to the {@code temporal}.
     *
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBefore(temporal2, Period.ofMonths(1)).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBefore(final T temporal, final TemporalAmount temporalAmount) {
        return this.isBefore(temporal, temporalAmount, null);
    }

    /**
     * Check if the checked {@link Temporal} is before to the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBefore(temporal2, Period.ofMonths(1), "not before").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBefore(final T temporal, final TemporalAmount temporalAmount, final CharSequence message,
            final Object... arguments) {
        return this.isBefore(temporal, temporalAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is before to the {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBefore(temporal2, Period.ofMonths(1), Locale.US, "not before").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBefore(final T temporal, final TemporalAmount temporalAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorTemporal.isBefore(this.getStep(), temporal, temporalAmount, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is before or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBeforeOrEqual(temporal2).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBeforeOrEqual(final T temporal) {
        return this.isBeforeOrEqual(temporal, (CharSequence) null);
    }

    /**
     * Check if the checked {@link Temporal} is before or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBeforeOrEqual(temporal2, "not before or equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBeforeOrEqual(final T temporal, final CharSequence message, final Object... arguments) {
        return this.isBeforeOrEqual(temporal, (Locale) null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is before or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBeforeOrEqual(temporal2, Locale.US, "not before or equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBeforeOrEqual(final T temporal, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorTemporal.isBeforeOrEqual(this.getStep(), temporal, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Temporal} is before or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBeforeOrEqual(temporal2, Period.ofMonths(1)).orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBeforeOrEqual(final T temporal, final TemporalAmount temporalAmount) {
        return this.isBeforeOrEqual(temporal, temporalAmount, null);
    }

    /**
     * Check if the checked {@link Temporal} is before or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBeforeOrEqual(temporal2, Period.ofMonths(1), "not before or equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBeforeOrEqual(final T temporal, final TemporalAmount temporalAmount, final CharSequence message,
            final Object... arguments) {
        return this.isBeforeOrEqual(temporal, temporalAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Temporal} is before or equal to the
     * {@code temporal}.
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(temporal).isBeforeOrEqual(temporal2, Period.ofMonths(1), Locale.US, "not before or equal").orElseThrow();
     * </pre>
     * 
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepTemporal<T> isBeforeOrEqual(final T temporal, final TemporalAmount temporalAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorTemporal.isBeforeOrEqual(this.getStep(), temporal, temporalAmount,
                MessageAssertor.of(locale, message, arguments));
    }
}
