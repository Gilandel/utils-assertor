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
package fr.landel.utils.assertor.predicate;

import java.time.temporal.Temporal;

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate or final link in chain, see
 * {@link PredicateStep}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateStepTemporal<T extends Temporal & Comparable<T>>
        extends PredicateStep<PredicateStepTemporal<T>, T> {

    default PredicateStepTemporal<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorStepTemporal<T> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default PredicateAssertorStepTemporal<T> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default PredicateAssertorStepTemporal<T> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default PredicateAssertorStepTemporal<T> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default PredicateAssertorStepTemporal<T> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
