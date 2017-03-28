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

import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;

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
    protected static <I extends Iterable<T>, T> StepAssertor<I> hasSize(final StepAssertor<I> step, final int size,
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
    protected static <I extends Iterable<T>, T> StepAssertor<I> isEmpty(final StepAssertor<I> step, final MessageAssertor message) {

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
    protected static <I extends Iterable<T>, T> StepAssertor<I> isNotEmpty(final StepAssertor<I> step, final MessageAssertor message) {

        final BiPredicate<I, Boolean> checker = (iterable, not) -> !IterableUtils.isEmpty(iterable);

        return new StepAssertor<>(step, checker, false, message, MSG.ITERABLE.EMPTY, true);
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
    protected static <I extends Iterable<T>, T> StepAssertor<I> containsAll(final StepAssertor<I> step, final Iterable<T> values,
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
    protected static <I extends Iterable<T>, T> StepAssertor<I> containsAny(final StepAssertor<I> step, final Iterable<T> values,
            final MessageAssertor message) {

        return contains(step, values, MSG.ITERABLE.CONTAINS_ANY, false, message);
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
    protected static <I extends Iterable<T>, T> StepAssertor<I> contains(final StepAssertor<I> step, final T value,
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

    private static <I extends Iterable<T>, T> boolean has(final I iterable, final T object, final EnumAnalysisMode analysisMode) {
        final Predicate<T> predicate;
        if (object != null) {
            predicate = o -> object.equals(o);
        } else {
            predicate = o -> o == null;
        }

        final Spliterator<T> spliterator = iterable.spliterator();
        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (T objectRef : iterable) {
                if (predicate.test(objectRef)) {
                    return true;
                }
            }
        } else {
            return StreamSupport.stream(spliterator, EnumAnalysisMode.PARALLEL.equals(analysisMode)).anyMatch(predicate);
        }

        return false;
    }

    private static <I extends Iterable<T>, T> boolean has(final I iterable1, final Iterable<T> iterable2, final boolean all,
            final boolean not, final EnumAnalysisMode analysisMode) {
        long found = 0;

        final Spliterator<T> spliterator = iterable2.spliterator();
        if (EnumAnalysisMode.STANDARD.equals(analysisMode)) {
            for (T objectRef : iterable2) {
                if (AssertorIterable.has(iterable1, objectRef, analysisMode)) {
                    ++found;
                }
            }
        } else {
            found = StreamSupport.stream(spliterator, EnumAnalysisMode.PARALLEL.equals(analysisMode))
                    .filter(o -> AssertorIterable.has(iterable1, o, analysisMode)).count();
        }

        return HelperAssertor.isValid(all, not, found, IterableUtils.size(iterable2));
    }
}
