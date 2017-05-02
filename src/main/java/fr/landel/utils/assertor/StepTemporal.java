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

import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate or final link in chain, see {@link Step}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface StepTemporal<T extends Temporal & Comparable<T>> extends Step<StepTemporal<T>, T> {

    default StepTemporal<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    @Override
    default AssertorStepTemporal<T> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default AssertorStepTemporal<T> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default AssertorStepTemporal<T> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default AssertorStepTemporal<T> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default AssertorStepTemporal<T> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
