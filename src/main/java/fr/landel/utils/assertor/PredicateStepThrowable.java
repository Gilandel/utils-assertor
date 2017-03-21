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

/**
 * This class is an intermediate or final link in chain, see
 * {@link PredicateStep}.
 *
 * @since Aug 3, 2016
 * @author Gilles
 *
 */
@FunctionalInterface
public interface PredicateStepThrowable<T extends Throwable> extends PredicateStep<PredicateStepThrowable<T>, T> {

    default PredicateStepThrowable<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorThrowable<T> and() {
        return () -> HelperAssertor.and(this.getStep());
    }

    @Override
    default PredicateAssertorThrowable<T> or() {
        return () -> HelperAssertor.or(this.getStep());
    }

    @Override
    default PredicateAssertorThrowable<T> xor() {
        return () -> HelperAssertor.xor(this.getStep());
    }

    @Override
    default PredicateAssertorThrowable<T> nand() {
        return () -> HelperAssertor.nand(this.getStep());
    }

    @Override
    default PredicateAssertorThrowable<T> nor() {
        return () -> HelperAssertor.nor(this.getStep());
    }
}
