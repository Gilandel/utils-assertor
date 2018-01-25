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
package fr.landel.utils.assertor;

import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumType;
import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate link in Assertor chain, see
 * {@link AssertorStep}.
 *
 * @since Mar 28, 2017
 * @author Gilles
 *
 * @param <S>
 *            the type of predicate step
 * @param <T>
 *            the type of checked object
 */
public interface OperatorAnd<S extends Step<S, T>, T> {

    /**
     * @return the step result
     */
    StepAssertor<T> getStep();

    /**
     * The only purpose is to avoid the copy of basic methods into children
     * interfaces. This is an indirect way to create specific {@link Step} by
     * overriding this interface. All children class has to override this method
     * 
     * @param result
     *            the result
     * @return the predicate step
     */
    S get(StepAssertor<T> result);

    /**
     * Applies a predicate step in the current one with the operator
     * {@link EnumOperator#AND}. The aim of this is to provide the equivalence
     * of parenthesis in condition expressions.
     * 
     * <pre>
     * // '' null or not empty and 'text' null or not empty
     * Assertor.that("").isNull().or().isNotEmpty().and("text").isNull().or().isNotEmpty().isOK();
     * // -&gt; true (because: false or false and false or true, false or false =
     * // false =&gt; false and false = false =&gt; false or true = true)
     * 
     * // ('' null or not empty) and ('text' null or not empty)
     * Assertor.that("").isNull().or().isNotEmpty().and(Assertor.that("text").isNull().or().isNotEmpty()).isOK();
     * // -&gt; false (because: (false or false) and (false or true) =&gt; false and
     * // true = false)
     * 
     * </pre>
     * 
     * @param other
     *            the other predicate step
     * @param <X>
     *            The type of other checked object
     * @param <R>
     *            The {@linkplain Step} type
     * @return this predicate step with the other injected
     */
    default <X, R extends Step<R, X>> S and(final Step<R, X> other) {
        return this.get(HelperStep.and(this.getStep(), other.getStep()));
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check another object.
     * 
     * @param other
     *            the other or next checked object to check
     * @param <X>
     *            the object type
     * @param <R>
     *            the type of predicate
     * @return the predicate assertor
     */
    default <X, R extends Step<R, X>> AssertorStep<R, X> and(final X other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check another object.
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
    default <X, R extends Step<R, X>> AssertorStep<R, X> and(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.getType(other), analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Boolean}.
     * 
     * @param other
     *            the other or next checked {@link Boolean} to check
     * @return the predicate assertor
     */
    default AssertorStepBoolean and(final Boolean other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Boolean}.
     * 
     * @param other
     *            the other or next checked {@link Boolean} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor
     */
    default AssertorStepBoolean and(final Boolean other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.BOOLEAN, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link CharSequence}.
     * 
     * @param other
     *            the other or next checked {@link CharSequence} to check
     * @param <X>
     *            the {@link CharSequence} type
     * @return the predicate assertor
     */
    default <X extends CharSequence> AssertorStepCharSequence<X> and(final X other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link CharSequence}.
     * 
     * @param other
     *            the other or next checked {@link CharSequence} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the {@link CharSequence} type
     * @return the predicate assertor
     */
    default <X extends CharSequence> AssertorStepCharSequence<X> and(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.CHAR_SEQUENCE, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Number}.
     * 
     * @param other
     *            the other or next checked {@link Number} to check
     * @param <N>
     *            the {@link Number} type
     * @return the predicate assertor
     */
    default <N extends Number & Comparable<N>> AssertorStepNumber<N> and(final N other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Number}.
     * 
     * @param other
     *            the other or next checked {@link Number} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <N>
     *            the {@link Number} type
     * @return the predicate assertor
     */
    default <N extends Number & Comparable<N>> AssertorStepNumber<N> and(final N other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.getType(other), analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check an {@code array}.
     * 
     * @param other
     *            the other or next checked {@code array} to check
     * @param <X>
     *            the array elements type
     * @return the predicate assertor
     */
    default <X> AssertorStepArray<X> and(final X[] other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check an {@code array}.
     * 
     * @param other
     *            the other or next checked {@code array} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the array elements type
     * @return the predicate assertor
     */
    default <X> AssertorStepArray<X> and(final X[] other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.ARRAY, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Class}.
     * 
     * @param other
     *            the other or next checked {@link Class} to check
     * @param <X>
     *            the {@link Class} type
     * @return the predicate assertor
     */
    default <X> AssertorStepClass<X> and(final Class<X> other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Class}.
     * 
     * @param other
     *            the other or next checked {@link Class} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the {@link Class} type
     * @return the predicate assertor
     */
    default <X> AssertorStepClass<X> and(final Class<X> other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.CLASS, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Map}.
     * 
     * @param other
     *            the other or next checked {@link Map} to check
     * @param <K>
     *            the {@link Map} key elements type
     * @param <V>
     *            the {@link Map} value elements type
     * @return the predicate assertor
     */
    default <K, V> AssertorStepMap<K, V> and(final Map<K, V> other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Map}.
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
    default <K, V> AssertorStepMap<K, V> and(final Map<K, V> other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.MAP, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check an {@link Iterable}.
     * 
     * @param other
     *            the other or next checked {@link Iterable} to check
     * @param <I>
     *            the {@link Iterable} type
     * @param <X>
     *            the {@link Iterable} elements type
     * @return the predicate assertor
     */
    default <I extends Iterable<X>, X> AssertorStepIterable<I, X> and(final I other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check an {@link Iterable}.
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
    default <I extends Iterable<X>, X> AssertorStepIterable<I, X> and(final I other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.ITERABLE, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Date}.
     * 
     * @param other
     *            the other or next checked {@link Date} to check
     * @return the predicate assertor
     */
    default AssertorStepDate and(final Date other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Date}.
     * 
     * @param other
     *            the other or next checked {@link Date} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor
     */
    default AssertorStepDate and(final Date other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.DATE, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Calendar}.
     * 
     * @param other
     *            the other or next checked {@link Calendar} to check
     * @return the predicate assertor
     */
    default AssertorStepCalendar and(final Calendar other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a {@link Calendar}.
     * 
     * @param other
     *            the other or next checked {@link Calendar} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @return the predicate assertor
     */
    default AssertorStepCalendar and(final Calendar other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.CALENDAR, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a comparable {@link Temporal}.
     * 
     * @param other
     *            the other or next checked {@link Temporal} to check
     * @param <X>
     *            the temporal type
     * @return the predicate assertor
     */
    default <X extends Temporal & Comparable<X>> AssertorStepTemporal<X> and(final X other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a comparable {@link Temporal}.
     * 
     * @param other
     *            the other or next checked {@link Temporal} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the temporal type
     * @return the predicate assertor
     */
    default <X extends Temporal & Comparable<X>> AssertorStepTemporal<X> and(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.TEMPORAL, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a comparable {@link Throwable}.
     * 
     * @param other
     *            the other or next checked {@link Throwable} to check
     * @param <X>
     *            the throwable type
     * @return the predicate assertor
     */
    default <X extends Throwable> AssertorStepThrowable<X> and(final X other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check a comparable {@link Throwable}.
     * 
     * @param other
     *            the other or next checked {@link Throwable} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the throwable type
     * @return the predicate assertor
     */
    default <X extends Throwable> AssertorStepThrowable<X> and(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.THROWABLE, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check an {@link Enum}.
     * 
     * @param other
     *            the other or next checked {@link Enum} to check
     * @param <X>
     *            the type of the {@link Enum}
     * @return the predicate assertor
     */
    default <X extends Enum<X>> AssertorStepEnum<X> and(final X other) {
        return this.and(other, null);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step with
     * the ability to check an {@link Enum}.
     * 
     * @param other
     *            the other or next checked {@link Enum} to check
     * @param analysisMode
     *            the preferred analysis mode
     * @param <X>
     *            the type of the {@link Enum}
     * @return the predicate assertor
     */
    default <X extends Enum<X>> AssertorStepEnum<X> and(final X other, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), other, EnumType.ENUMERATION, analysisMode);
    }

    /**
     * Append an operator '{@link EnumOperator#AND}' on the current step.
     * 
     * @return the predicate assertor
     */
    default AssertorStep<S, T> and() {
        return () -> HelperStep.and(this.getStep());
    }

    /**
     * Append a sub-assertor with the operator {@link EnumOperator#AND} on the
     * current step. Be sure you sub-assertor manage {@code null} values.
     * 
     * @param subAssertor
     *            the sub-assertor
     * @param <X>
     *            the predicate step type expected
     * @param <Y>
     *            the type of the checked sub-object
     * @return the predicate step
     */
    default <X extends Step<X, Y>, Y> S andAssertor(final Function<T, X> subAssertor) {
        return this.get(HelperStep.and(this.getStep(), subAssertor));
    }

    /**
     * Append an object through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <R>
     *            the predicate step type expected
     * @param <X>
     *            the type of the checked sub-object
     * @return the assertor step
     */
    default <X, R extends Step<R, X>> AssertorStep<R, X> andObject(final Function<T, X> mapper) {
        return this.andObject(mapper, null);
    }

    /**
     * Append an object through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <R>
     *            the predicate step type expected
     * @param <X>
     *            the type of the checked sub-object
     * @return the assertor step
     */
    default <X, R extends Step<R, X>> AssertorStep<R, X> andObject(final Function<T, X> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.UNKNOWN, analysisMode);
    }

    /**
     * Append a {@link Boolean} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @return the assertor step
     */
    default AssertorStepBoolean andBoolean(final Function<T, Boolean> mapper) {
        return this.andBoolean(mapper, null);
    }

    /**
     * Append a {@link Boolean} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @return the assertor step
     */
    default AssertorStepBoolean andBoolean(final Function<T, Boolean> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.BOOLEAN, analysisMode);
    }

    /**
     * Append a {@link CharSequence} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <X>
     *            the type of the {@link CharSequence}
     * @return the assertor step
     */
    default <X extends CharSequence> AssertorStepCharSequence<X> andCharSequence(final Function<T, X> mapper) {
        return this.andCharSequence(mapper, null);
    }

    /**
     * Append a {@link CharSequence} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <X>
     *            the type of the {@link CharSequence}
     * @return the assertor step
     */
    default <X extends CharSequence> AssertorStepCharSequence<X> andCharSequence(final Function<T, X> mapper,
            final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.CHAR_SEQUENCE, analysisMode);
    }

    /**
     * Append an array through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <X>
     *            the type of the array elements
     * @return the assertor step
     */
    default <X> AssertorStepArray<X> andArray(final Function<T, X[]> mapper) {
        return this.andArray(mapper, null);
    }

    /**
     * Append an array through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <X>
     *            the type of the array elements
     * @return the assertor step
     */
    default <X> AssertorStepArray<X> andArray(final Function<T, X[]> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.ARRAY, analysisMode);
    }

    /**
     * Append a {@link Date} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @return the assertor step
     */
    default AssertorStepDate andDate(final Function<T, Date> mapper) {
        return this.andDate(mapper, null);
    }

    /**
     * Append a {@link Date} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @return the assertor step
     */
    default AssertorStepDate andDate(final Function<T, Date> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.DATE, analysisMode);
    }

    /**
     * Append a {@link Calendar} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @return the assertor step
     */
    default AssertorStepCalendar andCalendar(final Function<T, Calendar> mapper) {
        return this.andCalendar(mapper, null);
    }

    /**
     * Append a {@link Calendar} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @return the assertor step
     */
    default AssertorStepCalendar andCalendar(final Function<T, Calendar> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.CALENDAR, analysisMode);
    }

    /**
     * Append a {@link Temporal} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <X>
     *            the type of the {@link Temporal}
     * @return the assertor step
     */
    default <X extends Temporal & Comparable<X>> AssertorStepTemporal<X> andTemporal(final Function<T, X> mapper) {
        return this.andTemporal(mapper, null);
    }

    /**
     * Append a {@link Temporal} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <X>
     *            the type of the {@link Temporal}
     * @return the assertor step
     */
    default <X extends Temporal & Comparable<X>> AssertorStepTemporal<X> andTemporal(final Function<T, X> mapper,
            final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.TEMPORAL, analysisMode);
    }

    /**
     * Append a {@link Class} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <X>
     *            the type of the {@link Class}
     * @return the assertor step
     */
    default <X> AssertorStepClass<X> andClass(final Function<T, Class<X>> mapper) {
        return this.andClass(mapper, null);
    }

    /**
     * Append a {@link Class} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <X>
     *            the type of the {@link Class}
     * @return the assertor step
     */
    default <X> AssertorStepClass<X> andClass(final Function<T, Class<X>> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.CLASS, analysisMode);
    }

    /**
     * Append a {@link Enum} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <X>
     *            the type of the {@link Enum}
     * @return the assertor step
     */
    default <X extends Enum<X>> AssertorStepEnum<X> andEnum(final Function<T, X> mapper) {
        return this.andEnum(mapper, null);
    }

    /**
     * Append a {@link Enum} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <X>
     *            the type of the {@link Enum}
     * @return the assertor step
     */
    default <X extends Enum<X>> AssertorStepEnum<X> andEnum(final Function<T, X> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.ENUMERATION, analysisMode);
    }

    /**
     * Append a {@link Throwable} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <X>
     *            the type of the {@link Throwable}
     * @return the assertor step
     */
    default <X extends Throwable> AssertorStepThrowable<X> andThrowable(final Function<T, X> mapper) {
        return this.andThrowable(mapper, null);
    }

    /**
     * Append a {@link Throwable} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <X>
     *            the type of the {@link Throwable}
     * @return the assertor step
     */
    default <X extends Throwable> AssertorStepThrowable<X> andThrowable(final Function<T, X> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.THROWABLE, analysisMode);
    }

    /**
     * Append a {@link Number} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <N>
     *            the type of the {@link Number}
     * @return the assertor step
     */
    default <N extends Number & Comparable<N>> AssertorStepNumber<N> andNumber(final Function<T, N> mapper) {
        return this.andNumber(mapper, null);
    }

    /**
     * Append a {@link Number} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <N>
     *            the type of the {@link Number}
     * @return the assertor step
     */
    default <N extends Number & Comparable<N>> AssertorStepNumber<N> andNumber(final Function<T, N> mapper,
            final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.NUMBER_DECIMAL, analysisMode);
    }

    /**
     * Append a {@link Iterable} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <I>
     *            the type of the {@link Iterable}
     * @param <X>
     *            the type of the {@link Iterable} elements
     * @return the assertor step
     */
    default <I extends Iterable<X>, X> AssertorStepIterable<I, X> andIterable(final Function<T, I> mapper) {
        return this.andIterable(mapper, null);
    }

    /**
     * Append a {@link Iterable} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <I>
     *            the type of the {@link Iterable}
     * @param <X>
     *            the type of the {@link Iterable} elements
     * @return the assertor step
     */
    default <I extends Iterable<X>, X> AssertorStepIterable<I, X> andIterable(final Function<T, I> mapper,
            final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.ITERABLE, analysisMode);
    }

    /**
     * Append a {@link Map} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param <K>
     *            the type of the {@link Map} keys
     * @param <V>
     *            the type of the {@link Map} values
     * @return the assertor step
     */
    default <K, V> AssertorStepMap<K, V> andMap(final Function<T, Map<K, V>> mapper) {
        return this.andMap(mapper, null);
    }

    /**
     * Append a {@link Map} through a mapper with the operator
     * {@link EnumOperator#AND} on the current step.
     * 
     * @param mapper
     *            the mapper function
     * @param analysisMode
     *            the analysis mode ({@link EnumAnalysisMode#STANDARD},
     *            {@link EnumAnalysisMode#STREAM} or
     *            {@link EnumAnalysisMode#PARALLEL})
     * @param <K>
     *            the type of the {@link Map} keys
     * @param <V>
     *            the type of the {@link Map} values
     * @return the assertor step
     */
    default <K, V> AssertorStepMap<K, V> andMap(final Function<T, Map<K, V>> mapper, final EnumAnalysisMode analysisMode) {
        return () -> HelperStep.and(this.getStep(), mapper, EnumType.MAP, analysisMode);
    }
}
