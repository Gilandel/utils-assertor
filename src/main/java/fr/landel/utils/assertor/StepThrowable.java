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
 */
@FunctionalInterface
public interface StepThrowable<T extends Throwable> extends Step<StepThrowable<T>, T> {

    default StepThrowable<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    @Override
    default AssertorStepThrowable<T> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default AssertorStepThrowable<T> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default AssertorStepThrowable<T> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default AssertorStepThrowable<T> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default AssertorStepThrowable<T> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
