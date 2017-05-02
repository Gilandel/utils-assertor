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

import fr.landel.utils.assertor.StepAssertor;
import fr.landel.utils.assertor.helper.HelperStep;

/**
 * This class is an intermediate or final link in chain, see
 * {@link PredicateStep}.
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
public interface PredicateStepIterable<I extends Iterable<T>, T>
        extends PredicateStep<PredicateStepIterable<I, T>, I> {

    default PredicateStepIterable<I, T> get(final StepAssertor<I> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorStepIterable<I, T> and() {
        return () -> HelperStep.and(this.getStep());
    }

    @Override
    default PredicateAssertorStepIterable<I, T> or() {
        return () -> HelperStep.or(this.getStep());
    }

    @Override
    default PredicateAssertorStepIterable<I, T> xor() {
        return () -> HelperStep.xor(this.getStep());
    }

    @Override
    default PredicateAssertorStepIterable<I, T> nand() {
        return () -> HelperStep.nand(this.getStep());
    }

    @Override
    default PredicateAssertorStepIterable<I, T> nor() {
        return () -> HelperStep.nor(this.getStep());
    }
}
