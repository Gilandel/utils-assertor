/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2017 Gilles Landel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package fr.landel.utils.assertor.utils;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.Comparators;

/**
 * Utility class to prepare the check of comparable {@link Temporal}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorTemporal extends ConstantsAssertor {

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * equal to the specified temporal (same type)
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isEqual(final StepAssertor<T> step, final T temporal,
            final MessageAssertor message) {

        return isAround(step, temporal, null, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * NOT equal to the specified temporal (same type)
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isNotEqual(final StepAssertor<T> step, final T temporal,
            final MessageAssertor message) {

        return isNotAround(step, temporal, null, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * NOT around to the specified temporal (same type)
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null} if
     * {@code temporalAmount} is {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message if invalid
     * @param temporalAmount
     *            the temporal amount
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isNotAround(final StepAssertor<T> step, final T temporal,
            final TemporalAmount temporalAmount, final MessageAssertor message) {

        return isAround(step, temporal, temporalAmount, true, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * around to the specified temporal (same type)
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null} if
     * {@code temporalAmount} is {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message if invalid
     * @param temporalAmount
     *            the temporal amount
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isAround(final StepAssertor<T> step, final T temporal,
            final TemporalAmount temporalAmount, final MessageAssertor message) {

        return AssertorTemporal.isAround(step, temporal, temporalAmount, false, message);
    }

    private static <T extends Temporal & Comparable<T>> StepAssertor<T> isAround(final StepAssertor<T> step, final T temporal,
            final TemporalAmount temporalAmount, final boolean reverse, final MessageAssertor message) {

        final Predicate<T> preChecker = (temporal1) -> {
            boolean prerequisites;
            if (temporalAmount != null) {
                prerequisites = temporal1 != null && temporal != null;
            } else {
                prerequisites = true;
            }
            return prerequisites;
        };

        final BiPredicate<T, Boolean> checker = (temporal1, not) -> {
            boolean around = false;
            final int compare = Comparators.compare(temporal1, temporal);
            if (compare == 0) {
                around = true;
            } else if (temporalAmount != null) {
                around = AssertorTemporal.isAround(temporal1, temporal, temporalAmount, compare);
            }
            return reverse ^ around;
        };

        if (temporalAmount == null) {
            return new StepAssertor<>(step, preChecker, checker, false, message, MSG.TEMPORAL.EQUALS, reverse,
                    new ParameterAssertor<>(temporal));
        } else {
            return new StepAssertor<>(step, preChecker, checker, false, message, MSG.TEMPORAL.AROUND, reverse,
                    new ParameterAssertor<>(temporal), new ParameterAssertor<>(temporalAmount, EnumType.UNKNOWN));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Temporal & Comparable<T>> boolean isAround(final T temporal1, final T temporal,
            final TemporalAmount temporalAmount, final int compare) {
        Temporal t1 = (Temporal) temporal1;
        Temporal t2 = (Temporal) temporal;
        if (compare < 0) {
            t1 = t1.plus(temporalAmount);
            return ((T) t1).compareTo((T) t2) >= 0;
        } else if (compare > 0) {
            t2 = t2.plus(temporalAmount);
            return ((T) t2).compareTo((T) t1) >= 0;
        }
        return true; // normally, not used (equals)
    }

    /**
     * Prepare the next step to validate if the {@link Temporal} is between the
     * specified Temporals (same type)
     * 
     * @param step
     *            the current step
     * @param temporalStart
     *            the minimum date
     * @param temporalEnd
     *            the maximum date
     * @param message
     *            the message if invalid
     * @param <T>
     *            the Temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isBetween(final StepAssertor<T> step, final T temporalStart,
            final T temporalEnd, final MessageAssertor message) {

        final Predicate<T> preChecker = (Temporal) -> Temporal != null && temporalStart != null && temporalEnd != null;

        final BiPredicate<T, Boolean> checker = (Temporal, not) -> Comparators.compare(Temporal, temporalStart) >= 0
                && Comparators.compare(Temporal, temporalEnd) <= 0;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.TEMPORAL.BETWEEN, false,
                new ParameterAssertor<>(temporalStart), new ParameterAssertor<>(temporalEnd));
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * after the specified temporal (same type)
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isAfter(final StepAssertor<T> step, final T temporal,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (temporal1, not) -> Comparators.compare(temporal1, temporal) > 0;

        return is(step, temporal, MSG.TEMPORAL.AFTER, checker, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * after the specified temporal (same type)
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isAfter(final StepAssertor<T> step, final T temporal,
            final TemporalAmount temporalAmount, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = AssertorTemporal.checker(temporal, temporalAmount, c -> c > 0);

        return is(step, temporal, MSG.TEMPORAL.AFTER, checker, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * after or equals to the specified temporal (same type)
     * 
     * <p>
     * precondition: neither temporal can be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isAfterOrEqual(final StepAssertor<T> step, final T temporal,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (temporal1, not) -> Comparators.compare(temporal1, temporal) >= 0;

        return is(step, temporal, MSG.TEMPORAL.AFTER_OR_EQUALS, checker, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * after or equals to the specified temporal (same type)
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isAfterOrEqual(final StepAssertor<T> step, final T temporal,
            final TemporalAmount temporalAmount, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = AssertorTemporal.checker(temporal, temporalAmount, c -> c >= 0);

        return is(step, temporal, MSG.TEMPORAL.AFTER_OR_EQUALS, checker, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * before the specified temporal (same type)
     * 
     * <p>
     * precondition: neither temporal can be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isBefore(final StepAssertor<T> step, final T temporal,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (temporal1, not) -> Comparators.compare(temporal1, temporal) < 0;

        return is(step, temporal, MSG.TEMPORAL.BEFORE, checker, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * before the specified temporal (same type)
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isBefore(final StepAssertor<T> step, final T temporal,
            final TemporalAmount temporalAmount, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = AssertorTemporal.checker(temporal, temporalAmount, c -> c < 0);

        return is(step, temporal, MSG.TEMPORAL.BEFORE, checker, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * before or equals to the specified temporal (same type)
     * 
     * <p>
     * precondition: neither temporal can be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isBeforeOrEqual(final StepAssertor<T> step, final T temporal,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (temporal1, not) -> Comparators.compare(temporal1, temporal) <= 0;

        return is(step, temporal, MSG.TEMPORAL.BEFORE_OR_EQUALS, checker, message);
    }

    /**
     * Prepare the next step to validate if the comparable {@link Temporal} is
     * before or equals to the specified temporal (same type)
     * 
     * <p>
     * precondition: neither {@link Temporal} can be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param temporal
     *            the temporal to compare
     * @param temporalAmount
     *            the temporal amount
     * @param message
     *            the message if invalid
     * @param <T>
     *            the temporal type
     * @return the next step
     */
    public static <T extends Temporal & Comparable<T>> StepAssertor<T> isBeforeOrEqual(final StepAssertor<T> step, final T temporal,
            final TemporalAmount temporalAmount, final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = AssertorTemporal.checker(temporal, temporalAmount, c -> c <= 0);

        return is(step, temporal, MSG.TEMPORAL.BEFORE_OR_EQUALS, checker, message);
    }

    private static <T extends Temporal & Comparable<T>> StepAssertor<T> is(final StepAssertor<T> step, final T temporal,
            final CharSequence key, final BiPredicate<T, Boolean> checker, final MessageAssertor message) {

        final Predicate<T> preChecker = (temporal1) -> temporal1 != null && temporal != null;

        return new StepAssertor<>(step, preChecker, checker, false, message, key, false, new ParameterAssertor<>(temporal));
    }

    private static <T extends Temporal & Comparable<T>> BiPredicate<T, Boolean> checker(final T temporal,
            final TemporalAmount temporalAmount, final Predicate<Integer> comparator) {
        return (temporal1, not) -> {
            boolean around = false;
            final int compare = Comparators.compare(temporal1, temporal);
            if (comparator.test(compare)) {
                around = temporalAmount == null || AssertorTemporal.isAround(temporal1, temporal, temporalAmount, compare);
            }
            return around;
        };
    }
}
