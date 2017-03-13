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
 * @since Aug 7, 2016
 * @author Gilles
 *
 * @param <T>
 *            the type of the checked object
 */
@FunctionalInterface
public interface PredicateStepCharSequence<T extends CharSequence> extends PredicateStep<PredicateStepCharSequence<T>, T> {

    /**
     * {@inheritDoc}
     */
    @Override
    default PredicateStepCharSequence<T> get(final StepAssertor<T> result) {
        return () -> result;
    }

    @Override
    default PredicateAssertorCharSequence<T> and() {
        return () -> HelperAssertor.and(this.getStep());
    }

    @Override
    default PredicateAssertorCharSequence<T> or() {
        return () -> HelperAssertor.or(this.getStep());
    }

    @Override
    default PredicateAssertorCharSequence<T> xor() {
        return () -> HelperAssertor.xor(this.getStep());
    }

    @Override
    default PredicateAssertorCharSequence<T> nand() {
        return () -> HelperAssertor.nand(this.getStep());
    }

    @Override
    default PredicateAssertorCharSequence<T> nor() {
        return () -> HelperAssertor.nor(this.getStep());
    }
}
