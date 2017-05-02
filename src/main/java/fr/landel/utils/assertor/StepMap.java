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

import java.util.Map;

import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate or final link in chain, see {@link Step}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface StepMap<M extends Map<K, V>, K, V> extends Step<StepMap<M, K, V>, M> {

    default StepMap<M, K, V> get(final StepAssertor<M> result) {
        return () -> result;
    }

    @Override
    default AssertorStepMap<M, K, V> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default AssertorStepMap<M, K, V> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default AssertorStepMap<M, K, V> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default AssertorStepMap<M, K, V> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default AssertorStepMap<M, K, V> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
