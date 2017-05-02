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
 * @since Aug 7, 2016
 * @author Gilles
 *
 * @param <T>
 *            the type of the checked object
 */
@FunctionalInterface
public interface StepCharSequence<T extends CharSequence> extends Step<StepCharSequence<T>, T> {

    /**
     * {@inheritDoc}
     */
    @Override
    default StepCharSequence<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    @Override
    default AssertorStepCharSequence<T> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default AssertorStepCharSequence<T> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default AssertorStepCharSequence<T> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default AssertorStepCharSequence<T> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default AssertorStepCharSequence<T> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
