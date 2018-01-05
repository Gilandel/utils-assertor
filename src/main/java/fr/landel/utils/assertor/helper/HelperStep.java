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
package fr.landel.utils.assertor.helper;

import java.util.function.Function;

import fr.landel.utils.assertor.Step;
import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.commons.ConstantsAssertor;
import fr.landel.utils.assertor.enums.EnumAnalysisMode;
import fr.landel.utils.assertor.enums.EnumOperator;
import fr.landel.utils.assertor.enums.EnumType;

/**
 * Assertor helper class, to build steps.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
public class HelperStep extends ConstantsAssertor {

    /**
     * Apply a 'NOT' on the current step
     * 
     * @param result
     *            the current intermediate step data
     * @param <T>
     *            the type of checked object
     * @return the new intermediate step data
     */
    public static <T> StepAssertor<T> not(final StepAssertor<T> result) {
        return new StepAssertor<>(result);
    }

    /**
     * Append an 'AND' operator with a property to the current step
     * 
     * @param result
     *            the previous step
     * @param mapper
     *            the mapper function
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the property
     * @return the next step
     */
    public static <X, T> StepAssertor<T> and(final StepAssertor<X> result, final Function<X, T> mapper, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, mapper, type, EnumOperator.AND, analysisMode);
    }

    /**
     * Append an 'AND' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param subAssertor
     *            the sub-assertor
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @param <S>
     *            the type of step
     * @return the next step
     */
    public static <X, T, S extends Step<S, X>> StepAssertor<T> and(final StepAssertor<T> result, final Function<T, S> subAssertor) {
        return new StepAssertor<>(result, subAssertor, EnumOperator.AND);
    }

    /**
     * Append an 'AND' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param object
     *            the new object
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <X, T> StepAssertor<T> and(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.AND, analysisMode);
    }

    /**
     * Append an 'OR' operator with a property to the current step
     * 
     * @param result
     *            the previous step
     * @param mapper
     *            the mapper function
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the property
     * @return the next step
     */
    public static <X, T> StepAssertor<T> or(final StepAssertor<X> result, final Function<X, T> mapper, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, mapper, type, EnumOperator.OR, analysisMode);
    }

    /**
     * Append an 'OR' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param subAssertor
     *            the sub-assertor
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @param <S>
     *            the type of step
     * @return the next step
     */
    public static <X, T, S extends Step<S, X>> StepAssertor<T> or(final StepAssertor<T> result, final Function<T, S> subAssertor) {
        return new StepAssertor<>(result, subAssertor, EnumOperator.OR);
    }

    /**
     * Append an 'OR' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param object
     *            the new object
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <X, T> StepAssertor<T> or(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.OR, analysisMode);
    }

    /**
     * Append an 'XOR' operator with a property to the current step
     * 
     * @param result
     *            the previous step
     * @param mapper
     *            the mapper function
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the property
     * @return the next step
     */
    public static <X, T> StepAssertor<T> xor(final StepAssertor<X> result, final Function<X, T> mapper, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, mapper, type, EnumOperator.XOR, analysisMode);
    }

    /**
     * Append an 'XOR' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param subAssertor
     *            the sub-assertor
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @param <S>
     *            the type of step
     * @return the next step
     */
    public static <X, T, S extends Step<S, X>> StepAssertor<T> xor(final StepAssertor<T> result, final Function<T, S> subAssertor) {
        return new StepAssertor<>(result, subAssertor, EnumOperator.XOR);
    }

    /**
     * Append an 'XOR' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param object
     *            the new object
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <X, T> StepAssertor<T> xor(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.XOR, analysisMode);
    }

    /**
     * Append an 'NAND' operator with a property to the current step
     * 
     * @param result
     *            the previous step
     * @param mapper
     *            the mapper function
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the property
     * @return the next step
     */
    public static <X, T> StepAssertor<T> nand(final StepAssertor<X> result, final Function<X, T> mapper, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, mapper, type, EnumOperator.NAND, analysisMode);
    }

    /**
     * Append an 'NAND' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param subAssertor
     *            the sub-assertor
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @param <S>
     *            the type of step
     * @return the next step
     */
    public static <X, T, S extends Step<S, X>> StepAssertor<T> nand(final StepAssertor<T> result, final Function<T, S> subAssertor) {
        return new StepAssertor<>(result, subAssertor, EnumOperator.NAND);
    }

    /**
     * Append an 'NAND' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param object
     *            the new object
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <X, T> StepAssertor<T> nand(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.NAND, analysisMode);
    }

    /**
     * Append an 'NOR' operator with a property to the current step
     * 
     * @param result
     *            the previous step
     * @param mapper
     *            the mapper function
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the property
     * @return the next step
     */
    public static <X, T> StepAssertor<T> nor(final StepAssertor<X> result, final Function<X, T> mapper, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, mapper, type, EnumOperator.NOR, analysisMode);
    }

    /**
     * Append an 'NOR' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param subAssertor
     *            the sub-assertor
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @param <S>
     *            the type of step
     * @return the next step
     */
    public static <X, T, S extends Step<S, X>> StepAssertor<T> nor(final StepAssertor<T> result, final Function<T, S> subAssertor) {
        return new StepAssertor<>(result, subAssertor, EnumOperator.NOR);
    }

    /**
     * Append an 'NOR' operator with a new object to the current step
     * 
     * @param result
     *            the previous step
     * @param object
     *            the new object
     * @param type
     *            the type of the new object
     * @param analysisMode
     *            the analysis mode to check the new object
     * @param <X>
     *            the type of the previous checked object
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <X, T> StepAssertor<T> nor(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.NOR, analysisMode);
    }

    /**
     * Append an 'AND' operator to the current step
     * 
     * @param result
     *            the previous step
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <T> StepAssertor<T> and(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.AND);
    }

    /**
     * Append an 'OR' operator to the current step
     * 
     * @param result
     *            the previous step
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <T> StepAssertor<T> or(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.OR);
    }

    /**
     * Append an 'XOR' operator to the current step
     * 
     * @param result
     *            the previous step
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <T> StepAssertor<T> xor(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.XOR);
    }

    /**
     * Append an 'NAND' operator to the current step
     * 
     * @param result
     *            the previous step
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <T> StepAssertor<T> nand(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.NAND);
    }

    /**
     * Append an 'NOR' operator to the current step
     * 
     * @param result
     *            the previous step
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <T> StepAssertor<T> nor(final StepAssertor<T> result) {
        return new StepAssertor<>(result, EnumOperator.NOR);
    }

    /**
     * Append an 'AND' operator with a sub step to the current step
     * 
     * @param result
     *            the previous step
     * @param other
     *            the sub step
     * @param <T>
     *            the type of the current checked object
     * @param <X>
     *            the type of the checked object of the sub step
     * @return the next step
     */
    public static <T, X> StepAssertor<T> and(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.AND);
    }

    /**
     * Append an 'OR' operator with a sub step to the current step
     * 
     * @param result
     *            the previous step
     * @param other
     *            the sub step
     * @param <T>
     *            the type of the current checked object
     * @param <X>
     *            the type of the checked object of the sub step
     * @return the next step
     */
    public static <T, X> StepAssertor<T> or(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.OR);
    }

    /**
     * Append an 'XOR' operator with a sub step to the current step
     * 
     * @param result
     *            the previous step
     * @param other
     *            the sub step
     * @param <T>
     *            the type of the current checked object
     * @param <X>
     *            the type of the checked object of the sub step
     * @return the next step
     */
    public static <T, X> StepAssertor<T> xor(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.XOR);
    }

    /**
     * Append an 'NAND' operator with a sub step to the current step
     * 
     * @param result
     *            the previous step
     * @param other
     *            the sub step
     * @param <T>
     *            the type of the current checked object
     * @param <X>
     *            the type of the checked object of the sub step
     * @return the next step
     */
    public static <T, X> StepAssertor<T> nand(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.NAND);
    }

    /**
     * Append an 'NOR' operator with a sub step to the current step
     * 
     * @param result
     *            the previous step
     * @param other
     *            the sub step
     * @param <T>
     *            the type of the current checked object
     * @param <X>
     *            the type of the checked object of the sub step
     * @return the next step
     */
    public static <T, X> StepAssertor<T> nor(final StepAssertor<T> result, final StepAssertor<X> other) {
        return new StepAssertor<>(result, other, EnumOperator.NOR);
    }

    /**
     * Append a matcher object step to the current step
     * 
     * @param result
     *            the previous step
     * @param object
     *            the matcher object
     * @param <T>
     *            the type of the new object
     * @return the next step
     */
    public static <T> StepAssertor<T> object(final StepAssertor<T> result, final T object) {
        return new StepAssertor<>(result, object);
    }
}
