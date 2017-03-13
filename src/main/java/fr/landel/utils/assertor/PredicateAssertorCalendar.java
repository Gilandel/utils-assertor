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

import java.util.Calendar;
import java.util.Locale;

/**
 * This class define methods that can be applied on the checked {@link Calendar}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepCalendar}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorCalendar} &gt; {@link PredicateStepCalendar} &gt; {@link PredicateAssertorCalendar} &gt; {@link PredicateStepCalendar}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorCalendar} and ends
 * with {@link PredicateStepCalendar}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateAssertorCalendar extends PredicateAssertor<PredicateStepCalendar, Calendar> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepCalendar get(final StepAssertor<Calendar> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorCalendar not() {
        return () -> HelperAssertor.not(this.getStep());
    }

    /**
     * Check if the checked {@link Calendar} is equal to the {@code date}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isEqual(calendar2).orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @return the assertor step
     */
    default PredicateStepCalendar isEqual(final Calendar date) {
        return this.isEqual(date, null);
    }

    /**
     * Check if the checked {@link Calendar} is equal to the {@code date}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isEqual(calendar2, "not equal").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isEqual(final Calendar date, final CharSequence message, final Object... arguments) {
        return this.isEqual(date, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is equal to the {@code date}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isEqual(calendar2, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isEqual(final Calendar date, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorDate.isEqual(this.getStep(), date, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is NOT equal to the {@code date}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isNotEqual(calendar2).orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @return the assertor step
     */
    default PredicateStepCalendar isNotEqual(final Calendar date) {
        return this.isNotEqual(date, null);
    }

    /**
     * Check if the checked {@link Calendar} is NOT equal to the {@code date}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isNotEqual(calendar2, "not equal").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isNotEqual(final Calendar date, final CharSequence message, final Object... arguments) {
        return this.isNotEqual(date, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is NOT equal to the {@code date}.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isNotEqual(calendar2, Locale.US, "not equal").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
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
    default PredicateStepCalendar isNotEqual(final Calendar date, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorDate.isNotEqual(this.getStep(), date, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is around the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isEqual}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAround(calendar2, Calendar.DAY_OF_YEAR, 1).orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @return the assertor step
     */
    default PredicateStepCalendar isAround(final Calendar date, final int calendarField, final int calendarAmount) {
        return this.isAround(date, calendarField, calendarAmount, null);
    }

    /**
     * Check if the checked {@link Calendar} is around the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isEqual}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAround(calendar2, Calendar.DAY_OF_YEAR, 1, "not around").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAround(final Calendar date, final int calendarField, final int calendarAmount,
            final CharSequence message, final Object... arguments) {
        return this.isAround(date, calendarField, calendarAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is around the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isEqual}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAround(calendar2, Calendar.DAY_OF_YEAR, 1, Locale.US, "not around").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAround(final Calendar date, final int calendarField, final int calendarAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorDate.isAround(this.getStep(), date, calendarField, calendarAmount,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is NOT around the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isNotEqual}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isNotAround(calendar2, Calendar.DAY_OF_YEAR, 1).orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @return the assertor step
     */
    default PredicateStepCalendar isNotAround(final Calendar date, final int calendarField, final int calendarAmount) {
        return this.isNotAround(date, calendarField, calendarAmount, null);
    }

    /**
     * Check if the checked {@link Calendar} is NOT around the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isNotEqual}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isNotAround(calendar2, Calendar.DAY_OF_YEAR, 1, "must not be around").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isNotAround(final Calendar date, final int calendarField, final int calendarAmount,
            final CharSequence message, final Object... arguments) {
        return this.isNotAround(date, calendarField, calendarAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is NOT around the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isNotEqual}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isNotAround(calendar2, Calendar.DAY_OF_YEAR, 1, Locale.US, "must not be around").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isNotAround(final Calendar date, final int calendarField, final int calendarAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorDate.isNotAround(this.getStep(), date, calendarField, calendarAmount,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is after the {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfter(calendar2).orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @return the assertor step
     */
    default PredicateStepCalendar isAfter(final Calendar date) {
        return this.isAfter(date, null);
    }

    /**
     * Check if the checked {@link Calendar} is after the {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfter(calendar2, "not after").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAfter(final Calendar date, final CharSequence message, final Object... arguments) {
        return this.isAfter(date, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is after the {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfter(calendar2, Locale.US, "not after").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAfter(final Calendar date, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorDate.isAfter(this.getStep(), date, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is after the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isAfter(Calendar)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfter(calendar2, Calendar.MONTH, 1).orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @return the assertor step
     */
    default PredicateStepCalendar isAfter(final Calendar date, final int calendarField, final int calendarAmount) {
        return this.isAfter(date, calendarField, calendarAmount, null);
    }

    /**
     * Check if the checked {@link Calendar} is after the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isAfter(Calendar, CharSequence, Object...)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfter(calendar2, Calendar.MONTH, 1, "not after").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAfter(final Calendar date, final int calendarField, final int calendarAmount,
            final CharSequence message, final Object... arguments) {
        return this.isAfter(date, calendarField, calendarAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is after the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isAfter(Calendar, Locale, CharSequence, Object...)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfter(calendar2, Calendar.MONTH, 1, Locale.US, "not after").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAfter(final Calendar date, final int calendarField, final int calendarAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorDate.isAfter(this.getStep(), date, calendarField, calendarAmount,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is after or equal to the
     * {@code date}.
     * 
     * <p>
     * precondition: neither can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfterOrEqual(calendar2).orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @return the assertor step
     */
    default PredicateStepCalendar isAfterOrEqual(final Calendar date) {
        return this.isAfterOrEqual(date, null);
    }

    /**
     * Check if the checked {@link Calendar} is after or equal to the
     * {@code date}.
     * 
     * <p>
     * precondition: neither can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfterOrEqual(calendar2, "not after or equal").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAfterOrEqual(final Calendar date, final CharSequence message, final Object... arguments) {
        return this.isAfterOrEqual(date, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is after or equal to the
     * {@code date}.
     * 
     * <p>
     * precondition: neither can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfterOrEqual(calendar2, Locale.US, "not after or equal").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAfterOrEqual(final Calendar date, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorDate.isAfterOrEqual(this.getStep(), date, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is after or equal to the
     * {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isAfterOrEqual(Calendar)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfterOrEqual(calendar2, Calendar.MONTH, 1).orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @return the assertor step
     */
    default PredicateStepCalendar isAfterOrEqual(final Calendar date, final int calendarField, final int calendarAmount) {
        return this.isAfterOrEqual(date, calendarField, calendarAmount, null);
    }

    /**
     * Check if the checked {@link Calendar} is after or equal to the
     * {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isAfterOrEqual(Calendar, CharSequence, Object...)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfterOrEqual(calendar2, Calendar.MONTH, 1, "not after or equal").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAfterOrEqual(final Calendar date, final int calendarField, final int calendarAmount,
            final CharSequence message, final Object... arguments) {
        return this.isAfterOrEqual(date, calendarField, calendarAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is after or equal to the
     * {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as
     * {@link #isAfterOrEqual(Calendar, Locale, CharSequence, Object...)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isAfterOrEqual(calendar2, Calendar.MONTH, 1, Locale.US, "not after or equal").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isAfterOrEqual(final Calendar date, final int calendarField, final int calendarAmount,
            final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorDate.isAfterOrEqual(this.getStep(), date, calendarField, calendarAmount,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is before to the {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBefore(calendar2).orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @return the assertor step
     */
    default PredicateStepCalendar isBefore(final Calendar date) {
        return this.isBefore(date, null);
    }

    /**
     * Check if the checked {@link Calendar} is before to the {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBefore(calendar2, "not before").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isBefore(final Calendar date, final CharSequence message, final Object... arguments) {
        return this.isBefore(date, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is before to the {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBefore(calendar2, Locale.US, "not before").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isBefore(final Calendar date, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorDate.isBefore(this.getStep(), date, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is before to the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isBefore(Calendar)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBefore(calendar2, Calendar.MONTH, 1).orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @return the assertor step
     */
    default PredicateStepCalendar isBefore(final Calendar date, final int calendarField, final int calendarAmount) {
        return this.isBefore(date, calendarField, calendarAmount, null);
    }

    /**
     * Check if the checked {@link Calendar} is before to the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isBefore(Calendar, CharSequence, Object...)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBefore(calendar2, Calendar.MONTH, 1, "not before").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isBefore(final Calendar date, final int calendarField, final int calendarAmount,
            final CharSequence message, final Object... arguments) {
        return this.isBefore(date, calendarField, calendarAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is before to the {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isBefore(Calendar, Locale, CharSequence, Object...)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBefore(calendar2, Calendar.MONTH, 1, Locale.US, "not before").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isBefore(final Calendar date, final int calendarField, final int calendarAmount, final Locale locale,
            final CharSequence message, final Object... arguments) {
        return () -> AssertorDate.isBefore(this.getStep(), date, calendarField, calendarAmount,
                MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is before or equal to the
     * {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBeforeOrEqual(calendar2).orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @return the assertor step
     */
    default PredicateStepCalendar isBeforeOrEqual(final Calendar date) {
        return this.isBeforeOrEqual(date, null);
    }

    /**
     * Check if the checked {@link Calendar} is before or equal to the
     * {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBeforeOrEqual(calendar2, "not before or equal").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isBeforeOrEqual(final Calendar date, final CharSequence message, final Object... arguments) {
        return this.isBeforeOrEqual(date, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is before or equal to the
     * {@code date}.
     * 
     * <p>
     * precondition: neither dates can be {@code null}
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBeforeOrEqual(calendar2, Locale.US, "not before or equal").orElseThrow();
     * </pre>
     * 
     * @param date
     *            the date to compare
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isBeforeOrEqual(final Calendar date, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorDate.isBeforeOrEqual(this.getStep(), date, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Check if the checked {@link Calendar} is before or equal to the
     * {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isBeforeOrEqual(Calendar)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBeforeOrEqual(calendar2, Calendar.MONTH, 1).orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @return the assertor step
     */
    default PredicateStepCalendar isBeforeOrEqual(final Calendar date, final int calendarField, final int calendarAmount) {
        return this.isBeforeOrEqual(date, calendarField, calendarAmount, null);
    }

    /**
     * Check if the checked {@link Calendar} is before or equal to the
     * {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as {@link #isBeforeOrEqual(Calendar, CharSequence, Object...)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBeforeOrEqual(calendar2, Calendar.MONTH, 1, "not before or equal").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isBeforeOrEqual(final Calendar date, final int calendarField, final int calendarAmount,
            final CharSequence message, final Object... arguments) {
        return this.isBeforeOrEqual(date, calendarField, calendarAmount, null, message, arguments);
    }

    /**
     * Check if the checked {@link Calendar} is before or equal to the
     * {@code date}.
     * 
     * <p>
     * If the {@code calendarField} is equal to -1, the method does exactly the
     * same as
     * {@link #isBeforeOrEqual(Calendar, Locale, CharSequence, Object...)}.
     * </p>
     * 
     * <p>
     * precondition: if {@code calendarField} is not equal to -1. Neither dates
     * can be {@code null}, the {@code calendarField} must be a valid field and
     * the {@code calendarAmount} cannot be equal to zero.
     * </p>
     * 
     * <pre>
     * Assertor.that(calendar).isBeforeOrEqual(calendar2, Calendar.MONTH, 1, Locale.US, "not before or equal").orElseThrow();
     * </pre>
     * 
     * Valid calendar field:
     * <ul>
     * <li>{@link Calendar#ERA}</li>
     * <li>{@link Calendar#YEAR}</li>
     * <li>{@link Calendar#MONTH}</li>
     * <li>{@link Calendar#WEEK_OF_YEAR}</li>
     * <li>{@link Calendar#WEEK_OF_MONTH}</li>
     * <li>{@link Calendar#DATE}</li>
     * <li>{@link Calendar#DAY_OF_MONTH}</li>
     * <li>{@link Calendar#DAY_OF_YEAR}</li>
     * <li>{@link Calendar#DAY_OF_WEEK}</li>
     * <li>{@link Calendar#DAY_OF_WEEK_IN_MONTH}</li>
     * <li>{@link Calendar#AM_PM}</li>
     * <li>{@link Calendar#HOUR}</li>
     * <li>{@link Calendar#HOUR_OF_DAY}</li>
     * <li>{@link Calendar#MINUTE}</li>
     * <li>{@link Calendar#SECOND}</li>
     * <li>{@link Calendar#MILLISECOND}</li>
     * </ul>
     * 
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param locale
     *            the message locale (only used to format this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            the message on mismatch
     * @param arguments
     *            the message arguments
     * @return the assertor step
     */
    default PredicateStepCalendar isBeforeOrEqual(final Calendar date, final int calendarField, final int calendarAmount,
            final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorDate.isBeforeOrEqual(this.getStep(), date, calendarField, calendarAmount,
                MessageAssertor.of(locale, message, arguments));
    }
}
