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

import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate or final link in chain, see {@link Step}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 * @param <I>
 *            the {@link Iterable} type
 * @param <T>
 *            the {@link Iterable} elements type
 */
@FunctionalInterface
public interface StepIterable<I extends Iterable<T>, T> extends Step<StepIterable<I, T>, I> {

    default StepIterable<I, T> get(final StepAssertor<I> result) {
        return () -> result;
    }

    @Override
    default AssertorStepIterable<I, T> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default AssertorStepIterable<I, T> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default AssertorStepIterable<I, T> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default AssertorStepIterable<I, T> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default AssertorStepIterable<I, T> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
