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

import java.util.Locale;

import fr.landel.utils.assertor.Assertor;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.helper.HelperStep;
import fr.landel.utils.assertor.utils.AssertorNumber;

/**
 * This class define methods that can be applied on the checked {@link Number}
 * object. To provide a result, it's also provide a chain builder by returning a
 * {@link PredicateStepNumber}. The chain looks like:
 * 
 * <pre>
 * {@link PredicateAssertorStepNumber} &gt; {@link PredicateStepNumber} &gt; {@link PredicateAssertorStepNumber} &gt; {@link PredicateStepNumber}...
 * </pre>
 * 
 * This chain always starts with a {@link PredicateAssertorStepNumber} and ends
 * with {@link PredicateStepNumber}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 * @param <N>
 *            The type of checked object
 */
@FunctionalInterface
public interface PredicateAssertorStepNumber<N extends Number & Comparable<N>> extends PredicateAssertorStep<PredicateStepNumber<N>, N> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepNumber<N> get(final StepAssertor<N> result) {
        return () -> result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateAssertorStepNumber<N> not() {
        return () -> HelperStep.not(this.getStep());
    }

    /**
     * Asserts that the given number is equal to the specified one. Beware about
     * floating numbers comparison, check the link below.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).istEqual(number2).orElseThrow();
     * </pre>
     * 
     * @see <a href=
     *      "http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/"></a>
     * 
     * @param number
     *            The number to compare
     * @return The operator
     */
    default PredicateStepNumber<N> isEqual(final N number) {
        return this.isEqual(number, null);
    }

    /**
     * Asserts that the given number is equal to the specified one. Beware about
     * floating numbers comparison, check the link below.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).istEqual(number2, "%1$d* cannot be equal to %2$d*").orElseThrow();
     * </pre>
     * 
     * @see <a href=
     *      "http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/"></a>
     * 
     * @param number
     *            The number to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isEqual(final N number, final CharSequence message, final Object... arguments) {
        return this.isEqual(number, null, message, arguments);
    }

    /**
     * Asserts that the given number is equal to the specified one. Beware about
     * floating numbers comparison, check the link below.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).istEqual(number2, Locale.US, "%1$d* cannot be equal to %2$d*").orElseThrow();
     * </pre>
     * 
     * @see <a href=
     *      "http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/"></a>
     * 
     * @param number
     *            The number to compare
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isEqual(final N number, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isEqual(this.getStep(), number, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given number is NOT equal to the specified one. Beware
     * about floating numbers comparison, check the link below.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isNotEqual(number2).orElseThrow();
     * </pre>
     * 
     * @see <a href=
     *      "http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/"></a>
     * 
     * @param number
     *            The number to compare
     * @return The operator
     */
    default PredicateStepNumber<N> isNotEqual(final N number) {
        return this.isNotEqual(number, null);
    }

    /**
     * Asserts that the given number is NOT equal to the specified one. Beware
     * about floating numbers comparison, check the link below.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isNotEqual(number2, "%1$d* is not equal to %2$d*").orElseThrow();
     * </pre>
     * 
     * @see <a href=
     *      "http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/"></a>
     * 
     * @param number
     *            The number to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isNotEqual(final N number, final CharSequence message, final Object... arguments) {
        return this.isNotEqual(number, null, message, arguments);
    }

    /**
     * Asserts that the given number is NOT equal to the specified one. Beware
     * about floating numbers comparison, check the link below.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isNotEqual(number2, Locale.US, "%1$d* is not equal to %2$d*").orElseThrow();
     * </pre>
     * 
     * @see <a href=
     *      "http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/"></a>
     * 
     * @param number
     *            The number to compare
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isNotEqual(final N number, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isNotEqual(this.getStep(), number, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given number is equal to zero. Otherwise returns false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isZero().orElseThrow();
     * </pre>
     * 
     * @return The operator
     */
    default PredicateStepNumber<N> isZero() {
        return this.isZero(null);
    }

    /**
     * Asserts that the given number is equal to zero. Otherwise returns false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isZero("not zero").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isZero(final CharSequence message, final Object... arguments) {
        return this.isZero(null, message, arguments);
    }

    /**
     * Asserts that the given number is equal to zero. Otherwise returns false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isZero(Locale.US, "not zero").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isZero(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isZero(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given number is positive. Positive means a number &gt;
     * 0. Also 0, negative number and {@code null} return false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isPositive().orElseThrow();
     * </pre>
     * 
     * @return The operator
     */
    default PredicateStepNumber<N> isPositive() {
        return this.isPositive(null);
    }

    /**
     * Asserts that the given number is positive. Positive means a number &gt;
     * 0. Also 0, negative number and {@code null} return false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isPositive("not positive").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isPositive(final CharSequence message, final Object... arguments) {
        return this.isPositive(null, message, arguments);
    }

    /**
     * Asserts that the given number is positive. Positive means a number &gt;
     * 0. Also 0, negative number and {@code null} return false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isPositive(Locale.US, "not positive").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isPositive(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isPositive(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given number is negative. Negative means a number &lt;
     * 0. Also 0, positive number and {@code null} return false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isNegative().orElseThrow();
     * </pre>
     * 
     * @return The operator
     */
    default PredicateStepNumber<N> isNegative() {
        return this.isNegative(null);
    }

    /**
     * Asserts that the given number is negative. Negative means a number &lt;
     * 0. Also 0, positive number and {@code null} return false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isNegative("not negative").orElseThrow();
     * </pre>
     * 
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isNegative(final CharSequence message, final Object... arguments) {
        return this.isNegative(null, message, arguments);
    }

    /**
     * Asserts that the given number is negative. Negative means a number &lt;
     * 0. Also 0, positive number and {@code null} return false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isNegative(Locale.US, "not negative").orElseThrow();
     * </pre>
     * 
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isNegative(final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isNegative(this.getStep(), MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given number is greater than the number to compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code false}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isGT(number2).orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @return The operator
     */
    default PredicateStepNumber<N> isGT(final N number) {
        return this.isGT(number, null);
    }

    /**
     * Asserts that the given number is greater than the number to compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code false}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isGT(number2, "not greater").orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isGT(final N number, final CharSequence message, final Object... arguments) {
        return this.isGT(number, null, message, arguments);
    }

    /**
     * Asserts that the given number is greater than the number to compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code false}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isGT(number2, Locale.US, "not greater").orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isGT(final N number, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isGT(this.getStep(), number, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given number is equal or greater than the number to
     * compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code true}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isGTE(number2).orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @return The operator
     */
    default PredicateStepNumber<N> isGTE(final N number) {
        return this.isGTE(number, null);
    }

    /**
     * Asserts that the given number is equal or greater than the number to
     * compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code true}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isGTE(number2, "not greater or equal").orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isGTE(final N number, final CharSequence message, final Object... arguments) {
        return this.isGTE(number, null, message, arguments);
    }

    /**
     * Asserts that the given number is equal or greater than the number to
     * compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code true}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isGTE(number2, Locale.US, "not greater or equal").orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isGTE(final N number, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isGTE(this.getStep(), number, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given number is lower than the number to compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code false}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isLT(number2).orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @return The operator
     */
    default PredicateStepNumber<N> isLT(final N number) {
        return this.isLT(number, null);
    }

    /**
     * Asserts that the given number is lower than the number to compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code false}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isLT(number2, "not lower").orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isLT(final N number, final CharSequence message, final Object... arguments) {
        return this.isLT(number, null, message, arguments);
    }

    /**
     * Asserts that the given number is lower than the number to compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code false}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isLT(number2, Locale.US, "not lower").orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isLT(final N number, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isLT(this.getStep(), number, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Asserts that the given number is equal or lower than the number to
     * compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code true}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isLTE(number2).orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @return The operator
     */
    default PredicateStepNumber<N> isLTE(final N number) {
        return this.isLTE(number, null);
    }

    /**
     * Asserts that the given number is equal or lower than the number to
     * compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code true}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isLTE(number2, "not lower or equal").orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isLTE(final N number, final CharSequence message, final Object... arguments) {
        return this.isLTE(number, null, message, arguments);
    }

    /**
     * Asserts that the given number is equal or lower than the number to
     * compare.
     * 
     * <p>
     * If one of the number is {@code null}, it's considered as the lowest. If
     * both are {@code null}, they are considered equal, so the result is
     * {@code true}.
     * </p>
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * <pre>
     * Assertor.that(number1).isLTE(number2, Locale.US, "not lower or equal").orElseThrow();
     * </pre>
     * 
     * @param number
     *            The number to compare
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isLTE(final N number, final Locale locale, final CharSequence message, final Object... arguments) {
        return () -> AssertorNumber.isLTE(this.getStep(), number, MessageAssertor.of(locale, message, arguments));
    }

    /**
     * Prepare the next step to validate if {@link Number} is between the left
     * hand number (inclusive) and the right hand number (exclusive).
     * 
     * <p>
     * precondition: none number can be {@code null} and {@code lhn} must be
     * lower than {@code rhn}
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isBetween(bound1, bound2).orElseThrow();
     * </pre>
     * 
     * @param lhn
     *            the left hand number (lower bound)
     * @param rhn
     *            the right hand number (upper bound)
     * @return The operator
     */
    default PredicateStepNumber<N> isBetween(final N lhn, final N rhn) {
        return this.isBetween(lhn, rhn, null);
    }

    /**
     * Prepare the next step to validate if {@link Number} is between the left
     * hand number (inclusive) and the right hand number (exclusive).
     * 
     * <p>
     * precondition: none number can be {@code null} and {@code lhn} must be
     * lower than {@code rhn}
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isBetween(bound1, bound2, "%1$,.3f* is not between %2$,.3f* and %3$,.3f*").orElseThrow();
     * </pre>
     * 
     * @param lhn
     *            the left hand number (lower bound)
     * @param rhn
     *            the right hand number (upper bound)
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isBetween(final N lhn, final N rhn, final CharSequence message, final Object... arguments) {
        return this.isBetween(lhn, rhn, null, message, arguments);
    }

    /**
     * Prepare the next step to validate if {@link Number} is between the left
     * hand number (inclusive) and the right hand number (exclusive).
     * 
     * <p>
     * precondition: none number can be {@code null} and {@code lhn} must be
     * lower than {@code rhn}
     * </p>
     * 
     * <pre>
     * Assertor.that(number).isBetween(bound1, bound2, Locale.US, "%1$,.3f* is not between %2$,.3f* and %3$,.3f*").orElseThrow();
     * </pre>
     * 
     * @param lhn
     *            the left hand number (lower bound)
     * @param rhn
     *            the right hand number (upper bound)
     * @param locale
     *            The locale of the message (only applied for this message,
     *            otherwise use {@link Assertor#setLocale})
     * @param message
     *            The message on mismatch
     * @param arguments
     *            The arguments of the message, use {@link String#format}
     * @return The operator
     */
    default PredicateStepNumber<N> isBetween(final N lhn, final N rhn, final Locale locale, final CharSequence message,
            final Object... arguments) {
        return () -> AssertorNumber.isBetween(this.getStep(), lhn, rhn, MessageAssertor.of(locale, message, arguments));
    }
}
