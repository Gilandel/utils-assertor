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

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.commons.MessageAssertor;
import fr.landel.utils.assertor.commons.ParameterAssertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.helper.HelperAssertor;

/**
 * Utility class to prepare the check of {@link Iterable}
 *
 * @since Aug 10, 2016
 * @author Gilles
 *
 */
public class AssertorIterable extends ConstantsAssertor {

    /**
     * Prepare the next step to validate the {@link Iterable} size
     * 
     * <p>
     * precondition: {@link Iterable} cannot be {@code null} and size cannot be
     * lower than zero
     * </p>
     * 
     * @param step
     *            the current step
     * @param size
     *            the size to validate
     * @param message
     *            the message if invalid
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the next step
     */
    public static <I extends Iterable<T>, T> StepAssertor<I> hasSize(final StepAssertor<I> step, final int size,
            final MessageAssertor message) {

        final Predicate<I> preChecker = (iterable) -> size >= 0 && iterable != null;

        final BiPredicate<I, Boolean> checker = (iterable, not) -> IterableUtils.size(iterable) == size;

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.ITERABLE.SIZE, false,
                new ParameterAssertor<>(size, EnumType.NUMBER_INTEGER, false));
    }

    /**
     * Prepare the next step to validate if the {@link Iterable} is {@code null}
     * or empty
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the next step
     */
    public static <I extends Iterable<T>, T> StepAssertor<I> isEmpty(final StepAssertor<I> step, final MessageAssertor message) {

        final BiPredicate<I, Boolean> checker = (iterable, not) -> IterableUtils.isEmpty(iterable);

        return new StepAssertor<>(step, checker, false, message, MSG.ITERABLE.EMPTY, false);
    }

    /**
     * Prepare the next step to validate if the {@link Iterable} is NOT
     * {@code null} and NOT empty
     * 
     * <p>
     * precondition: none
     * </p>
     * 
     * @param step
     *            the current step
     * @param message
     *            the message if invalid
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the next step
     */
    public static <I extends Iterable<T>, T> StepAssertor<I> isNotEmpty(final StepAssertor<I> step, final MessageAssertor message) {

        final BiPredicate<I, Boolean> checker = (iterable, not) -> !IterableUtils.isEmpty(iterable);

        return new StepAssertor<>(step, checker, false, message, MSG.ITERABLE.EMPTY, true);
    }

    /**
     * Prepare the next step to validate if the {@link Iterable} contains the
     * specified value
     * 
     * <p>
     * precondition: the {@link Iterable} cannot be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param value
     *            the value to find
     * @param message
     *            the message if invalid
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the next step
     */
    public static <I extends Iterable<T>, T> StepAssertor<I> contains(final StepAssertor<I> step, final T value,
            final MessageAssertor message) {

        final Predicate<I> preChecker = (iterable) -> !IterableUtils.isEmpty(iterable);

        final BiPredicate<I, Boolean> checker = (iterable, not) -> AssertorIterable.has(iterable, value, step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, false, message, MSG.ITERABLE.CONTAINS_OBJECT, false,
                new ParameterAssertor<>(value));
    }

    private static <I extends Iterable<T>, T> StepAssertor<I> contains(final StepAssertor<I> step, final Iterable<T> iterable,
            final CharSequence key, final boolean all, final MessageAssertor message) {

        final Predicate<I> preChecker = (iterable1) -> !IterableUtils.isEmpty(iterable1) && !IterableUtils.isEmpty(iterable);

        final BiPredicate<I, Boolean> checker = (iterable1, not) -> AssertorIterable.has(iterable1, iterable, all, not,
                step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, key, false,
                new ParameterAssertor<>(iterable, EnumType.ITERABLE));
    }

    /**
     * Prepare the next step to validate if the {@link Iterable} contains all
     * values
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param values
     *            the values to find
     * @param message
     *            the message if invalid
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the next step
     */
    public static <I extends Iterable<T>, T> StepAssertor<I> containsAll(final StepAssertor<I> step, final Iterable<T> values,
            final MessageAssertor message) {

        return contains(step, values, MSG.ITERABLE.CONTAINS_ALL, true, message);
    }

    /**
     * Prepare the next step to validate if the {@link Iterable} contains any
     * values
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param values
     *            the values to find
     * @param message
     *            the message if invalid
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the next step
     */
    public static <I extends Iterable<T>, T> StepAssertor<I> containsAny(final StepAssertor<I> step, final Iterable<T> values,
            final MessageAssertor message) {

        return contains(step, values, MSG.ITERABLE.CONTAINS_ANY, false, message);
    }

    /**
     * Prepare the next step to validate if the {@link Iterable} contains values
     * in a specified order
     * 
     * <p>
     * precondition: neither {@link Iterable} can be {@code null} or empty
     * </p>
     * 
     * @param step
     *            the current step
     * @param values
     *            the values to find
     * @param message
     *            the message if invalid
     * @param <I>
     *            the {@link Iterable} type
     * @param <T>
     *            the {@link Iterable} elements type
     * @return the next step
     */
    public static <I extends Iterable<T>, T> StepAssertor<I> containsInOrder(final StepAssertor<I> step, final Iterable<T> values,
            final MessageAssertor message) {

        final Predicate<I> preChecker = (iterable1) -> !IterableUtils.isEmpty(iterable1) && !IterableUtils.isEmpty(values);

        final BiPredicate<I, Boolean> checker = (iterable1, not) -> AssertorIterable.hasInOrder(iterable1, values, not,
                step.getAnalysisMode());

        return new StepAssertor<>(step, preChecker, checker, true, message, MSG.ITERABLE.CONTAINS_IN_ORDER, false,
                new ParameterAssertor<>(values, EnumType.ITERABLE));
    }

    private static <I extends Iterable<T>, T> boolean has(final I iterable, final T object, final EnumAnalysisMode analysisMode) {
        final Predicate<T> predicate;
        if (object != null) {
            predicate = object::equals;
        } else {
            predicate = Objects::isNull;
        }

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (T objectRef : iterable) {
                if (predicate.test(objectRef)) {
                    return true;
                }
            }
        } else {
            return StreamSupport.stream(iterable.spliterator(), EnumAnalysisMode.PARALLEL.equals(analysisMode)).anyMatch(predicate);
        }

        return false;
    }

    private static <I extends Iterable<T>, T> boolean has(final I iterable1, final Iterable<T> iterable2, final boolean all,
            final boolean not, final EnumAnalysisMode analysisMode) {
        long found = 0;

        final int size2 = IterableUtils.size(iterable2);

        if (all && !not && size2 > IterableUtils.size(iterable1)) {
            return false;
        }

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (T objectRef : iterable2) {
                if (AssertorIterable.has(iterable1, objectRef, analysisMode)) {
                    ++found;
                }
            }
        } else {
            found = StreamSupport.stream(iterable2.spliterator(), EnumAnalysisMode.PARALLEL.equals(analysisMode))
                    .filter(o -> AssertorIterable.has(iterable1, o, analysisMode)).count();
        }

        return HelperAssertor.isValid(all, not, found, size2);
    }

    private static <I extends Iterable<T>, T> boolean hasInOrder(final I iterable1, final Iterable<T> iterable2, final boolean not,
            final EnumAnalysisMode analysisMode) {

        long found = 0;
        final int size1 = IterableUtils.size(iterable1);
        final int size2 = IterableUtils.size(iterable2);

        if (size1 < size2) {
            return not;
        } else if (size1 == size2) {
            return not ^ iterable1.equals(iterable2);
        }

        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            final Iterator<T> iterator1 = iterable1.iterator();
            Iterator<T> iterator2 = iterable2.iterator();

            // not empty pre-check, so we call next directly
            T value2 = iterator2.next();
            while (iterator1.hasNext() && found < size2) {
                if (Objects.equals(iterator1.next(), value2)) {
                    ++found;
                    if (iterator2.hasNext()) {
                        value2 = iterator2.next();
                    }
                } else if (found > 0) {
                    found = 0;
                    iterator2 = iterable2.iterator();
                    value2 = iterator2.next();
                }
            }
        } else {
            final AtomicInteger count = new AtomicInteger(0);

            final List<T> list2 = IterableUtils.toList(iterable2);

            StreamSupport.stream(iterable1.spliterator(), EnumAnalysisMode.PARALLEL.equals(analysisMode)).forEachOrdered(o -> {
                int inc = count.get();
                if (inc < size2) {
                    if (Objects.equals(o, list2.get(inc))) {
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
