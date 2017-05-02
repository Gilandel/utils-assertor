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
package fr.landel.utils.assertor.helper;

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
     * @return the new step
     */
    public static <X, T> StepAssertor<T> and(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.AND, analysisMode);
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
     * @return the new step
     */
    public static <X, T> StepAssertor<T> or(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.OR, analysisMode);
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
     * @return the new step
     */
    public static <X, T> StepAssertor<T> xor(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.XOR, analysisMode);
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
     * @return the new step
     */
    public static <X, T> StepAssertor<T> nand(final StepAssertor<X> result, final T object, final EnumType type,
            final EnumAnalysisMode analysisMode) {
        return new StepAssertor<>(result, object, type, EnumOperator.NAND, analysisMode);
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
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
     * @return the new step
     */
    public static <T> StepAssertor<T> object(final StepAssertor<T> result, final T object) {
        return new StepAssertor<>(result, object);
    }
}