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
package fr.landel.utils.assertor.utils;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.commons.Comparators;
import fr.landel.utils.commons.NumberUtils;
import fr.landel.utils.commons.ObjectUtils;

/**
 * Utility class to prepare the check of {@link Number}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorNumber extends ConstantsAssertor {

    /**
     * Prepare the next step to validate if {@link Number} is equal to
     * {@code number}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param number
     *            the number to compare
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isEqual(final StepAssertor<N> step, final N number,
            final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> Comparators.compare(object, number) == 0;

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.EQUALS, false, new ParameterAssertor<>(number));
    }

    /**
     * Prepare the next step to validate if {@link Number} is NOT equal to
     * {@code number}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param number
     *            the number to compare
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isNotEqual(final StepAssertor<N> step, final N number,
            final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> Comparators.compare(object, number) != 0;

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.EQUALS, true, new ParameterAssertor<>(number));
    }

    /**
     * Prepare the next step to validate if {@link Number} is zero
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isZero(final StepAssertor<N> step, final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> NumberUtils.isZero(object);

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.POSITIVE, false);
    }

    /**
     * Prepare the next step to validate if {@link Number} is positive (number
     * &gt; 0). 0 and {@code null} return false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isPositive(final StepAssertor<N> step, final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> NumberUtils.signum(object) > 0;

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.POSITIVE, false);
    }

    /**
     * Prepare the next step to validate if {@link Number} is negative (number
     * &lt; 0). 0 and {@code null} return false.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isNegative(final StepAssertor<N> step, final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> NumberUtils.signum(object) < 0;

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.NEGATIVE, false);
    }

    /**
     * Prepare the next step to validate if {@link Number} is greater than
     * {@code number}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param number
     *            the number to compare
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isGT(final StepAssertor<N> step, final N number,
            final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> Comparators.compare(object, number) > 0;

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.GT, false, new ParameterAssertor<>(number));
    }

    /**
     * Prepare the next step to validate if {@link Number} is greater than or
     * equals to {@code number}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param number
     *            the number to compare
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isGTE(final StepAssertor<N> step, final N number,
            final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> Comparators.compare(object, number) >= 0;

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.GTE, false, new ParameterAssertor<>(number));
    }

    /**
     * Prepare the next step to validate if {@link Number} is lower than
     * {@code number}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param number
     *            the number to compare
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isLT(final StepAssertor<N> step, final N number,
            final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> Comparators.compare(object, number) < 0;

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.LT, false, new ParameterAssertor<>(number));
    }

    /**
     * Prepare the next step to validate if {@link Number} is lower than or
     * equals to {@code number}
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the previous step
     * @param number
     *            the number to compare
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isLTE(final StepAssertor<N> step, final N number,
            final MessageAssertor message) {

        final BiPredicate<N, Boolean> checker = (object, not) -> Comparators.compare(object, number) <= 0;

        return new StepAssertor<>(step, checker, false, message, MSG.NUMBER.LTE, false, new ParameterAssertor<>(number));
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
     * @param step
     *            the previous step
     * @param lhn
     *            the left hand number (lower bound)
     * @param rhn
     *            the right hand number (upper bound)
     * @param message
     *            the message if invalid
     * @param <N>
     *            The number type
     * @return the next step
     */
    public static <N extends Number & Comparable<N>> StepAssertor<N> isBetween(final StepAssertor<N> step, final N lhn, final N rhn,
            final MessageAssertor message) {

        final Predicate<N> preChecker = object -> ObjectUtils.allNotNull(object, lhn, rhn) && Comparators.compare(lhn, rhn) < 0;

        final BiPredicate<N, Boolean> checker = (object, not) -> Comparators.compare(object, lhn) >= 0
                && Comparators.compare(object, rhn) < 0;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.NUMBER.BETWEEN, false, new ParameterAssertor<>(lhn),
                new ParameterAssertor<>(rhn));
    }
}
