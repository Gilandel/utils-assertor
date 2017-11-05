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
     * Prepare the next step to validate the array length.
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

        final Predicate<T[]> preChecker = (object) -> length >= 0 && object != null;

        final BiPredicate<T[], Boolean> checker = (object, not) -> object.length == length;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.ARRAY.LENGTH, false,
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

        final BiPredicate<T[], Boolean> checker = (object, not) -> ArrayUtils.isEmpty(object);

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

        final BiPredicate<T[], Boolean> checker = (object, not) -> ArrayUtils.isNotEmpty(object);

        return new StepAssertor<>(step, checker, false, message, MSG.ARRAY.EMPTY, true);
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

        final Predicate<T[]> preChecker = (object) -> ArrayUtils.isNotEmpty(object);

        final BiPredicate<T[], Boolean> checker = (object, not) -> AssertorArray.has(object, element, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.ARRAY.CONTAINS_OBJECT, false,
                new ParameterAssertor<>(element, EnumType.UNKNOWN));
    }

    private static <T> StepAssertor<T[]> contains(final StepAssertor<T[]> step, final T[] array, final boolean all, final CharSequence key,
            final MessageAssertor message) {

        final Predicate<T[]> preChecker = (object) -> ArrayUtils.isNotEmpty(array) && ArrayUtils.isNotEmpty(object);

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

    private static <T> boolean has(final T[] array, final T object, final EnumAnalysisMode analysisMode) {
        final Predicate<T> predicate;
        if (object != null) {
            predicate = o -> object.equals(o);
        } else {
            predicate = o -> o == null;
        }

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (T objectArray : array) {
                if (predicate.test(objectArray)) {
                    return true;
                }
            }
        } else {
            Stream<T> stream = Stream.of(array);
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = stream.parallel();
            }
            return stream.anyMatch(predicate);
        }

        return false;
    }

    private static <T> boolean has(final T[] array1, final T[] array2, final boolean all, final boolean not,
            final EnumAnalysisMode analysisMode) {
        long found = 0;

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (T objectArray : array2) {
                if (AssertorArray.has(array1, objectArray, analysisMode)) {
                    ++found;
                }
            }
        } else {
            Stream<T> stream = Stream.of(array2);
            if (EnumAnalysisMode.PARALLEL.equals(analysisMode)) {
                stream = stream.parallel();
            }
            found = stream.filter(o -> AssertorArray.has(array1, o, analysisMode)).count();
        }

        return HelperAssertor.isValid(all, not, found, array2.length);
    }
}
