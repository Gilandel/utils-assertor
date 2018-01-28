/*-
 * #%L
 * utils-assertor
 * %%
 * Copyright (C) 2016 - 2018 Gilles Landel
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

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.helper.HelperAssertor;
import fr.landel.utils.commons.ArrayUtils;

/**
 * Utility class to prepare the check of arrays
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorArray extends ConstantsAssertor {

    /**
     * Prepare the next step to validate that the array length is equal to
     * {@code length}.
     * 
     * <p>
     * precondition: {@code length} has to be a positive number or equal to zero
     * and {@code array} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param length
     *            the length to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> hasLength(final StepAssertor<T[]> step, final int length, final MessageAssertor message) {

        final BiPredicate<T[], Boolean> checker = (array, not) -> array.length == length;

        return checkLength(step, length, checker, MSG.ARRAY.LENGTH, message);
    }

    /**
     * Prepare the next step to validate that the array length is greater than
     * {@code length}.
     * 
     * <p>
     * precondition: {@code length} has to be a positive number or equal to zero
     * and {@code array} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param length
     *            the length to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> hasLengthGT(final StepAssertor<T[]> step, final int length, final MessageAssertor message) {

        final BiPredicate<T[], Boolean> checker = (array, not) -> array.length > length;

        return checkLength(step, length, checker, MSG.ARRAY.LENGTH_GT, message);
    }

    /**
     * Prepare the next step to validate that the array length is greater than
     * or equal to {@code length}.
     * 
     * <p>
     * precondition: {@code length} has to be a positive number or equal to zero
     * and {@code array} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param length
     *            the length to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> hasLengthGTE(final StepAssertor<T[]> step, final int length, final MessageAssertor message) {

        final BiPredicate<T[], Boolean> checker = (array, not) -> array.length >= length;

        return checkLength(step, length, checker, MSG.ARRAY.LENGTH_GTE, message);
    }

    /**
     * Prepare the next step to validate that the array length is lower than
     * {@code length}.
     * 
     * <p>
     * precondition: {@code length} has to be a positive number or equal to zero
     * and {@code array} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param length
     *            the length to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> hasLengthLT(final StepAssertor<T[]> step, final int length, final MessageAssertor message) {

        final BiPredicate<T[], Boolean> checker = (array, not) -> array.length < length;

        return checkLength(step, length, checker, MSG.ARRAY.LENGTH_LT, message);
    }

    /**
     * Prepare the next step to validate that the array length is lower than or
     * equal to {@code length}.
     * 
     * <p>
     * precondition: {@code length} has to be a positive number or equal to zero
     * and {@code array} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param length
     *            the length to validate
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> hasLengthLTE(final StepAssertor<T[]> step, final int length, final MessageAssertor message) {

        final BiPredicate<T[], Boolean> checker = (array, not) -> array.length <= length;

        return checkLength(step, length, checker, MSG.ARRAY.LENGTH_LTE, message);
    }

    private static <T> StepAssertor<T[]> checkLength(final StepAssertor<T[]> step, final int length,
            final BiPredicate<T[], Boolean> checker, final String messageKey, final MessageAssertor message) {

        final Predicate<T[]> preChecker = (array) -> length >= 0 && array != null;

        return new StepAssertor<>(step, preChecker, checker, false, message, messageKey, false,
                new ParameterAssertor<>(length, EnumType.NUMBER_INTEGER));
    }

    /**
     * Prepare the next step to validate if the array is {@code null} or empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> isEmpty(final StepAssertor<T[]> step, final MessageAssertor message) {

        final BiPredicate<T[], Boolean> checker = (array, not) -> ArrayUtils.isEmpty(array);

        return new StepAssertor<>(step, checker, false, message, MSG.ARRAY.EMPTY, false);
    }

    /**
     * Prepare the next step to validate if the array is NOT {@code null} and
     * NOT empty.
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> isNotEmpty(final StepAssertor<T[]> step, final MessageAssertor message) {

        final BiPredicate<T[], Boolean> checker = (array, not) -> ArrayUtils.isNotEmpty(array);

        return new StepAssertor<>(step, checker, false, message, MSG.ARRAY.EMPTY, true);
    }

    /**
     * Prepare the next step to validate if all array elements match the
     * predicate.
     * 
     * <p>
     * precondition: {@code array} cannot be {@code null} or empty and
     * {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param predicate
     *            the predicate used to check each element
     * @param message
     *            the message on predicate failed
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> allMatch(final StepAssertor<T[]> step, final Predicate<T> predicate,
            final MessageAssertor message) {

        return match(step, predicate, true, MSG.ARRAY.MATCH_ALL, message);
    }

    /**
     * Prepare the next step to validate if any array element matches the
     * predicate.
     * 
     * <p>
     * precondition: {@code array} cannot be {@code null} or empty and
     * {@code predicate} cannot be {@code null}
     * </p>
     * 
     * @param step
     *            the current step
     * @param predicate
     *            the predicate used to check each element
     * @param message
     *            the message on predicate failed
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> anyMatch(final StepAssertor<T[]> step, final Predicate<T> predicate,
            final MessageAssertor message) {

        return match(step, predicate, false, MSG.ARRAY.MATCH_ANY, message);
    }

    private static <T> StepAssertor<T[]> match(final StepAssertor<T[]> step, final Predicate<T> predicate, final boolean all,
            final String messageKey, final MessageAssertor message) {

        final Predicate<T[]> preChecker = (array) -> ArrayUtils.isNotEmpty(array) && predicate != null;

        final BiPredicate<T[], Boolean> checker = (array, not) -> AssertorArray.has(array, predicate, not, all, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, messageKey, false,
                new ParameterAssertor<>(predicate, EnumType.UNKNOWN));
    }

    /**
     * Prepare the next step to validate if the array contains the element.
     * 
     * <p>
     * precondition: {@code array} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param element
     *            the element to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> contains(final StepAssertor<T[]> step, final T element, final MessageAssertor message) {

        final Predicate<T[]> preChecker = (array) -> ArrayUtils.isNotEmpty(array);

        final BiPredicate<T[], Boolean> checker = (array, not) -> AssertorArray.has(array, element, false, false, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.ARRAY.CONTAINS_OBJECT, false,
                new ParameterAssertor<>(element, EnumType.UNKNOWN));
    }

    private static <T> StepAssertor<T[]> contains(final StepAssertor<T[]> step, final T[] array, final boolean all, final CharSequence key,
            final MessageAssertor message) {

        final Predicate<T[]> preChecker = (object) -> ArrayUtils.isNotEmpty(object) && ArrayUtils.isNotEmpty(array);

        final BiPredicate<T[], Boolean> checker = (object, not) -> AssertorArray.has(object, array, all, not, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, key, false, new ParameterAssertor<>(array, EnumType.ARRAY));
    }

    /**
     * Prepare the next step to validate if the array contains all elements of
     * the specified array
     * 
     * <p>
     * precondition: neither of {@code array} can be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param array
     *            the array to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> containsAll(final StepAssertor<T[]> step, final T[] array, final MessageAssertor message) {

        return AssertorArray.contains(step, array, true, MSG.ARRAY.CONTAINS_ALL, message);
    }

    /**
     * Prepare the next step to validate if the array contains any elements of
     * the specified array
     * 
     * <p>
     * precondition: neither of {@code array} can be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param array
     *            the array to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> containsAny(final StepAssertor<T[]> step, final T[] array, final MessageAssertor message) {

        return AssertorArray.contains(step, array, false, MSG.ARRAY.CONTAINS_ANY, message);
    }

    /**
     * Prepare the next step to validate if the array contains elements in the
     * specified order of the specified array
     * 
     * <p>
     * precondition: neither of {@code array} can be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param array
     *            the array to find
     * @param message
     *            the message if invalid
     * @param <T>
     *            the array elements type
     * @return the next step
     */
    public static <T> StepAssertor<T[]> containsInOrder(final StepAssertor<T[]> step, final T[] array, final MessageAssertor message) {

        final Predicate<T[]> preChecker = (object) -> ArrayUtils.isNotEmpty(array) && ArrayUtils.isNotEmpty(object);

        final BiPredicate<T[], Boolean> checker = (object, not) -> AssertorArray.hasInOrder(object, array, not, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, MSG.ARRAY.CONTAINS_IN_ORDER, false,
                new ParameterAssertor<>(array, EnumType.ARRAY));
    }

    private static <T> boolean has(final T[] array, final T object, final boolean not, final boolean all,
            final EnumAnalysisMode analysisMode) {
        final Predicate<T> predicate;
        if (object != null) {
            predicate = object::equals;
        } else {
            predicate = Objects::isNull;
        }

        return has(array, predicate, not, all, analysisMode);
    }

    private static <T> boolean has(final T[] array, final Predicate<T> predicate, final boolean not, final boolean all,
            final EnumAnalysisMode analysisMode) {

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            if (all && !not) {
                for (final T objectArray : array) {
                    if (!predicate.test(objectArray)) {
                        return false;
                    }
                }
                return true;
            } else if (!all) { // any and not any
                for (final T objectArray : array) {
                    if (predicate.test(objectArray)) {
                        return !not;
                    }
                }
                return not;
            } else { // not all
                long found = 0;
                for (final T objectArray : array) {
                    if (predicate.test(objectArray)) {
                        ++found;
                    }
                }
                return HelperAssertor.isValid(all, not, found, array.length);
            }
        } else {
            Stream<T> stream = Stream.of(array);
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = stream.parallel();
            }

            return HelperAssertor.isValid(stream, predicate, all, not, () -> array.length);
        }
    }

    private static <T> boolean has(final T[] array1, final T[] array2, final boolean all, final boolean not,
            final EnumAnalysisMode analysisMode) {

        if (all && !not && array1.length < array2.length) {
            return false;
        }

        long found = 0;

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (T objectArray : array2) {
                if (AssertorArray.has(array1, objectArray, false, false, analysisMode)) {
                    ++found;
                }
            }
        } else {
            Stream<T> stream = Stream.of(array2);
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = stream.parallel();
            }
            found = stream.filter(o -> AssertorArray.has(array1, o, false, false, analysisMode)).count();
        }

        return HelperAssertor.isValid(all, not, found, array2.length);
    }

    private static <T> boolean hasInOrder(final T[] array1, final T[] array2, final boolean not, final EnumAnalysisMode analysisMode) {

        int found = 0;

        final int size1 = array1.length;
        final int size2 = array2.length;

        if (size1 < size2) {
            return not;
        } else if (size1 == size2) {
            return not ^ Arrays.equals(array1, array2);
        }

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (int i = 0; i < size1 && found < size2; ++i) {
                if (Objects.equals(array1[i], array2[found])) {
                    ++found;
                } else if (found > 0) {
                    found = 0;
                }
            }
        } else {
            final AtomicInteger count = new AtomicInteger(0);

            Stream<T> stream = Stream.of(array1);
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = stream.parallel();
            }

            stream.forEachOrdered(o -> {
                int inc = count.get();
                if (inc < size2) {
                    if (Objects.equals(o, array2[inc])) {
                        count.incrementAndGet();
                    } else if (inc > 0) {
                        count.set(0);
                    }
                }
            });

            found = count.get();
        }

        return not ^ (found == size2);
    }
}
