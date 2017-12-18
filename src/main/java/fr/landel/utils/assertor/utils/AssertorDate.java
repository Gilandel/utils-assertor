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

import java.util.Calendar;
import java.util.Date;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.commons.Comparators;
import fr.landel.utils.commons.DateUtils;

/**
 * Utility class to prepare the check of {@link Date} or {@link Calendar}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorDate extends ConstantsAssertor {

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is equal to the specified date (same type)
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isEqual(final StepAssertor<T> step, final T date,
            final MessageAssertor message) {

        return AssertorDate.isAround(step, date, -1, 0, message);
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is NOT equal to the specified date (same type)
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isNotEqual(final StepAssertor<T> step, final T date,
            final MessageAssertor message) {

        return AssertorDate.isNotAround(step, date, -1, 0, message);
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is NOT around to the specified date (same type)
     * 
     * <p>
     * precondition: {@code calendarField} must be a valid field and
     * {@code calendarAmount} cannot be equal to zero
     * </p>
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
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param message
     *            the message if invalid
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isNotAround(final StepAssertor<T> step, final T date, final int calendarField,
            final int calendarAmount, final MessageAssertor message) {

        return AssertorDate.isAround(step, date, calendarField, calendarAmount, true, message);
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is around to the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null, {@code calendarField} must be a
     * valid field and {@code calendarAmount} cannot be equal to zero
     * </p>
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
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param message
     *            the message if invalid
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isAround(final StepAssertor<T> step, final T date, final int calendarField,
            final int calendarAmount, final MessageAssertor message) {

        return AssertorDate.isAround(step, date, calendarField, calendarAmount, false, message);
    }

    private static <T extends Comparable<T>> StepAssertor<T> isAround(final StepAssertor<T> step, final T date, final int calendarField,
            final int calendarAmount, final boolean reverse, final MessageAssertor message) {

        final Predicate<T> preChecker = preChecker(date, calendarField, calendarAmount);

        final BiPredicate<T, Boolean> checker = (date1, not) -> {
            boolean around = false;
            final int compare = Comparators.compare(date1, date);
            if (compare == 0) {
                around = true;
            } else if (calendarField != -1) {
                around = AssertorDate.isAround(date1, date, calendarField, calendarAmount, compare);
            }
            return reverse ^ around;
        };

        if (calendarField == -1) {
            return new StepAssertor<>(step, preChecker, checker, false, message, MSG.DATE.EQUALS, reverse, new ParameterAssertor<>(date));
        } else {
            return new StepAssertor<>(step, preChecker, checker, false, message, MSG.DATE.AROUND, reverse, new ParameterAssertor<>(date),
                    new ParameterAssertor<>(calendarField, EnumType.CALENDAR_FIELD),
                    new ParameterAssertor<>(calendarAmount, EnumType.NUMBER_INTEGER));
        }
    }

    private static <T extends Comparable<T>> boolean isAround(final T date1, final T date, final int calendarField,
            final int calendarAmount, final int compare) {

        final Class<?> clazz = date1.getClass();

        Calendar calendar1;
        Calendar calendar2;

        if (Date.class.equals(clazz)) {
            calendar1 = DateUtils.getCalendar((Date) date1);
            calendar2 = DateUtils.getCalendar((Date) date);
        } else { // Calendar
            calendar1 = (Calendar) ((Calendar) date1).clone();
            calendar2 = (Calendar) ((Calendar) date).clone();
        }

        // prechecker calendarAmount != 0
        if (compare < 0) {
            calendar1.add(calendarField, calendarAmount);
            return !calendar1.before(calendar2);
        } else if (compare > 0) {
            calendar2.add(calendarField, calendarAmount);
            return !calendar2.before(calendar1);
        }
        return true; // normally, not used (equals)
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is between the specified dates (same type)
     * 
     * @param step
     *            the current step
     * @param dateStart
     *            the minimum date
     * @param dateEnd
     *            the maximum date
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isBetween(final StepAssertor<T> step, final T dateStart, final T dateEnd,
            final MessageAssertor message) {

        final Predicate<T> preChecker = (date) -> date != null && dateStart != null && dateEnd != null;

        final BiPredicate<T, Boolean> checker = (date, not) -> Comparators.compare(date, dateStart) >= 0
                && Comparators.compare(date, dateEnd) <= 0;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.DATE.BETWEEN, false, new ParameterAssertor<>(dateStart),
                new ParameterAssertor<>(dateEnd));
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is after the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isAfter(final StepAssertor<T> step, final T date,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (date1, not) -> Comparators.compare(date1, date) > 0;

        return AssertorDate.is(step, date, MSG.DATE.AFTER, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is after the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null, {@code calendarField} must be a
     * valid field and {@code calendarAmount} cannot be equal to zero
     * </p>
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
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isAfter(final StepAssertor<T> step, final T date, final int calendarField,
            final int calendarAmount, final MessageAssertor message) {

        final Predicate<T> preChecker = preChecker(date, calendarField, calendarAmount);

        final BiPredicate<T, Boolean> checker = AssertorDate.checker(date, calendarField, calendarAmount, c -> c > 0);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.DATE.AFTER, false, new ParameterAssertor<>(date));
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is after or equals to the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isAfterOrEqual(final StepAssertor<T> step, final T date,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (date1, not) -> Comparators.compare(date1, date) >= 0;

        return AssertorDate.is(step, date, MSG.DATE.AFTER_OR_EQUALS, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is after or equals to the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null, {@code calendarField} must be a
     * valid field and {@code calendarAmount} cannot be equal to zero
     * </p>
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
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isAfterOrEqual(final StepAssertor<T> step, final T date,
            final int calendarField, final int calendarAmount, final MessageAssertor message) {

        final Predicate<T> preChecker = preChecker(date, calendarField, calendarAmount);

        final BiPredicate<T, Boolean> checker = AssertorDate.checker(date, calendarField, calendarAmount, c -> c >= 0);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.DATE.AFTER_OR_EQUALS, false,
                new ParameterAssertor<>(date));
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is before the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isBefore(final StepAssertor<T> step, final T date,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (date1, not) -> Comparators.compare(date1, date) < 0;

        return AssertorDate.is(step, date, MSG.DATE.BEFORE, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is before the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null, {@code calendarField} must be a
     * valid field and {@code calendarAmount} cannot be equal to zero
     * </p>
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
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isBefore(final StepAssertor<T> step, final T date, final int calendarField,
            final int calendarAmount, final MessageAssertor message) {

        final Predicate<T> preChecker = preChecker(date, calendarField, calendarAmount);

        final BiPredicate<T, Boolean> checker = AssertorDate.checker(date, calendarField, calendarAmount, c -> c < 0);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.DATE.BEFORE, false, new ParameterAssertor<>(date));
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is before or equals to the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null
     * </p>
     * 
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isBeforeOrEqual(final StepAssertor<T> step, final T date,
            final MessageAssertor message) {

        final BiPredicate<T, Boolean> checker = (date1, not) -> Comparators.compare(date1, date) <= 0;

        return AssertorDate.is(step, date, MSG.DATE.BEFORE_OR_EQUALS, checker, message);
    }

    /**
     * Prepare the next step to validate if the {@link Date} or {@link Calendar}
     * is before or equals to the specified date (same type)
     * 
     * <p>
     * precondition: neither dates can be null, {@code calendarField} must be a
     * valid field and {@code calendarAmount} cannot be equal to zero
     * </p>
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
     * @param step
     *            the current step
     * @param date
     *            the date to compare
     * @param calendarField
     *            the calendar field
     * @param calendarAmount
     *            the calendar amount
     * @param message
     *            the message if invalid
     * @param <T>
     *            the date type
     * @return the next step
     */
    public static <T extends Comparable<T>> StepAssertor<T> isBeforeOrEqual(final StepAssertor<T> step, final T date,
            final int calendarField, final int calendarAmount, final MessageAssertor message) {

        final Predicate<T> preChecker = preChecker(date, calendarField, calendarAmount);

        final BiPredicate<T, Boolean> checker = AssertorDate.checker(date, calendarField, calendarAmount, c -> c <= 0);

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.DATE.BEFORE_OR_EQUALS, false,
                new ParameterAssertor<>(date));
    }

    private static <T extends Comparable<T>> StepAssertor<T> is(final StepAssertor<T> step, final T date, final CharSequence key,
            final BiPredicate<T, Boolean> checker, final MessageAssertor message) {

        final Predicate<T> preChecker = (date1) -> date1 != null && date != null;

        return new StepAssertor<>(step, preChecker, checker, false, message, key, false, new ParameterAssertor<>(date));
    }

    private static <T extends Comparable<T>> Predicate<T> preChecker(final T date, final int calendarField, final int calendarAmount) {
        return (date1) -> {
            final boolean prerequisites;
            final boolean calendarFieldOk = calendarField == -1 || (CALENDAR_FIELDS.containsKey(calendarField) && calendarAmount != 0);

            if (calendarField != -1) {
                prerequisites = date1 != null && date != null;
            } else {
                prerequisites = true;
            }

            return prerequisites && calendarFieldOk;
        };
    }

    private static <T extends Comparable<T>> BiPredicate<T, Boolean> checker(final T date, final int calendarField,
            final int calendarAmount, final Predicate<Integer> comparator) {
        return (date1, not) -> {
            boolean around = false;
            final int compare = Comparators.compare(date1, date);
            if (comparator.test(compare)) {
                if (calendarField == -1) {
                    around = true;
                } else {
                    around = AssertorDate.isAround(date1, date, calendarField, calendarAmount, compare);
                }
            }
            return around;
        };
    }
}
