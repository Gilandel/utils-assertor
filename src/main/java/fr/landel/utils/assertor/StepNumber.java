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
public interface StepNumber<N extends Number & Comparable<N>> extends Step<StepNumber<N>, N> {

    default StepNumber<N> get(final StepAssertor<N> result) {
        return () -> result;
    }

    @Override
    default AssertorStepNumber<N> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default AssertorStepNumber<N> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default AssertorStepNumber<N> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default AssertorStepNumber<N> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default AssertorStepNumber<N> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
