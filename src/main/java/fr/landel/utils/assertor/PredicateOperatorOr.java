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
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * This class is an intermediate link in Assertor chain, see
 * {@link PredicateAssertor}.
 *
 * @since Mar 28, 2017
 * @author Gilles
 *
 * @param <S>
 *            the type of predicate step
 * @param <T>
 *            the type of checked object
 */
public interface PredicateOperatorOr<S extends PredicateStep<S, T>, T> {

    /**
     * @return the step result
     */
    StepAssertor<T> getStep();

    /**
     * The only purpose is to avoid the copy of basic methods into children
     * interfaces. This is an indirect way to create specific
     * {@link PredicateStep} by overriding this interface. All children class
     * has to override this method
     * 
     * @param result
     *            the result
     * @return the predicate step
     */
    S get(StepAssertor<T> result);

    /**
     * Applies a predicate step in the current one with the operator OR. The aim
     * of this is to provide the equivalence of parenthesis in condition
     * expressions.
     * 
     * <pre>
     * // '' empty or 'text' not empty and contains 'r'
     * Assertor.that("").isEmpty().or("text").isNotEmpty().and().contains("r").isOK();
     * // -&gt; false (because: true or true and false =&gt; true or true = true =&gt;
     * // true
     * // and false = false)
     * 
     * // '' empty or ('text' not empty and contains 'r')
     * Assertor.that("").isEmpty().or(Assertor.that("text").isNotEmpty().and().contains("r")).isOK();
     * // -&gt; true (because: true or (true and false) =&gt; (true and false) =
     * // false =&gt; true or false = true)
     * 
     * </pre>
     * 
     * @param other
     *            the other predicate step
     * @param <X>
     *            The type of other checked object
     * @param <R>
     *            The {@linkplain PredicateStep} type
     * @return this predicate step with the other injected
     */
    default <X, R extends PredicateStep<R, X>> S or(final PredicateStep<R, X> other) {
        return this.get(HelperAssertor.or(this.getStep(), other.getStep()));
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check
     * another object.
     * 
     * @param other
     *            the other or next checked object to check
     * @param <X>
     *            the object type
     * @param <R>
     *            the type of predicate
     * @return the predicate assertor
     */
    default <X, R extends PredicateStep<R, X>> PredicateAssertor<R, X> or(final X other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check
     * another object.
     * 
     * @param other
     *            the other or next checked object to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the object type
     * @param <R>
     *            the type of predicate
     * @return the predicate assertor
     */
    default <X, R extends PredicateStep<R, X>> PredicateAssertor<R, X> or(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.getType(other), analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Boolean}.
     * 
     * @param other
     *            the other or next checked {@link Boolean} to check
     * @return the predicate assertor
     */
    default PredicateAssertorBoolean or(final Boolean other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Boolean}.
     * 
     * @param other
     *            the other or next checked {@link Boolean} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor
     */
    default PredicateAssertorBoolean or(final Boolean other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.BOOLEAN, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link CharSequence}.
     * 
     * @param other
     *            the other or next checked {@link CharSequence} to check
     * @param <X>
     *            the {@link CharSequence} type
     * @return the predicate assertor
     */
    default <X extends CharSequence> PredicateAssertorCharSequence<X> or(final X other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link CharSequence}.
     * 
     * @param other
     *            the other or next checked {@link CharSequence} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the {@link CharSequence} type
     * @return the predicate assertor
     */
    default <X extends CharSequence> PredicateAssertorCharSequence<X> or(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.CHAR_SEQUENCE, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Number}.
     * 
     * @param other
     *            the other or next checked {@link Number} to check
     * @param <N>
     *            the {@link Number} type
     * @return the predicate assertor
     */
    default <N extends Number & Comparable<N>> PredicateAssertorNumber<N> or(final N other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Number}.
     * 
     * @param other
     *            the other or next checked {@link Number} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <N>
     *            the {@link Number} type
     * @return the predicate assertor
     */
    default <N extends Number & Comparable<N>> PredicateAssertorNumber<N> or(final N other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.getType(other), analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check an
     * {@code array}.
     * 
     * @param other
     *            the other or next checked {@code array} to check
     * @param <X>
     *            the array elements type
     * @return the predicate assertor
     */
    default <X> PredicateAssertorArray<X> or(final X[] other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check an
     * {@code array}.
     * 
     * @param other
     *            the other or next checked {@code array} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the array elements type
     * @return the predicate assertor
     */
    default <X> PredicateAssertorArray<X> or(final X[] other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.ARRAY, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Class}.
     * 
     * @param other
     *            the other or next checked {@link Class} to check
     * @param <X>
     *            the {@link Class} type
     * @return the predicate assertor
     */
    default <X> PredicateAssertorClass<X> or(final Class<X> other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Class}.
     * 
     * @param other
     *            the other or next checked {@link Class} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the {@link Class} type
     * @return the predicate assertor
     */
    default <X> PredicateAssertorClass<X> or(final Class<X> other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.CLASS, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Map}.
     * 
     * @param other
     *            the other or next checked {@link Map} to check
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the predicate assertor
     */
    default <K, V> PredicateAssertorMap<K, V> or(final Map<K, V> other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Map}.
     * 
     * @param other
     *            the other or next checked {@link Map} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the predicate assertor
     */
    default <K, V> PredicateAssertorMap<K, V> or(final Map<K, V> other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.MAP, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check an
     * {@link Iterable}.
     * 
     * @param other
     *            the other or next checked {@link Iterable} to check
     * @param <I>
     *            the {@link Iterable} type
     * @param <X>
     *            the {@link Iterable} elements type
     * @return the predicate assertor
     */
    default <I extends Iterable<X>, X> PredicateAssertorIterable<I, X> or(final I other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check an
     * {@link Iterable}.
     * 
     * @param other
     *            the other or next checked {@link Iterable} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <I>
     *            the {@link Iterable} type
     * @param <X>
     *            the {@link Iterable} elements type
     * @return the predicate assertor
     */
    default <I extends Iterable<X>, X> PredicateAssertorIterable<I, X> or(final I other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.ITERABLE, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Date}.
     * 
     * @param other
     *            the other or next checked {@link Date} to check
     * @return the predicate assertor
     */
    default PredicateAssertorDate or(final Date other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Date}.
     * 
     * @param other
     *            the other or next checked {@link Date} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor
     */
    default PredicateAssertorDate or(final Date other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.DATE, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Calendar}.
     * 
     * @param other
     *            the other or next checked {@link Calendar} to check
     * @return the predicate assertor
     */
    default PredicateAssertorCalendar or(final Calendar other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * {@link Calendar}.
     * 
     * @param other
     *            the other or next checked {@link Calendar} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor
     */
    default PredicateAssertorCalendar or(final Calendar other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.CALENDAR, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * comparable {@link Temporal}.
     * 
     * @param other
     *            the other or next checked {@link Temporal} to check
     * @param <X>
     *            the temporal type
     * @return the predicate assertor
     */
    default <X extends Temporal & Comparable<X>> PredicateAssertorTemporal<X> or(final X other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * comparable {@link Temporal}.
     * 
     * @param other
     *            the other or next checked {@link Temporal} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the temporal type
     * @return the predicate assertor
     */
    default <X extends Temporal & Comparable<X>> PredicateAssertorTemporal<X> or(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.TEMPORAL, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * comparable {@link Throwable}.
     * 
     * @param other
     *            the other or next checked {@link Throwable} to check
     * @param <X>
     *            the throwable type
     * @return the predicate assertor
     */
    default <X extends Throwable> PredicateAssertorThrowable<X> or(final X other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check a
     * comparable {@link Throwable}.
     * 
     * @param other
     *            the other or next checked {@link Throwable} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the throwable type
     * @return the predicate assertor
     */
    default <X extends Throwable> PredicateAssertorThrowable<X> or(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.THROWABLE, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check an
     * {@link Enum}.
     * 
     * @param other
     *            the other or next checked {@link Enum} to check
     * @param <X>
     *            the type of the {@link Enum}
     * @return the predicate assertor
     */
    default <X extends Enum<X>> PredicateAssertorEnum<X> or(final X other) {
        return this.or(other, null);
    }

    /**
     * Append an operator 'OR' on the current step with the ability to check an
     * {@link Enum}.
     * 
     * @param other
     *            the other or next checked {@link Enum} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the type of the {@link Enum}
     * @return the predicate assertor
     */
    default <X extends Enum<X>> PredicateAssertorEnum<X> or(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperAssertor.or(this.getStep(), other, EnumType.ENUMERATION, analysisMode);
    }

    /**
     * Append an operator 'OR' on the current step.
     * 
     * @return the predicate assertor
     */
    default PredicateAssertor<S, T> or() {
        return () -> HelperAssertor.or(this.getStep());
    }
}